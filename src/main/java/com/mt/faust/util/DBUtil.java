package com.mt.faust.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mt.faust.comman.FaustErrorCodes;
import com.mt.faust.comman.FaustException;
import com.mt.faust.config.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jaydeep.k on 03/10/17.
 *
 */

@Slf4j
public class DBUtil {

    private static DBUtil INSTANCE = null;
    private ComboPooledDataSource cpds;

    private DBUtil(DatabaseConfig databaseConfig) throws SQLException, ClassNotFoundException {
        log.debug("Entering the constructor of DBUtil");
        Class.forName("com.mysql.jdbc.Driver");
        cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl(databaseConfig.getJdbcUrl());
        cpds.setUser(databaseConfig.getUsername());
        cpds.setPassword(databaseConfig.getPassword());
        cpds.setTestConnectionOnCheckin(databaseConfig.getTestConnectionOnCheckin());
        cpds.setIdleConnectionTestPeriod(databaseConfig.getIdleConnectionTestPeriod());
        cpds.setTestConnectionOnCheckout(databaseConfig.getTestConnectionOnCheckout());
        cpds.setMinPoolSize(databaseConfig.getMinPoolSize());
        cpds.setMaxPoolSize(databaseConfig.getMaxPoolSize());
        cpds.setInitialPoolSize(databaseConfig.getInitialPoolSize());
        cpds.setAcquireIncrement(databaseConfig.getAcquireIncrement());
        cpds.setMaxIdleTime(databaseConfig.getMaxIdleTime());
        cpds.setMaxIdleTimeExcessConnections(databaseConfig.getMaxIdleTimeExcessConnections());
        cpds.setUnreturnedConnectionTimeout(databaseConfig.getUnreturnedConnectionTimeout());
        cpds.setAcquireRetryAttempts(databaseConfig.getAcquireRetryAttempts());

        log.debug("Exiting the constructor of DBUtils");
    }


    public synchronized static void init(final DatabaseConfig databaseConfig) {
        if(INSTANCE == null) {
            try {
                INSTANCE = new DBUtil(databaseConfig);
            } catch (ClassNotFoundException | SQLException e) {
                log.error("Failed to initialize DButil exception -> {}", e);
                throw new FaustException(FaustErrorCodes.DB_SET_INSTANCE_ERROR);
            }
        }
    }

    public static DBUtil getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else {
            log.error("Failed to get DButil instance");
            throw new FaustException(FaustErrorCodes.DB_SET_INSTANCE_ERROR);
        }
    }
    public Connection getConnection() throws SQLException {
        if (cpds != null) {
            return cpds.getConnection();
        } else {
            log.error("Can't create connection, cpds is not initialized.");
            return null;
        }
    }

}
