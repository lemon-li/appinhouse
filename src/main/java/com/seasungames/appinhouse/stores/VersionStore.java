package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.models.VersionVo;
import io.vertx.core.json.JsonObject;

import java.util.List;

public interface VersionStore {

    List<JsonObject> getPlatformList(String appId, String platform);

    List<JsonObject> getLatestList(String appId);

    int createVersion(VersionVo vo);

    VersionVo getOneApp(String appId, String platform, String version);

}
