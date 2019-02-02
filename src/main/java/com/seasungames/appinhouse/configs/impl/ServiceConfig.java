package com.seasungames.appinhouse.configs.impl;

import com.seasungames.appinhouse.configs.BaseConfig;

/**
 * @author lile on 2019-01-28
 */
public interface ServiceConfig extends BaseConfig {

    @Key("service.app.address")
    @DefaultValue("com.seasungames.appinhouse.app-dynamodb-service")
    String serviceAppAddress();

    @Key("service.version.address")
    @DefaultValue("com.seasungames.appinhouse.version-dynamodb-service")
    String serviceVersionAddress();

    @Key("service.proxy.send.timeout")
    @DefaultValue("5000")
    long serviceProxySendTimeout();
}
