package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;

import java.util.List;

public interface VersionStore {

    List<VersionResponseVo> getPlatformList(String appId, String platform);

    List<VersionResponseVo> getLatestList(String appId);

    void createVersion(VersionVo vo);

    VersionVo getVersion(String appId, String platform, String version);

}
