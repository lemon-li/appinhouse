package com.seasungames.appinhouse.configs;

import org.aeonbits.owner.Config;

/**
 * Created by lile on 2019-01-26
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"file:~/.appinhouse.properties",
    "file:appinhouse.properties",
    "classpath:appinhouse.properties"})
public interface BaseConfig extends Config {

}
