package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.models.response.VersionResponseVo;

import java.util.List;

public interface VersionStore {

    List<VersionResponseVo> getPlatformList(String appId, String platform);

    List<VersionResponseVo> getLatestList(String appId);

    void createVersion(VersionVo vo);

    VersionVo getVersion(String appId, String platform, String version);

}
