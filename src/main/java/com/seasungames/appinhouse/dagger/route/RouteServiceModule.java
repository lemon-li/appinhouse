package com.seasungames.appinhouse.dagger.route;

import com.seasungames.appinhouse.configs.impl.ServiceConfig;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.services.impl.AppServiceImpl;
import com.seasungames.appinhouse.services.impl.VersionServiceImpl;
import com.seasungames.appinhouse.stores.services.app.AppDBService;
import com.seasungames.appinhouse.stores.services.version.VersionDBService;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.ext.web.Router;
import io.vertx.serviceproxy.ServiceProxyBuilder;

/**
 * @author lile on 2019-01-06
 */
@Module
public class RouteServiceModule {

    @Provides
    @AppInHouse
    AppService provideAppService(AppServiceImpl appService) {
        return appService;
    }

    @Provides
    @AppInHouse
    VersionService provideVersionService(VersionServiceImpl versionService) {
        return versionService;
    }

    @Provides
    @AppInHouse
    Router provideRouter(Vertx vertx) {
        return Router.router(vertx);
    }

    @Provides
    @AppInHouse
    AppDBService provideAppDBService(Vertx vertx, ServiceConfig serviceConfig) {
        return new ServiceProxyBuilder(vertx)
            .setAddress(serviceConfig.serviceAppAddress())
            .setOptions(new DeliveryOptions()
                .setLocalOnly(true)
                .setSendTimeout(serviceConfig.serviceProxySendTimeout()))
            .build(AppDBService.class);
    }

    @Provides
    @AppInHouse
    VersionDBService provideVersionDBService(Vertx vertx, ServiceConfig serviceConfig) {
        return new ServiceProxyBuilder(vertx)
            .setAddress(serviceConfig.serviceVersionAddress())
            .setOptions(new DeliveryOptions()
                .setLocalOnly(true)
                .setSendTimeout(serviceConfig.serviceProxySendTimeout()))
            .build(VersionDBService.class);
    }
}
