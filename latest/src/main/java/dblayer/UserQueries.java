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
 * @author lasse
 */
public class UserQueries {

    public static final int MAX_USERPAGE_POSTS = 20;
    public static final String GET_USER_QUERY = "SELECT usertype, username FROM users WHERE userid = ?";
    public static final String GET_USER_POSTS_QUERY = "SELECT postid, postthreadid, postcontent FROM posts WHERE postauthorid = ? ORDER BY postid DESC LIMIT ?";

    public static JsonObject getUserpage(int userid) throws SQLException {
        JsonObject ret = new JsonObject();
        int postcount = 0;

        String username = null;
        String usertype = null;
        JsonArray arr_postid = new JsonArray();
        JsonArray arr_postthreadid = new JsonArray();
        JsonArray arr_postcontent = new JsonArray();

        try (Connection con = HikariCPDataSource.getConnection()) {
            PreparedStatement ps;
            ResultSet rs;

            ps = con.prepareStatement(GET_USER_QUERY);
            ps.setInt(1, userid);
            rs = ps.executeQuery();
            while (rs.next()) {
                usertype = rs.getString("usertype");
                username = rs.getString("username");
            }

            ps = con.prepareStatement(GET_USER_POSTS_QUERY);
            ps.setInt(1, userid);
            ps.setInt(2, MAX_USERPAGE_POSTS);
            rs = ps.executeQuery();
            while (rs.next()) {
                arr_postid.add(rs.getInt("postid"));
                arr_postthreadid.add(rs.getInt("postthreadid"));
                arr_postcontent.add(rs.getString("postcontent"));
                postcount++;
            }
        }

        ret.addProperty("username", username);
        ret.addProperty("usertype", usertype);
        ret.addProperty("len", postcount);
        ret.add("arr_postid", arr_postid);
        ret.add("arr_postthreadid", arr_postthreadid);
        ret.add("arr_postcontent", arr_postcontent);

        return ret;
    }
}
