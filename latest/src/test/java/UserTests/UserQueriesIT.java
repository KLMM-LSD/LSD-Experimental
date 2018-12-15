/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserTests;

import dblayer.UserQueries;
import java.sql.SQLException;
import org.junit.Test;

/**
 *
 * @author Micha
 */
public class UserQueriesIT {
    
    @Test
    public void getUserPageTest() throws SQLException{
        UserQueries.getUserpage(1);
        //TODO queri db test
    }
}
