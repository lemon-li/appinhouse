package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.models.ResponseVo;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by lile on 1/15/2019
 */
public abstract class BaseHandler {

    protected <T> Handler<AsyncResult<T>> resultVoidHandler(RoutingContext context) {
        return ar -> {
            if (ar.succeeded()) {
                context.response()
                    .setStatusCode(HttpResponseStatus.OK.code())
                    .putHeader("content-type", "application/json")
                    .end(new ResponseVo<T>().setData(ar.result()).toJson());
            } else {
                context.fail(ar.cause());
            }
        };
    }

    protected Handler<AsyncResult<Void>> resultVoidHandler(RoutingContext context, int status) {
        return ar -> {
            if (ar.succeeded()) {
                context.response()
                    .setStatusCode(status == 0 ? HttpResponseStatus.OK.code() : status)
                    .putHeader("content-type", "application/json")
                    .end();
            } else {
                context.fail(ar.cause());
            }
        };
    }

    protected Handler<AsyncResult<String>> resultXMLHandler(RoutingContext context) {
        return ar -> {
            if (ar.succeeded()) {
                context.response()
                    .setStatusCode(HttpResponseStatus.OK.code())
                    .putHeader("content-type", "application/x-plist; charset=utf-8")
                    .end(ar.result());
            } else {
                context.fail(ar.cause());
            }
        };
    }

    protected void toResponseJson(RoutingContext rc, int statusCode, String message) {
        rc.response()
            .putHeader("Content-Type", "application/json; charset=utf-8")
            .setStatusCode(statusCode)
            .end(new ResponseVo().setMessage(message).toJson());
    }
}
