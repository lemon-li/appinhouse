package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.models.ResponseVo;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
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
                internalError(context, ar.cause());
                ar.cause().printStackTrace();
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
                internalError(context, ar.cause());
                ar.cause().printStackTrace();
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
                internalError(context, ar.cause());
                ar.cause().printStackTrace();
            }
        };
    }

    protected void internalError(RoutingContext context, Throwable ex) {
        context.response().setStatusCode(500)
            .putHeader("content-type", "application/json")
            .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
    }

}
