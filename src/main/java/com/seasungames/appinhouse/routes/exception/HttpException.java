package com.seasungames.appinhouse.routes.exception;

import io.vertx.core.VertxException;

public abstract class HttpException extends VertxException {
    public final int status;
    private final String msg;

    protected HttpException(int status, String msg) {
        super(msg);
        this.status = status;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg != null ? msg : "Http exception : " + status;
    }
}
