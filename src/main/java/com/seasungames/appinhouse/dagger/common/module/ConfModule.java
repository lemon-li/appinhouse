package com.seasungames.appinhouse.dagger.common.module;

import com.seasungames.appinhouse.configs.impl.DBConfig;
import com.seasungames.appinhouse.configs.impl.RouteConfig;
import com.seasungames.appinhouse.configs.impl.ServiceConfig;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import dagger.Module;
import dagger.Provides;
import org.aeonbits.owner.ConfigFactory;

/**
 * @author lile on 2019-01-18
 */
@Module
public class ConfModule {

    @Provides
    @AppInHouse
    DBConfig provideDBConfig() {
        return ConfigFactory.create(DBConfig.class);
    }

    @Provides
    @AppInHouse
    RouteConfig provideRouteConfig() {
        return ConfigFactory.create(RouteConfig.class);
    }

    @Provides
    @AppInHouse
    ServiceConfig proviceServiceConfig() {
        return ConfigFactory.create(ServiceConfig.class);
    }
}
