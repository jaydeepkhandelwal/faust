package com.mt.faust.config;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaydeep.k on 03/10/17.
 */
@Getter
@Setter
public class DatabaseConfig {
    private String jdbcUrl;
    private String url;
    private String driverClass;
    private String username;
    private String password;
    private Integer acquireIncrement;
    private Integer maxIdleTime;
    private Integer maxIdleTimeExcessConnections;
    private Integer minPoolSize;
    private Integer maxPoolSize;
    private Integer unreturnedConnectionTimeout;
    private Integer idleConnectionTestPeriod;
    private Boolean testConnectionOnCheckin;
    private Boolean testConnectionOnCheckout;
    private Integer initialPoolSize;
    private Integer acquireRetryAttempts;
}
