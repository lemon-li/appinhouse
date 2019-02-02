package com.seasungames.appinhouse.dagger.route;

import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

/**
 * @author lile on 2019-01-03
 */
@Module
public class RouteModule {

    @Provides
    @AppInHouse
    HttpServer provideHttpServer(Vertx vertx) {
        return vertx.createHttpServer();
    }
}
