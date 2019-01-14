package com.seasungames.appinhouse.stores.services;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.models.response.AppListResponseVo;
import com.seasungames.appinhouse.models.response.AppResponseVo;
import com.seasungames.appinhouse.models.response.VersionResponseVo;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * Created by lile on 1/14/2019
 */

@ProxyGen
public interface DynamoDBService {

    String SERVICE_ADDRESS = "com.seasungames.appinhouse.dynamodb-service";

    void getAppsList(String lastKey, Handler<AsyncResult<AppListResponseVo>> resultHandler);

    void updateApps(AppVo vo, Handler<AsyncResult<AppResponseVo>> resultHandler);

    void createApps(AppVo vo, Handler<AsyncResult<Void>> resultHandler);

    void deleteApps(String appId, Handler<AsyncResult<Void>> resultHandler);

    void getApps(String appId, Handler<AsyncResult<AppResponseVo>> resultHandler);

    void getPlatformList(String appId, String platform, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler);

    void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler);

    void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler);

    void getVersion(String appId, String platform, String version, Handler<AsyncResult<VersionVo>> resultHandler);

    @ProxyClose
    void close(Handler<AsyncResult<Void>> resultHandler);
}
