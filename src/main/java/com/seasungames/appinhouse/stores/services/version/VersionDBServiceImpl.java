package com.seasungames.appinhouse.stores.services.version;

import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

/**
 * Created by lile on 1/15/2019
 */
public class VersionDBServiceImpl implements VersionDBService {

    @Override
    public void getPlatformList(String appId, String platform, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {

    }

    @Override
    public void getLatestList(String appId, Handler<AsyncResult<List<VersionResponseVo>>> resultHandler) {

    }

    @Override
    public void createVersion(VersionVo vo, Handler<AsyncResult<Void>> resultHandler) {

    }

    @Override
    public void getVersion(String appId, String platform, String version, Handler<AsyncResult<VersionVo>> resultHandler) {

    }

    @Override
    public void close(Handler<AsyncResult<Void>> resultHandler) {

    }
}
