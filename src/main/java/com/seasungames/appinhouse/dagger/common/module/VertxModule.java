package com.seasungames.appinhouse.dagger.common.module;

import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import dagger.Module;
import dagger.Provides;
import io.vertx.core.Vertx;

/**
 * @author lile on 2019-01-03
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
