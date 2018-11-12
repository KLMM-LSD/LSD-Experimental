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

/**
 *
 * @author Lasse
 */
public class PostQueries {

    public static final String GET_LATEST_ID_QUERY = "SELECT postid FROM posts ORDER BY postid DESC LIMIT 1";
    public static final String GET_THREAD_QUERY = "SELECT * FROM posts WHERE postthreadid = ?";
    public static final String GET_FRONTPAGE_QUERY = "SELECT * FROM posts WHERE posttype = \"story\" ORDER BY postid DESC LIMIT ?";
    public static final int MAX_FRONTPAGE_ENTRIES = 20;

    public static int getLatestID() throws SQLException {
        int ret = 0;

        try (Connection con = HikariCPDataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(GET_LATEST_ID_QUERY);

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
            ret.addProperty("len", len);
            ret.add("arr_postid", arr_postid);
            ret.add("arr_postparentid", arr_postparentid);
            ret.add("arr_postauthorid", arr_postauthorid);
            ret.add("arr_postcontent", arr_postcontent);
        }
        return ret;
    }

    public static JsonObject getFrontpage() throws SQLException {
        JsonObject ret = new JsonObject();
        try (Connection con = HikariCPDataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(GET_FRONTPAGE_QUERY);
            ResultSet rs;
            int len = 0;
            JsonArray arr_postid = new JsonArray();
            JsonArray arr_postauthorid = new JsonArray();
            JsonArray arr_postcontent = new JsonArray();

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
