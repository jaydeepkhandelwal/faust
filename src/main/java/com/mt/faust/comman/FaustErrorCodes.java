package com.mt.faust.comman;

import lombok.Getter;

/**
 * Created by jaydeep.k on 03/10/17.
 */
@Getter
public enum FaustErrorCodes {


    DB_SET_INSTANCE_ERROR("DB_SET_INSTANCE_ERROR", "Error while creating c3p0 instance", 500),
    DB_SET_ERROR("DB_SET_ERROR:F-1001-04", "Error while updating cache",
            500),
    DB_GET_ERROR("DB_GET_ERROR:F-1001-05", "Error while updating cache",
            500),

    RECORD_NO_FOUND("RECORD_NO_FOUND:F-1001-07", "No Record found", 404),

    RECORD_SET_ERROR("RECORD_SET_ERROR:F-1001-08", "Error while store data",
            500),

    DB_DELETE_ERROR("DB_DELETE_ERROR:F-1001-13", "Error while deleting from DB",
            500),
    CONFIG_MISSING("CONFIG_MISSING:F-1001-14", "Some config is missing",
            500),
    BAD_REQUEST_ERROR("BAD_REQUEST_ERROR:F-9000-01", "Bad Request Error", 400),
    SERVER_ERROR("SERVER_ERROR:F-9000-02", "Server Error", 500),
    OBJECT_MAPPER_ERROR("OBJECT_MAPPER_ERROR:F-9000-06", "Object Mapper Error", 400),
    REDIRECTION_ERROR("REDIRECTION_ERROR:F-9000-03", "Redirection Error", 300);



    private String errorCode;
    private String errorString;
    private Integer statusCode;
    private FaustErrorCodes(String errorCode, String errorString, Integer statusCode) {
        this.errorCode = errorCode;
        this.errorString = errorString;
        this.statusCode = statusCode;
    }

}
