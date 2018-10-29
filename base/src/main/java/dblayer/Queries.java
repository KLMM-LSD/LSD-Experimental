/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblayer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import queue.Node;

/**
 *
 * @author Lasse
 */
public class Queries {

    /* If fail, try to insert as much as possible anyways */
    public static final String INSERT_POST_QUERY = "INSERT IGNORE INTO posts VALUES(?, ?, ?,\n"
            + "	(SELECT userid FROM users WHERE username = ? AND userpassword = ?)\n"
            + ", ?, ?)";

    /* 1:parent, 2:this */
    public static final String ALTER_POST_QUERY = "UPDATE IGNORE posts AS lorem\n"
            + "INNER JOIN (SELECT * FROM posts WHERE postid = ?) AS ipsum\n"
            + "SET lorem.postthreadid = ipsum.postthreadid\n"
            + "WHERE lorem.postid = ?";

    public static final String INSERT_USER_QUERY = "INSERT IGNORE INTO users (usertype, username, userpassword) VALUES (?, ?, ?)";

    public static void pushList(Node n) throws SQLException {
        try (Connection con = HikariCPDataSource.getConnection()) {
            con.setAutoCommit(false);
            while (n != null) {
                insertWithCon(con, n);
                n = n.next;
            }
            con.commit();
        }
    }

    public static void insertWithCon(Connection con, Node n) throws SQLException {
        switch (n.kind) {
            case POST:
                insertPost(con, n);
                break;
            case SIGNUP:
                System.out.println("Insert a new user");
                insertUser(con, n);
                break;
            default:
            /* System.out.println("Wtf?"); */
        }

    }

    public static void insertPost(Connection con, Node n) throws SQLException {
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(n.body).getAsJsonObject();
        PreparedStatement ps;

        switch (jo.get("post_type").getAsString()) {
            case "story":
                ps = con.prepareStatement(INSERT_POST_QUERY);

                ps.setInt(1, jo.get("hanesst_id").getAsInt());
                ps.setString(2, "story");
                ps.setNull(3, Types.INTEGER);
                ps.setString(4, jo.get("username").getAsString());
                ps.setString(5, jo.get("pwd_hash").getAsString());
                ps.setInt(6, jo.get("hanesst_id").getAsInt());
                ps.setString(7, jo.get("post_url").getAsString() + "\n" + jo.get("post_title").getAsString());

                ps.execute();
                break;

            case "comment":
                ps = con.prepareStatement(INSERT_POST_QUERY);

                ps.setInt(1, jo.get("hanesst_id").getAsInt());
                ps.setString(2, "comment");
                ps.setInt(3, jo.get("post_parent").getAsInt());
                ps.setString(4, jo.get("username").getAsString());
                ps.setString(5, jo.get("pwd_hash").getAsString());
                /* Lookup later */
                ps.setNull(6, Types.INTEGER);
                ps.setString(7, jo.get("post_text").getAsString());

                ps.execute();

                ps = con.prepareStatement(ALTER_POST_QUERY);
                ps.setInt(1, jo.get("post_parent").getAsInt());
                ps.setInt(2, jo.get("hanesst_id").getAsInt());

                ps.execute();

                break;

            default:
                System.out.println("WTF?");
                return;
        }

    }

    public static void insertUser(Connection con, Node n) throws SQLException {
        PreparedStatement ps = con.prepareStatement(INSERT_USER_QUERY);
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(n.body).getAsJsonObject();

        ps.setString(1, "user");
        ps.setString(2, jo.get("username").getAsString());
        ps.setString(3, jo.get("pwd_hash").getAsString());

        ps.execute();
    }
}
