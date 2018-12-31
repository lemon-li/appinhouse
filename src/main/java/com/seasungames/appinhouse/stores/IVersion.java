package com.seasungames.appinhouse.stores;

import com.seasungames.appinhouse.models.VersionVo;

import java.util.List;

public interface IVersion {

    List<VersionVo> GetPlatformList(String appId, String platform);

    List<VersionVo> GetLatestList(String appId);

    void CreateVersion(VersionVo vo);

}
