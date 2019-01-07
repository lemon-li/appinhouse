package com.seasungames.appinhouse.routes.exception;

/**
 * Created by lile on 1/7/2019
 */
public class BadRequestException extends HttpException {

    public BadRequestException() {
        super(400, "Bad request");
    }

    public BadRequestException(String msg) {
        super(400, "Bad request. " + msg);
    }
}
