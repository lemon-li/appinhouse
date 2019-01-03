package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.models.VersionVo;

public interface Version {

    String getPlatformList(String appId, String platform);

    String getLatestList(String appId);

    int createVersion(VersionVo vo);

    VersionVo getOneApp(String appId, String platform, String version);

}
