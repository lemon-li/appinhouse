package com.seasungames.appinhouse.services;

import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;

import java.util.List;

public interface VersionService {

    List<VersionResponseVo> getPlatformList(String appId, String platform);

    List<VersionResponseVo> getLatestList(String appId);

    void createVersion(VersionVo vo);

    String getPlist(String appId, String platform, String version);

}
