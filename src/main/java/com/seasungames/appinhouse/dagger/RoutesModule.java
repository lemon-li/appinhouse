package com.seasungames.appinhouse.dagger;

import com.seasungames.appinhouse.dagger.scope.AppiInHouse;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.services.impl.AppServiceImpl;
import com.seasungames.appinhouse.services.impl.VersionServiceImpl;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

@Module
public class RoutesModule {

    @Provides
    @AppiInHouse
    AppService provideAppService(AppServiceImpl appService) {
        return appService;
    }

    @Provides
    @AppiInHouse
    VersionService provideVersionService(VersionServiceImpl versionService) {
        return versionService;
    }

    @Provides
    @AppiInHouse
    Router provideRouter(Vertx vertx) {
        return Router.router(vertx);
    }
}
