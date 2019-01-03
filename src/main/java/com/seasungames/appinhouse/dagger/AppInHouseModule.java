package com.seasungames.appinhouse.dagger;

import com.seasungames.appinhouse.application.ConfigManager;
import com.seasungames.appinhouse.routes.RoutesManager;
import com.seasungames.appinhouse.stores.dynamodb.DynamoDBManager;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.PemKeyCertOptions;

import javax.inject.Singleton;

/**
 * Created by lile on 1/3/2019
 */

@Module
public class AppInHouseModule {

    @Provides
    @Singleton
    DynamoDBManager provideDynamoDBManager(ConfigManager conf) {
        return new DynamoDBManager(conf);
    }

    @Provides
    @Singleton
    RoutesManager provideRoutesManager(Vertx vertx) {
        return new RoutesManager(vertx).SetRoutes();
    }

    @Provides
    @Singleton
    HttpServer provideHttpServer(Vertx vertx) {
        return vertx.createHttpServer(new HttpServerOptions()
                .setSsl(true)
                .setUseAlpn(true)
                .setPemKeyCertOptions(new PemKeyCertOptions()
                        .setKeyPath("tls/private.pem")
                        .setCertPath("tls/public.pem")));
    }

    @Provides
    @Singleton
    ConfigManager provideConfigManager() {
        return new ConfigManager();
    }
}
