package com.seasungames.appinhouse.configs.impl;

import com.seasungames.appinhouse.configs.BaseConfig;

/**
 * @author lile on 2019-01-26
 */
public interface RouteConfig extends BaseConfig {

    @Key("http.host")
    @DefaultValue("0.0.0.0")
    String httpHost();

    @Key("http.port")
    @DefaultValue("8443")
    int httpPort();

    @Key("http.secureKey")
    @DefaultValue("")
    String httpSecureKey();
}
