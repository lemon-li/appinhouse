package com.seasungames.appinhouse.dagger;

import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;

import javax.inject.Singleton;

/**
 * Created by lile on 1/3/2019
 */
@Module
public class VertxModule {

    private final Vertx vertx;

    public VertxModule(Vertx vertx) {
        this.vertx = vertx;
    }

    @Provides
    @Singleton
    Vertx provideVertx() {
        return this.vertx;
    }
}
