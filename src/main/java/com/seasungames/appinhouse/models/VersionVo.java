package com.seasungames.appinhouse.models;

public class VersionVo {

    private String appId;

    private String platform;

    private String version;

    private String download_url;

    private String jenkins_url;

    private String plist;

    private int create_time;

    public VersionVo(String appId, String platform, String version, String download_url,
                     String jenkins_url, String plist, int create_time) {

        this.appId = appId;
        this.platform = platform;
        this.version = version;
        this.download_url = download_url;
        this.jenkins_url = jenkins_url;
        this.plist = plist;
        this.create_time = create_time;
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

    public String getPlist() {
        return plist;
    }

    public int getCreate_time() {
        return create_time;
    }
}
