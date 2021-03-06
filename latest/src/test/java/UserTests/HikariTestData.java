/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserTests;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Micha
 */
public class HikariTestData {
    private static final Logger logger = Logger.getLogger(HikariTestData.class);
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        logger.debug("Connecting");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/lsdtest?zeroDateTimeBehaviour=convertToNull&serverTimezone=UTC&useSSL=false");
        config.setUsername("root");
        config.setPassword("root");

        /* maximum time (in milliseconds) that a call to getConnection() will
           wait for a connection before timing out. */
        config.setConnectionTimeout(30 * 1000);

        /* This property controls the maximum amount of time (in milliseconds)
           that a connection is allowed to sit idle in the pool. */
        config.setIdleTimeout(60 * 1000);

        /* This property controls the maximum lifetime of a connection in
           the pool. */
        config.setMaxLifetime(60 * 1000);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
        
        logger.trace(config);
        logger.trace(ds);
    }

    public static Connection getConnection() {
        try {
            logger.info(ds + " has connected.");
            return ds.getConnection();
        } catch(SQLException e) {
            logger.error("Didnt' connect " + e);
        }
        return null;
    }

    HikariTestData() {
    }
}
