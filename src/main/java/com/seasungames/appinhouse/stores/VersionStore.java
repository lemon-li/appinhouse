package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.stores.services.version.models.VersionListResponseVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;

import java.util.List;

public interface VersionStore {

    VersionListResponseVo getPlatformList(String appId, String platform, String lastKey);

    List<VersionResponseVo> getLatestList(String appId);

    void createVersion(VersionVo vo);

    VersionVo getVersion(String appId, String platform, String version);
}
