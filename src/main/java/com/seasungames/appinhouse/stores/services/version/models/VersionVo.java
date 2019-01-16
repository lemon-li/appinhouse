package com.seasungames.appinhouse.stores.services.version.models;

import com.seasungames.appinhouse.application.PlatformEnum;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class VersionVo {

    private String appId;

    private String platform;

    private String version;

    private String downloadUrl;

    private String jenkinsUrl;

    private String desc;

    private String iosBundleId;

    private String iosTitle;

    private String createTime;

    public VersionVo(JsonObject jsonObject) {
        VersionVoConverter.fromJson(jsonObject, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        VersionVoConverter.toJson(this, json);
        return json;
    }

    public VersionVo() {

    }

    public VersionVo(String appId, String platform, String version,
                     String desc, String downloadUrl,
                     String jenkinsUrl, String createTime) {
        this.appId = appId;
        this.platform = platform;
        this.version = version;
        this.desc = desc;
        this.downloadUrl = downloadUrl;
        this.jenkinsUrl = jenkinsUrl;
        this.createTime = createTime;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public void setJenkinsUrl(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIOSBundleId() {
        return iosBundleId;
    }

    public void setIOSBundleId(String iosBundleId) {
        this.iosBundleId = iosBundleId;
    }

    public String getIOSTitle() {
        return iosTitle;
    }

    public void setIOSTitle(String iosTitle) {
        this.iosTitle = iosTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isIOS() {
        return this.platform.equals(PlatformEnum.IOS.getPlatform());
    }
}
