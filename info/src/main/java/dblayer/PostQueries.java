/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lasse
 */
public class PostQueries {

    public static final String GET_LATEST_QUERY = "SELECT postid FROM posts ORDER BY postid DESC LIMIT 1";

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
}
