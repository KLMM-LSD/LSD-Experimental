/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblayer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Lasse
 */
public class HikariCPDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/lsd?zeroDateTimeBehaviour=convertToNull&serverTimezone=UTC&useSSL=false");
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
        config.setMaxLifetime(120 * 1000);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);

    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();

    }

    private HikariCPDataSource() {
    }
}
