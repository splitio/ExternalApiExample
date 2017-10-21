package io.split.splitapiexample.client.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.Response;

public class APIMessage {

    private final int code;
    private final String message;
    private final String details;
    private final String transactionId;

    public APIMessage(String message) {
        this(Response.Status.OK, message);
    }

    public APIMessage(Response.StatusType status, String message) {
        this(status.getStatusCode(), message, null, null);
    }

    public APIMessage(int code, String message) {
        this(code, message, null, null);
    }

    public APIMessage(int code, String message, String transactionId) {
        this(code, message, null, transactionId);
    }

    @JsonCreator
    public APIMessage(@JsonProperty("code") int code,
                      @JsonProperty("message") String message,
                      @JsonProperty("details") String details,
                      @JsonProperty("tid") String transactionId) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return String.format("[code=%s][tid=%s] %s", code, transactionId, message);
    }
    public Integer getCode() {
        return code;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}
