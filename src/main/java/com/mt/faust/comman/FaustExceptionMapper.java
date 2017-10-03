package com.mt.faust.comman;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.TimeoutException;

/**
 * Created by jaydeep.k on 03/10/17.
 */

@Provider
public class FaustExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        if (exception instanceof FaustException) {
            Integer statusCode = ((FaustException) exception).getErrorCode().getStatusCode();
            String errorMessage = new FlurryError(exception.getMessage()).toString();
            return Response.status(statusCode).entity(errorMessage).type(MediaType.APPLICATION_JSON)
                .build();
        } else if (exception instanceof TimeoutException) {
            return Response.status(Response.Status.GATEWAY_TIMEOUT).entity("{\"error\":\"Downstream service timeout..\"}").build();
        }
        return null;
    }


}

class FlurryError {

    private String message;

    public FlurryError(String message) {
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"error\" :" + "\"" + message + "\"}";
    }
}
