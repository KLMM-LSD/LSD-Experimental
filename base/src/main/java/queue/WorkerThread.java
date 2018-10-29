/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queue;

import dblayer.Queries;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lasse
 */
public class WorkerThread extends Thread {

    @Override
    public void run() {
        for (;;) {
            try {
                workerLoop();
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void workerLoop() throws InterruptedException, SQLException {
        Thread.sleep(1000);
        Node n = Node.popList();
        processPosts(n);
    }

    public static void processPosts(Node n) throws SQLException {
        long before, after;
        if (n == null) {
            return;
        }

        Queries.pushList(n);
    }

}
