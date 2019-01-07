package com.seasungames.appinhouse.routes.exception;

public abstract class HttpException extends RuntimeException {
    public final int status;
    private final String msg;

    protected HttpException(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg != null ? msg : "Http exception : " + status;
    }
}
