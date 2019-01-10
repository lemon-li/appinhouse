package com.seasungames.appinhouse.services;

import com.seasungames.appinhouse.models.VersionVo;
import com.seasungames.appinhouse.models.response.VersionResponseVo;

import java.util.List;

public interface VersionService {

    List<VersionResponseVo> getPlatformList(String appId, String platform);

    List<VersionResponseVo> getLatestList(String appId);

    int createVersion(VersionVo vo);

    String getPlist(String appId, String platform, String version);

}
