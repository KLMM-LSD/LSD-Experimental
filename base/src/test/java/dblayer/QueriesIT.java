package dblayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import queue.Node;

/**
 *
 * @author Mart_
 */
public class QueriesIT {

    private static Queries query;
    private static HikariTestData con;
    private static String testUser;
    private static String testPost;

    public QueriesIT() {
    }

    @BeforeClass
    public static void setUpClass() {
        query = new Queries();
        testUser = "{'userid': 99999,'usertype' : 'user','username' : 'Essal','pwd_hash' : 'weqwe123'}";
        testPost = "{'post_title':'A good Post','post_type':'story','hanesst_id' : 99999,'username':'test0','pwd_hash':'123','post_url':'google.com'}";
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        try (Connection conn = HikariTestData.getConnection()) {
            cleanUp(conn);
        }
    }

    @Ignore
    @Test
    public void testUserInsert() throws SQLException {
        try (Connection conn = HikariTestData.getConnection()) {
            int before = getTestData(conn, "users");
            Node n = new Node(testUser, Node.KIND.SIGNUP);
            Queries.insertUser(conn, n);
            assertTrue(before < getTestData(conn, "users"));
        }
    }
    
    @Ignore
    @Test
    public void testPostInsert() throws SQLException {
        try (Connection conn = HikariTestData.getConnection()) {
            int before = getTestData(conn, "posts");
            System.out.println(before);
            Node n = new Node(testPost, Node.KIND.POST);
            Queries.insertPost(conn, n);
            System.out.println(getTestData(conn, testPost));
            assertTrue(before < getTestData(conn, "posts"));
        }
    }

    public static int getTestData(Connection con, String type) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT Count(*) AS count FROM " + type);
        ResultSet r = ps.executeQuery();
        r.next();
        int count = r.getInt("count");
        r.close();
        return count;
    }

    private static void cleanUp(Connection con) throws SQLException {
        PreparedStatement s1 = con.prepareStatement("DELETE FROM users WHERE userid = ?");
        PreparedStatement s2 = con.prepareStatement("DELETE FROM posts WHERE postid = ?");
        s1.setInt(1, 99999);
        s2.setInt(1, 99999);

        s1.execute();
        s2.execute();
    }

}
