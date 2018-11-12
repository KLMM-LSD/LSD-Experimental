
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mart_
 */
public class TestLog {
        private static Logger log = Logger.getLogger(TestLog.class.getName());    

        public static void main(String[] args) {        
            PropertyConfigurator.configure("log4j.properties");;
            log.debug("hej");
            log.info("hej");
    }
        
}
