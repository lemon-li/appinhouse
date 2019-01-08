package com.seasungames.appinhouse.dagger;

import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.dagger.scope.AppiInHouse;
import com.seasungames.appinhouse.routes.RoutesManager;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.ext.web.Router;
import org.aeonbits.owner.ConfigFactory;

import javax.inject.Named;

/**
 * Created by lile on 1/3/2019
 */

@Module
public class AppInHouseModule {

    @Provides
    @AppiInHouse
    RoutesManager provideRoutesManager(Router router) {
        return new RoutesManager(router);
    }

    @Provides
    @Named("HTTPS")
    @AppiInHouse
    HttpServer provideHttpsServer(Vertx vertx) {
        return vertx.createHttpServer(new HttpServerOptions()
                .setSsl(true)
                .setUseAlpn(true)
                .setPemKeyCertOptions(new PemKeyCertOptions()
                        .setKeyPath("tls/private.pem")
                        .setCertPath("tls/public.pem")));
    }

    @Provides
    @Named("HTTP")
    @AppiInHouse
    HttpServer provideHttpServer(Vertx vertx) {
        return vertx.createHttpServer();
    }

    @Provides
    @AppiInHouse
    Configuration provideConfiguration() {
        return ConfigFactory.create(Configuration.class);
    }
}
