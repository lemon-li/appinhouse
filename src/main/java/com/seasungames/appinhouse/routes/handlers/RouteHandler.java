package com.seasungames.appinhouse.routes.handlers;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public abstract class RouteHandler {

    protected void toVoidResult(RoutingContext rc) {
        rc.response().setStatusCode(200).end();
    }

    protected void toDeleteResult(RoutingContext rc) {
        rc.response().setStatusCode(204).end();
    }

    protected void toBadRequest(RoutingContext rc, Throwable ex) {
        rc.response().setStatusCode(400)
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
    }

    protected void toFileResult(RoutingContext rc, String fileContent) {
        rc.response().setStatusCode(200)
                .putHeader("content-type", "application/x-plist; charset=utf-8")
                .end(fileContent);
    }

    protected void notFound(RoutingContext rc) {
        rc.response().setStatusCode(404)
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("message", "not_found").encodePrettily());
    }
}
