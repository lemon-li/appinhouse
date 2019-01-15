package com.seasungames.appinhouse.services;

import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface AppService {

    void getAppsList(String lastKey, Handler<AsyncResult<AppListResponseVo>> resultHandler);

    void updateApps(AppVo appVo, Handler<AsyncResult<AppResponseVo>> resultHandler);

    void createApps(AppVo appVo, Handler<AsyncResult<Void>> resultHandler);

    void deleteApps(String id, Handler<AsyncResult<Void>> resultHandler);

    void getApps(String id, Handler<AsyncResult<AppResponseVo>> resultHandler);

}
