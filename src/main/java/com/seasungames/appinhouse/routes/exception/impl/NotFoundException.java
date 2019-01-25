package com.seasungames.appinhouse.routes.exception.impl;

import com.seasungames.appinhouse.application.Errors;
import com.seasungames.appinhouse.routes.exception.HttpException;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * Created by lile on 1/7/2019
 */
public class NotFoundException extends HttpException {

    public NotFoundException() {
        super(HttpResponseStatus.NOT_FOUND.code(), Errors.NOT_FOUND_ERROR_TYPE);
    }

    public NotFoundException(String resourceName) {
        super(HttpResponseStatus.NOT_FOUND.code(), resourceName);
    }
}
