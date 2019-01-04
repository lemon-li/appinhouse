package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.services.impl.AppServiceImpl;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by lile on 12/27/2018
 */
public class RouteAppHandler extends RouteHandler {

    private final AppService appService;

    public RouteAppHandler(AppService appService) {
        this.appService = appService;
    }

    public void index(RoutingContext rc) {
        rc.response()
                .sendFile(PathUtils.getAssetsPath("/assets/html/app.html"));
    }

    /**
     * API
     */
    public void apiGetApps(RoutingContext rc) {
        try {
            rc.response().setStatusCode(200).end(appService.getAppsList());
        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }

    public void apiGetApp(RoutingContext rc) {
        try {
            String appId = rc.request().getParam("id");
            appService.getApps(appId);
            toVoidResult(rc);
        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }

    public void apiCreateApps(RoutingContext rc) {
        try {
            String appId = rc.request().getParam("id");
            String desc = rc.request().getParam("desc");
            String alias = rc.request().getParam("alias");

            if (appId.isEmpty() || desc.isEmpty() || alias.isEmpty()) {
                toBadRequest(rc, new IllegalStateException("apiCreateApps"));
            } else {
                appService.createApps(appId, desc, alias);
                toVoidResult(rc);
            }
        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }

    public void apiUpdateApps(RoutingContext rc) {
        try {
            String appId = rc.request().getParam("id");
            String desc = rc.request().getParam("desc");
            String alias = rc.request().getParam("alias");

            if (appId.isEmpty() || desc.isEmpty() || alias.isEmpty()) {
                toBadRequest(rc, new IllegalStateException("apiUpdateApps"));
            } else {
                appService.updateApps(appId, desc, alias);
                toVoidResult(rc);
            }
        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }

    public void apiDeleteApps(RoutingContext rc) {
        try {
            String appId = rc.request().getParam("id");
            appService.deleteApps(appId);
            toDeleteResult(rc);
        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }
}
