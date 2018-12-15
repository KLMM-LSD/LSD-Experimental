/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserTests;

import com.google.gson.JsonObject;
import dblayer.UserQueries;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Micha
 */
public class UserQueriesIT {

    private UserQueries uQuery;
    private HikariTestData conn;
    private String testUser;

    public UserQueriesIT() {
    }

    @Before
    public void setUp() {
        uQuery = new UserQueries();
        testUser = "{'userid': 10, 'usertype':'user','username':'Bobby'}";
    }

//    @After
//    public void tearDown() throws SQLException {
//        try (Connection conn = HikariTestData.getConnection()) {
//            cleanUp(conn);
//        }
//    }

    @Test
    public void getUserPageTest() throws SQLException {
        try (Connection conn = HikariTestData.getConnection()) {
            
//            uQuery.getUserpage(10);
            assertEquals(1, uQuery);
            
        }
    }

    public JsonObject getTestData(Connection conn, String type) throws SQLException{
        PreparedStatement ps = conn.prepareStatement("SELECT usertype, username FROM users WHERE userid = ?");
        JsonObject ret;
        try (ResultSet r = ps.executeQuery()) {
            ret = new JsonObject();
            r.next();
            String usertype = r.getString("usertype");
            String username = r.getString("username");
            ret.addProperty(usertype, "usertype");
            ret.addProperty(username, "username");
        }
        return ret;
    }
    
//    private void cleanUp(Connection conn) throws SQLException {
//        PreparedStatement ps = conn.prepareStatement("DELETE usertype, username FROM users WHERE userid = ?");
//        ps.setInt(1, 10);
//        ps.execute();
//    }
}
