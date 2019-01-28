package com.seasungames.appinhouse.stores.services.version;

import com.seasungames.appinhouse.stores.services.version.models.VersionListResponseVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * Created by lile on 1/15/2019
 */
@ProxyGen
public interface VersionDBService {

    void getPlatformList(String appId, String platform, String lastKey, Handler<AsyncResult<VersionListResponseVo>> resultHandler);

    void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler);

    void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler);

    void getVersion(String appId, String platform, String version, Handler<AsyncResult<VersionVo>> resultHandler);

    @ProxyClose
    void close(Handler<AsyncResult<Void>> resultHandler);
}
