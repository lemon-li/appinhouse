package com.seasungames.appinhouse.stores.services.version;

import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.models.response.VersionResponseVo;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * Created by lile on 1/15/2019
 */
public interface VersionDBService {

    String SERVICE_ADDRESS = "com.seasungames.appinhouse.version-dynamodb-service";

    void getPlatformList(String appId, String platform, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler);

    void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler);

    void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler);

    void getVersion(String appId, String platform, String version, Handler<AsyncResult<VersionVo>> resultHandler);

    @ProxyClose
    void close(Handler<AsyncResult<Void>> resultHandler);
}
