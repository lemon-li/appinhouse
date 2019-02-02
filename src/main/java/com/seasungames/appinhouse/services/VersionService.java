package com.seasungames.appinhouse.services;

import com.seasungames.appinhouse.stores.services.version.models.VersionListResponseVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * @author lile on 2019-01-04
 */
public interface VersionService {

    void getPlatformList(String appId, String platform, String lastKey, Handler<AsyncResult<VersionListResponseVo>> resultHandler);

    void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler);

    void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler);

    void getPlist(String appId, String platform, String version, Handler<AsyncResult<String>> resultHandler);
}
