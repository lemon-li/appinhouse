package com.seasungames.appinhouse.routes;

import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.routes.exception.BadRequestException;
import com.seasungames.appinhouse.routes.handlers.RouteFailureHandler;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import com.seasungames.appinhouse.routes.handlers.RouteAppHandler;
import com.seasungames.appinhouse.routes.handlers.RouteVersionHandler;

import javax.inject.Inject;

/***
 RESTful API

 GET:     Used for retrieving resources.
 POST:    Used for creating resources.
 PATCH:   Used for updating resources with partial JSON data. For instance,
 an Issue resource has title and body attributes. A PATCH request may accept one or more of the
 attributes to update the resource. PATCH is a relatively new and uncommon HTTP verb, so resource endpoints also accept POST requests.

 PUT:     Used for replacing resources or collections. For PUT requests with no body attribute, be sure to set the Content-Length header to zero.
 DELETE:  Used for deleting resources.
 ***/
public class RoutesManager {

    private static final Logger log = LoggerFactory.getLogger(RoutesManager.class);

    @Inject
    Router router;

    @Inject
    RouteAppHandler appHandler;

    @Inject
    RouteVersionHandler versionHandler;

    public Router getRouter() {
        return this.router;
    }

    public void setRoutes() {
        router.route(APIConstant.INDEX).handler(rc ->
                rc.response().sendFile(PathUtils.getAssetsPath("/index.html")));

        router.route().last().handler(rc -> rc.fail(new BadRequestException()));
        router.route().failureHandler(new RouteFailureHandler());
    }
}
