package com.seasungames.appinhouse.stores.services.app;

import com.seasungames.appinhouse.models.AppVo;
import com.seasungames.appinhouse.models.response.AppListResponseVo;
import com.seasungames.appinhouse.models.response.AppResponseVo;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lile on 1/15/2019
 */
@ProxyGen
public interface AppDBService {

    String SERVICE_ADDRESS = "com.seasungames.appinhouse.app-dynamodb-service";

    void getAppsList(String lastKey, Handler<AsyncResult<AppListResponseVo>> resultHandler);

    void updateApps(AppVo vo, Handler<AsyncResult<AppResponseVo>> resultHandler);

    void createApps(AppVo vo, Handler<AsyncResult<Void>> resultHandler);

    void deleteApps(String appId, Handler<AsyncResult<Void>> resultHandler);

    void getApps(String appId, Handler<AsyncResult<AppResponseVo>> resultHandler);

    @ProxyClose
    void close(Handler<AsyncResult<Void>> resultHandler);
}
