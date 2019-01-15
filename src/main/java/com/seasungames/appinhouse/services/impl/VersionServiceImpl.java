package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import com.seasungames.appinhouse.stores.services.app.AppDBService;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;
import com.seasungames.appinhouse.stores.services.version.VersionDBService;
import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;
import com.seasungames.appinhouse.routes.exception.impl.NotFoundException;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.utils.PlistUtils;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by lile on 1/4/2019
 */
@AppInHouse
public class VersionServiceImpl implements VersionService {

    @Named("APP_DB_PROXY")
    @Inject
    AppDBService appDBServiceProxy;

    @Named("VERSION_DB_PROXY")
    @Inject
    VersionDBService versionDBServiceProxy;

    @Inject
    public VersionServiceImpl() {

    }

    @Override
    public void getPlatformList(String appId, String platform,
                                Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {
        versionDBServiceProxy.getPlatformList(appId, platform, resultHandler);
    }

    @Override
    public void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {
        versionDBServiceProxy.getLatestList(appId, resultHandler);
    }

    @Override
    public void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler) {
        Future<AppResponseVo> future = Future.future();
        future.compose(appResponseVo ->
            Future.<Void>future(f -> versionDBServiceProxy.createVersion(vo, f))
        ).setHandler(resultHandler);

        appDBServiceProxy.getApps(vo.getAppId(), future);
    }

    @Override
    public void getPlist(String appId, String platform, String version, Handler<AsyncResult<String>> resultHandler) {
        versionDBServiceProxy.getVersion(appId, platform, version, ar -> {
            VersionVo vo = ar.result();
            if (vo != null) {
                String result = PlistUtils.genPlist(vo.getDownloadUrl(), vo.getIosBundleId(), vo.getIosTitle());
                resultHandler.handle(Future.succeededFuture(result));
            } else {
                resultHandler.handle(Future.failedFuture(new NotFoundException(String.format("The Version not found , id : %s, platform : %s, version : %s",
                    appId, platform, version))));
            }
        });
    }
}
