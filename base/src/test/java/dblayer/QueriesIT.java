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
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author Mart_
 */
public class QueriesIT {

    private static Queries query;
    private static HikariTestData con;

    public QueriesIT() {
    }

    @BeforeClass
    public static void setUpClass() {
        query = new Queries();
        con = new HikariTestData();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    public static int getUserData(Connection con, String type) throws SQLException {

        PreparedStatement ps = con.prepareStatement("SELECT Count(*) AS count FROM "+ type);
        ResultSet r = ps.executeQuery();
        r.next();
        int count = r.getInt("count");
        r.close();
        System.out.println("My Count is : " + count);
        return count;
    }

    public static void main(String[] args) throws SQLException {
        try (Connection con = HikariTestData.getConnection()) {
            getUserData(con, "users");
        }

    }

}
