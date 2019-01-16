package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.models.ResponseVo;
import com.seasungames.appinhouse.routes.exception.HttpException;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.serviceproxy.ServiceException;

/**
 * Created by lile on 1/7/2019
 */
public class RouteFailureHandler implements Handler<RoutingContext> {

    private static final Logger LOG = LoggerFactory.getLogger(RouteFailureHandler.class);

    @Override
    public void handle(RoutingContext rc) {
        Throwable failure = rc.failure();

        if (failure instanceof ValidationException) {
            toResponseJson(rc, HttpResponseStatus.BAD_REQUEST.code(), errorMessageToErrorBody("Validation failed"));
        } else if (failure instanceof HttpException) {
            HttpException httpException = ((HttpException) failure);
            toResponseJson(rc, httpException.status, errorMessageToErrorBody(httpException.getMessage()));
        } else if (failure instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) failure;
            toResponseJson(rc, HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), errorMessageToErrorBody(serviceException.getMessage()));
        } else {
            toResponseJson(rc, HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), errorMessageToErrorBody(failure.getMessage()));
        }
        failure.printStackTrace();
        LOG.error("Error - {}: {}", failure.getClass().getSimpleName(), failure.getMessage());
    }

    public void toResponseJson(RoutingContext rc, int statusCode, String body) {
        rc.response().putHeader("Content-Type", "application/json; charset=utf-8");
        rc.response().setStatusCode(statusCode)
            .end(body);
    }

    private String errorMessageToErrorBody(String message) {
        return new ResponseVo().setMessage(message).toJson();
    }
}
