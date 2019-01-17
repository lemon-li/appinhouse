package com.seasungames.appinhouse.routes.handlers;

import com.seasungames.appinhouse.application.APIConstant;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.routes.validations.impl.VersionValidationHandler;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.utils.PathUtils;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import javax.inject.Inject;

/**
 * Created by lile on 12/27/2018
 */
@AppInHouse
public class RouteVersionHandler extends BaseHandler {

    @Inject
    VersionService versionService;

    @Inject
    public RouteVersionHandler(Router router) {
        router.route(APIConstant.INDEX_VERSION).handler(this::index);

        router.get(APIConstant.API_GET_VERSIONS_LATEST)
            .handler(VersionValidationHandler.validateId())
            .handler(this::apiLatestVersion);
        router.get(APIConstant.API_GET_VERSIONS_HISTORY)
            .handler(VersionValidationHandler.validatePlatform())
            .handler(this::apiHistoryVersion);
        router.post(APIConstant.API_CREATE_VERSIONS)
            .handler(VersionValidationHandler.validateForm())
            .handler(this::apiCreateVersion);
        router.get(APIConstant.API_GET_VERSIONS_PLIST)
            .handler(VersionValidationHandler.validateVersion())
            .handler(this::getPlist);
    }

    private void index(RoutingContext rc) {
        rc.response().sendFile(PathUtils.getAssetsPath("/html/version.html"));
    }

    private void apiLatestVersion(RoutingContext rc) {
        String appId = rc.request().getParam("id");

        versionService.getLatestList(appId, resultVoidHandler(rc));
    }

    private void apiHistoryVersion(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");

        versionService.getPlatformList(appId, platform, resultVoidHandler(rc));
    }

    private void apiCreateVersion(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");
        String version = rc.request().getParam("version");
        String desc = rc.request().getParam("desc");
        String downloadUrl = rc.request().getParam("software_url");
        String jenkinsUrl = rc.request().getParam("url");
        String createTime = rc.request().getParam("time");

        VersionVo vo = new VersionVo(appId, platform, version,
            desc, downloadUrl, jenkinsUrl, createTime);

        if (vo.isIOS()) {
            String iosBundleId = rc.request().getParam("ios_bundle_id");
            String iosTitle = rc.request().getParam("ios_title");
            vo.setIOSBundleId(iosBundleId);
            vo.setIOSTitle(iosTitle);
        }

        versionService.createVersion(vo, resultVoidHandler(rc, HttpResponseStatus.CREATED.code()));
    }

    private void getPlist(RoutingContext rc) {
        String appId = rc.request().getParam("id");
        String platform = rc.request().getParam("platform");
        String version = rc.request().getParam("version");

        versionService.getPlist(appId, platform, version, resultXMLHandler(rc));
    }
}
