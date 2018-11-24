/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblayer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lasse
 */
public class PostQueries {

    public static final String GET_LATEST_QUERY = "SELECT postid FROM posts ORDER BY postid DESC LIMIT 1";
    public static final String GET_THREAD_QUERY = "SELECT * FROM posts WHERE postthreadid = ?";
    public static final String GET_FRONTPAGE_QUERY = "SELECT * FROM posts WHERE posttype = \"story\" ORDER BY postid DESC LIMIT ?";
    public static final int MAX_FRONTPAGE_ENTRIES = 20;

    public static AtomicReference<JsonObject> FRONTPAGE_CACHE = new AtomicReference<>();
    public static AtomicInteger FRONTPAGE_CACHE_LOCK = new AtomicInteger();
    public static long FRONTPAGE_CACHE_TIMESTAMP = 0;
    public static long FRONTPAGE_CACHE_MAXLIFE_MS = 1000;

    public static int getLatest() throws SQLException {
        int ret = 0;

        try (Connection con = HikariCPDataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(GET_LATEST_QUERY);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ret = rs.getInt(1);
            }
        }

        return ret;
    }

    public static JsonObject getThread(int threadid) throws SQLException {
        JsonObject ret = new JsonObject();
        try (Connection con = HikariCPDataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(GET_THREAD_QUERY);
            ResultSet rs;
            boolean first_is_story = false;
            int len = 0;
            JsonArray arr_postid = new JsonArray();
            JsonArray arr_postparentid = new JsonArray();
            JsonArray arr_postauthorid = new JsonArray();
            JsonArray arr_postcontent = new JsonArray();
            ps.setInt(1, threadid);
            rs = ps.executeQuery();
            while (rs.next()) {
                arr_postid.add(rs.getInt("postid"));
                arr_postparentid.add(rs.getInt("postparentid"));
                arr_postauthorid.add(rs.getInt("postauthorid"));
                arr_postcontent.add(rs.getString("postcontent"));
                len++;
            }
            ret.addProperty("first_is_story", first_is_story);
            ret.addProperty("len", len);
            ret.add("arr_postid", arr_postid);
            ret.add("arr_postparentid", arr_postparentid);
            ret.add("arr_postauthorid", arr_postauthorid);
            ret.add("arr_postcontent", arr_postcontent);
        }
        return ret;
    }

    public static JsonObject getFrontpage() {
        tryUpdateCache();
        return FRONTPAGE_CACHE.get();
    }

    public static void tryUpdateCache() {
        long cache_age = System.currentTimeMillis() - FRONTPAGE_CACHE_TIMESTAMP;
        JsonObject newfrontpage;

        if (cache_age <= FRONTPAGE_CACHE_MAXLIFE_MS) {
            return;
        }

        if (FRONTPAGE_CACHE_LOCK.getAndSet(1) == 1) {
            return;
        }

        try {
            newfrontpage = getFrontpageQuery();
            FRONTPAGE_CACHE.set(newfrontpage);
        } catch (SQLException ex) {
            Logger.getLogger(PostQueries.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            FRONTPAGE_CACHE_LOCK.set(0);
            FRONTPAGE_CACHE_TIMESTAMP = System.currentTimeMillis();
        }
    }

    public static JsonObject getFrontpageQuery() throws SQLException {
        JsonObject ret = new JsonObject();
        try (Connection con = HikariCPDataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(GET_FRONTPAGE_QUERY);
            ResultSet rs;
            int len = 0;
            JsonArray arr_postid = new JsonArray(20);
            JsonArray arr_postauthorid = new JsonArray(20);
            JsonArray arr_postcontent = new JsonArray(20);

            ps.setInt(1, MAX_FRONTPAGE_ENTRIES);
            rs = ps.executeQuery();

            while (rs.next()) {
                arr_postid.add(rs.getInt("postid"));
                arr_postauthorid.add(rs.getInt("postauthorid"));
                arr_postcontent.add(rs.getString("postcontent"));
                len++;
            }

            ret.addProperty("len", len);
            ret.add("arr_postid", arr_postid);
            ret.add("arr_postauthorid", arr_postauthorid);
            ret.add("arr_postcontent", arr_postcontent);

            return ret;
        }
    }
}
