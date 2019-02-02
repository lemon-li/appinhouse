package com.seasungames.appinhouse.routes.handlers.impl;

import com.seasungames.appinhouse.application.Errors;
import com.seasungames.appinhouse.routes.handlers.BaseHandler;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * @author lile on 2019-01-28
 */
public class RouteFilterHandler extends BaseHandler implements Handler<RoutingContext> {

    public static RouteFilterHandler create(String secretKey) {
        return new RouteFilterHandler(secretKey);
    }

    private final String secretKey;

    public RouteFilterHandler(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void handle(RoutingContext rc) {
        String receiveSecretKey = rc.request().getHeader("X-SecretKey");
        if (receiveSecretKey.equals(secretKey)) {
            rc.next();
        } else {
            toResponseJson(rc, HttpResponseStatus.FORBIDDEN.code(), Errors.FORBIDDEN_ERROR_TYPE);
        }
    }
}
