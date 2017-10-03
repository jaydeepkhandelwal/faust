package com.mt.faust.comman;

/**
 * Created by jaydeep.k on 03/10/17.
 */

public class FaustException extends RuntimeException {
    private FaustErrorCodes errorCode;
    private static final long   serialVersionUID = -3853689944157298314L;

    public FaustException(FaustErrorCodes errorCode) {
        super(errorCode.getErrorString());
        this.errorCode = errorCode;
    }

    public FaustException(String s) {
        super(s);
    }

    public FaustException(int errorCode, String msg) {
        super(msg);
        if (errorCode >= 300 && errorCode <= 399) {
            this.errorCode = FaustErrorCodes.REDIRECTION_ERROR;
        } else if (errorCode >= 400 && errorCode <= 499) {
            this.errorCode = FaustErrorCodes.BAD_REQUEST_ERROR;
        } else if (errorCode >= 500 && errorCode <= 599) {
            this.errorCode = FaustErrorCodes.SERVER_ERROR;
        }
    }


    public FaustException(FaustErrorCodes errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public FaustException(FaustErrorCodes errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public FaustException(FaustErrorCodes errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public FaustErrorCodes getErrorCode() {
        return errorCode;
    }

    public Integer getStatusCode() {
        return this.errorCode.getStatusCode();
    }
}
