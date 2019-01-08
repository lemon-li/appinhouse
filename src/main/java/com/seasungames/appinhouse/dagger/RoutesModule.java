package com.seasungames.appinhouse.dagger;

import com.seasungames.appinhouse.dagger.scope.AppiInHouse;
import com.seasungames.appinhouse.routes.handlers.RouteAppHandler;
import com.seasungames.appinhouse.routes.handlers.RouteVersionHandler;
import com.seasungames.appinhouse.services.AppService;
import com.seasungames.appinhouse.services.VersionService;
import com.seasungames.appinhouse.services.impl.AppServiceImpl;
import com.seasungames.appinhouse.services.impl.VersionServiceImpl;
import com.seasungames.appinhouse.stores.AppStore;
import com.seasungames.appinhouse.stores.VersionStore;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

@Module
public class RoutesModule {

    @Provides
    @AppiInHouse
    RouteAppHandler provideRouteAppHandler(Router router, AppService service) {
        return new RouteAppHandler(router, service);
    }

    @Provides
    @AppiInHouse
    RouteVersionHandler provideRouteVersionHandler(Router router, VersionService service) {
        return new RouteVersionHandler(router, service);
    }

    @Provides
    @AppiInHouse
    AppService provideAppService(AppStore appTable) {
        return new AppServiceImpl(appTable);
    }

    @Provides
    @AppiInHouse
    VersionService provideVersionService(VersionStore versionTable, AppService appService) {
        return new VersionServiceImpl(versionTable, appService);
    }

    @Provides
    @AppiInHouse
    Router provideRouter(Vertx vertx) {
        return Router.router(vertx);
    }
}
