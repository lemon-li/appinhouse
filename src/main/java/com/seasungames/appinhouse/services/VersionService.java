package com.seasungames.appinhouse.services;

import com.seasungames.appinhouse.models.VersionVo;

public interface VersionService {

    String getPlatformList(String appId, String platform);

    String getLatestList(String appId);

    int createVersion(VersionVo vo);

    String getPlist(String appId, String platform, String version);

}
