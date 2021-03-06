/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import dblayer.Queries;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Lasse
 */
public class WorkerThread extends Thread {

    private static final Logger log = Logger.getLogger(Queries.class.getName());
    
    @Override
    public void run() {
        for (;;) {
            try {
                workerLoop();
            } catch (InterruptedException ex) {
                log.debug("Interruped Exception :" + ex.getMessage());
            } catch (SQLException ex) {
                log.debug("SQL Exception : " + ex.getMessage());
            }
        }
    }

    public void workerLoop() throws InterruptedException, SQLException {
        Thread.sleep(1000);
        Node n = Node.popList();
        processPosts(n);
    }

    public static void processPosts(Node n) throws SQLException {
        if (n == null) {
            return;
        }
        Queries.pushList(n);
    }

}
