package com.seasungames.appinhouse.routes.handlers;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.seasungames.appinhouse.models.ResponseVo;
import com.seasungames.appinhouse.routes.exception.HttpException;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.validation.ValidationException;

import static com.seasungames.appinhouse.utils.RestApiUtils.toResponseJson;

/**
 * Created by lile on 1/7/2019
 */
public class RouteFailureHandler implements Handler<RoutingContext> {

    private static final Logger LOG = LoggerFactory.getLogger(RouteFailureHandler.class);

    @Override
    public void handle(RoutingContext rc) {
        Throwable failure = rc.failure();

        if (failure instanceof ValidationException) {
            toResponseJson(rc, 400, errorMessageToErrorBody("Validation failed"));
        } else if (failure instanceof AmazonDynamoDBException) {
            toResponseJson(rc, 400, errorMessageToErrorBody(failure.getMessage()));
        } else if (failure instanceof HttpException) {
            toResponseJson(rc, ((HttpException) failure).status, errorMessageToErrorBody(failure.getMessage()));
        } else {
            toResponseJson(rc, 500, errorMessageToErrorBody(failure.getMessage()));
        }

        LOG.error("RouteError - {}: {}", failure.getClass().getSimpleName(), failure.getMessage());
    }

    private String errorMessageToErrorBody(String message) {
        return new ResponseVo().setMessage(message).toJson();
    }
}
