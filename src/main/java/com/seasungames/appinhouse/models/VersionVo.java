package com.seasungames.appinhouse.models;

import com.seasungames.appinhouse.application.PlatformEnum;

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

    public VersionVo(){

    }

    public VersionVo(String appId, String platform, String version,
                     String desc, String download_url,
                     String jenkins_url, String create_time) {
        this.appId = appId;
        this.platform = platform;
        this.version = version;
        this.desc = desc;
        this.downloadUrl = download_url;
        this.jenkinsUrl = jenkins_url;
        this.createTime = create_time;
    }

    public String getAppId() {
        return appId;
    }

    public String getPlatform() {
        return platform;
    }

    public String getVersion() {
        return version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public VersionVo setDownloadUrl(String download_url) {
        this.downloadUrl = download_url;
        return this;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public String getDesc() { return desc; }

    public String getIosBundleId() { return iosBundleId; }

    public VersionVo setIosBundleId(String ios_bundle_id) {
        this.iosBundleId = ios_bundle_id;
        return this;
    }

    public String getIosTitle() { return iosTitle; }

    public VersionVo setIosTitle(String ios_title) {
        this.iosTitle = ios_title;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public boolean isIOS() {
        return this.platform.equals(PlatformEnum.IOS.getPlatform());
    }
}
