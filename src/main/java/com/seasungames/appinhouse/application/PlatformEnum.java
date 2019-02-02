package com.seasungames.appinhouse.application;

/**
 * @author lile on 2019-01-01
 */
public enum PlatformEnum {

    IOS("ios"), ANDROID("android"), WINDOW("window");

    private String platform;

    PlatformEnum(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return this.platform;
    }
}
