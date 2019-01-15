package com.seasungames.appinhouse.services.impl;

import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import com.seasungames.appinhouse.stores.services.version.models.VersionVo;
import com.seasungames.appinhouse.stores.services.version.models.VersionResponseVo;
import com.seasungames.appinhouse.routes.exception.impl.NotFoundException;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.stores.VersionStore;
import com.seasungames.appinhouse.utils.PlistUtils;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lile on 1/4/2019
 */
@AppInHouse
public class VersionServiceImpl implements VersionService {

    @Inject
    VersionStore versionTable;

    @Inject
    AppService appService;

    @Inject
    public VersionServiceImpl() {

    }

    @Override
    public List<VersionResponseVo> getPlatformList(String appId, String platform) {
        return versionTable.getPlatformList(appId, platform);
    }

    @Override
    public List<VersionResponseVo> getLatestList(String appId) {
        return versionTable.getLatestList(appId);
    }

    @Override
    public void createVersion(VersionVo vo) {
        appService.getApps(vo.getAppId());
        versionTable.createVersion(vo);
    }

    @Override
    public String getPlist(String appId, String platform, String version) {
        VersionVo vo = versionTable.getVersion(appId, platform, version);
        if (vo != null) {
            return PlistUtils.genPlist(vo.getDownloadUrl(), vo.getIosBundleId(), vo.getIosTitle());
        } else {
            throw new NotFoundException(String.format("The Version not found , id : %s, platform : %s, version : %s",
                appId, platform, version));
        }
    }
}
