package com.seasungames.appinhouse.stores.services;

import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.models.response.AppListResponseVo;
import com.seasungames.appinhouse.models.response.AppResponseVo;
import com.seasungames.appinhouse.models.response.VersionResponseVo;
import com.seasungames.appinhouse.stores.AppStore;
import com.seasungames.appinhouse.stores.VersionStore;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lile on 1/14/2019
 */
@AppInHouse
public class DynamoDBServiceImpl implements DynamoDBService {

    @Inject
    AppStore appTable;

    @Inject
    VersionStore versionTable;

    @Inject
    public DynamoDBServiceImpl() {

    }

    @Override
    public void getAppsList(String lastKey, Handler<AsyncResult<AppListResponseVo>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(appTable.getAppsList(lastKey)));
    }

    @Override
    public void updateApps(AppVo vo, Handler<AsyncResult<AppResponseVo>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(appTable.updateApps(vo)));
    }

    @Override
    public void createApps(AppVo vo, Handler<AsyncResult<Void>> resultHandler) {
        appTable.createApps(vo);
        resultHandler.handle(Future.succeededFuture());
    }

    @Override
    public void deleteApps(String appId, Handler<AsyncResult<Void>> resultHandler) {
        appTable.deleteApps(appId);
        resultHandler.handle(Future.succeededFuture());
    }

    @Override
    public void getApps(String appId, Handler<AsyncResult<AppResponseVo>> resultHandler) {
        appTable.getApps(appId);
        resultHandler.handle(Future.succeededFuture());
    }

    @Override
    public void getPlatformList(String appId, String platform, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(versionTable.getPlatformList(appId, platform)));
    }

    @Override
    public void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(versionTable.getLatestList(appId)));
    }

    @Override
    public void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler) {
        versionTable.createVersion(vo);
        resultHandler.handle(Future.succeededFuture());
    }

    @Override
    public void getVersion(String appId, String platform, String version, Handler<AsyncResult<VersionVo>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(versionTable.getVersion(appId, platform, version)));
    }

    @Override
    public void close(Handler<AsyncResult<Void>> resultHandler) {
        resultHandler.handle(Future.succeededFuture());
    }
}
