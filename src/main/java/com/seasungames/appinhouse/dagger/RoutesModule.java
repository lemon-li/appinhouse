package com.seasungames.appinhouse.dagger;

import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.services.impl.AppServiceImpl;
import com.seasungames.appinhouse.services.impl.VersionServiceImpl;
import com.seasungames.appinhouse.stores.services.app.AppDBService;
import com.seasungames.appinhouse.stores.services.version.VersionDBService;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.serviceproxy.ServiceProxyBuilder;

import javax.inject.Named;

@Module
public class RoutesModule {

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
    @Named("APP_DB_PROXY")
    @AppInHouse
    AppDBService provideAppDBService(Vertx vertx) {
        return new ServiceProxyBuilder(vertx)
            .setAddress(AppDBService.SERVICE_ADDRESS)
            .build(AppDBService.class);
    }

    @Provides
    @Named("VERSION_DB_PROXY")
    @AppInHouse
    VersionDBService provideVersionDBService(Vertx vertx) {
        return new ServiceProxyBuilder(vertx)
            .setAddress(VersionDBService.SERVICE_ADDRESS)
            .build(VersionDBService.class);
    }
}
