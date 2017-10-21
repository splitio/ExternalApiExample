package io.split.splitapiexample.client.util;

public class ResponseStatusError extends RuntimeException {
    private final int code;
    private final String message;
    private final String transactionId;

    public ResponseStatusError(APIMessage message) {
        super(message.toString());
        this.code = message.getCode();
        this.message = message.getMessage();
        this.transactionId = message.getTransactionId();
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public String tid() {
        return transactionId;
    }
}
