package com.seasungames.appinhouse.stores.services.app;

import com.seasungames.appinhouse.stores.services.app.models.AppListResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppResponseVo;
import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * @author lile on 2019-01-15
 */
@ProxyGen
public interface AppDBService {

    void getAppsList(String lastKey, Handler<AsyncResult<AppListResponseVo>> resultHandler);

    void updateApps(AppVo vo, Handler<AsyncResult<AppResponseVo>> resultHandler);

    void createApps(AppVo vo, Handler<AsyncResult<Void>> resultHandler);

    void deleteApps(String appId, Handler<AsyncResult<Void>> resultHandler);

    void getApps(String appId, Handler<AsyncResult<AppResponseVo>> resultHandler);

    @ProxyClose
    void close(Handler<AsyncResult<Void>> resultHandler);
}
