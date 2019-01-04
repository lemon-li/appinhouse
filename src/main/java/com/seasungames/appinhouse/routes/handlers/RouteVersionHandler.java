package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.utils.PathUtils;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by lile on 12/27/2018
 */
public class RouteVersionHandler extends RouteHandler {

    private final VersionService versionService;

    public RouteVersionHandler(VersionService versionService) {
        this.versionService = versionService;
    }

    public void index(RoutingContext rc) {
        rc.response()
                .sendFile(PathUtils.getAssetsPath("/assets/html/version.html"));
    }

    public void apiLatestVersion(RoutingContext rc) {
        try {
            String appId = rc.request().getParam("id");
            rc.response().setStatusCode(200).end(versionService.getLatestList(appId));
        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }

    public void apiHistoryVersion(RoutingContext rc) {
        try {
            String appId = rc.request().getParam("id");
            String platform = rc.request().getParam("platform");
            rc.response().setStatusCode(200).end(versionService.getPlatformList(appId, platform));

        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }

    public void apiCreateVersion(RoutingContext rc) {
        try {
            String appId = rc.request().getParam("id");
            String platform = rc.request().getParam("platform");
            String version = rc.request().getParam("version");
            String desc = rc.request().getParam("desc");
            String download_url = rc.request().getParam("software_url");
            String jenkins_url = rc.request().getParam("url");
            String create_time = rc.request().getParam("time");

            VersionVo vo = new VersionVo(appId, platform, version,
                    desc, download_url, jenkins_url, create_time);

            if (vo.isIOS()) {
                String ios_bundle_id = rc.request().getParam("ios_bundle_id");
                String ios_title = rc.request().getParam("ios_title");
                vo.setIos_bundle_id(ios_bundle_id).setIos_title(ios_title);
            }

            versionService.createVersion(vo);
            toVoidResult(rc);
        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }

    public void getPlist(RoutingContext rc) {
        try {
            String appId = rc.request().getParam("id");
            String platform = rc.request().getParam("platform");
            String version = rc.request().getParam("version");
            toFileResult(rc, versionService.getPlist(appId, platform, version));
        } catch (Exception e) {
            toBadRequest(rc, e);
        }
    }
}
