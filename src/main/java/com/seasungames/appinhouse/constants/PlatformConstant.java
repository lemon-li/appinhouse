package com.seasungames.appinhouse.constants;

public enum PlatformConstant {

    IOS("ios"), ANDROID("android"), WINDOW("window");

    private String platform;

    PlatformConstant(String platform) {
        this.platform = platform;
    }

    public String getPlatform() {
        return this.platform;
    }

}
