package com.seasungames.appinhouse.dagger.common.module;

import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;

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
    @AppInHouse
    Vertx provideVertx() {
        return this.vertx;
    }
}
