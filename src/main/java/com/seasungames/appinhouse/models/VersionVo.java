package com.seasungames.appinhouse.models;

import com.seasungames.appinhouse.application.PlatformConstant;

public class VersionVo {

    private String appId;

    private String platform;

    private String version;

    private String download_url;

    private String jenkins_url;

    private String desc;

    private String ios_bundle_id;

    private String ios_title;

    private String create_time;

    public VersionVo(){

    }

    public VersionVo(String appId, String platform, String version,
                     String desc, String download_url,
                     String jenkins_url, String create_time) {

        this.appId = appId;
        this.platform = platform;
        this.version = version;
        this.desc = desc;
        this.download_url = download_url;
        this.jenkins_url = jenkins_url;
        this.create_time = create_time;
    }

    public VersionVo setDownload_url(String download_url) {
        this.download_url = download_url;
        return this;
    }

    public VersionVo setIos_bundle_id(String ios_bundle_id) {
        this.ios_bundle_id = ios_bundle_id;
        return this;
    }

    public VersionVo setIos_title(String ios_title) {
        this.ios_title = ios_title;
        return this;
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

    public String getDownload_url() {
        return download_url;
    }

    public String getJenkins_url() {
        return jenkins_url;
    }

    public String getDesc() { return desc; }

    public String getIos_bundle_id() { return ios_bundle_id; }

    public String getIos_title() { return ios_title; }

    public String getCreate_time() {
        return create_time;
    }

    public boolean isIOS() {
        return this.platform.equals(PlatformConstant.IOS.getPlatform());
    }
}
