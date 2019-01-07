package com.seasungames.appinhouse.routes.handlers;

import com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException;
import com.seasungames.appinhouse.routes.exception.ResourceNotFoundException;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.api.validation.ValidationException;

import static com.seasungames.appinhouse.utils.RestApiUtils.restResponse;

/**
 * Created by lile on 1/7/2019
 */
public class RouteFailureHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext routingContext) {
        Throwable failure = routingContext.failure();
        if (failure instanceof ValidationException) {
            restResponse(routingContext, 400, errorMessageToErrorBody("Validation failed"));
        } else if (failure instanceof AmazonDynamoDBException) {
            restResponse(routingContext, 400, errorMessageToErrorBody("DB Exception"));
        } else if (failure instanceof ResourceNotFoundException) {
            restResponse(routingContext, 404, errorMessageToErrorBody(failure.getMessage()));
        } else {
            restResponse(routingContext, 500, errorMessageToErrorBody(failure.getMessage()));
        }
    }

    private String errorMessageToErrorBody(String message) {
        return new JsonObject().put("message", message).toString();
    }
}
