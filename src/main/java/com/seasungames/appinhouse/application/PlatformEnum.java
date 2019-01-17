package com.seasungames.appinhouse.application;

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
