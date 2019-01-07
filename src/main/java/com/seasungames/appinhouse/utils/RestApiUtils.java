package com.seasungames.appinhouse.utils;

import io.vertx.ext.web.RoutingContext;

/**
 * Created by lile on 1/7/2019
 */
public class RestApiUtils {

    public static void toResponseJson(RoutingContext rc, int statusCode, String body) {
        rc.response().putHeader("Content-Type", "application/json; charset=utf-8");
        rc.response().setStatusCode(statusCode)
                .end(body);
    }

    public static void toResponseJson(RoutingContext rc, int statusCode) {
        toResponseJson(rc, statusCode, "");
    }

    public static void toResponseXML(RoutingContext rc, int statusCode, String body) {
        rc.response().setStatusCode(200)
                .putHeader("content-type", "application/x-plist; charset=utf-8")
                .end(body);
    }
}
