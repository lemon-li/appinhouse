package com.seasungames.appinhouse.routes.exception.impl;

import com.seasungames.appinhouse.application.Errors;
import com.seasungames.appinhouse.routes.exception.HttpException;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * Created by lile on 1/7/2019
 */
public class BadRequestException extends HttpException {

    public BadRequestException() {
        super(HttpResponseStatus.BAD_REQUEST.code(), Errors.CLIENT_ERROR_TYPE);
    }

    public BadRequestException(String msg) {
        super(HttpResponseStatus.BAD_REQUEST.code(), msg);
    }
}
