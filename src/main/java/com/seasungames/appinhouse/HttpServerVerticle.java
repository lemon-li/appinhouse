package com.seasungames.appinhouse;

import com.seasungames.appinhouse.dagger.common.module.VertxModule;
import com.seasungames.appinhouse.dagger.route.DaggerRouteComponent;
import com.seasungames.appinhouse.dagger.route.RouteComponent;
import com.seasungames.appinhouse.routes.RoutesManager;
import com.seasungames.appinhouse.utils.AsyncUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.inject.Inject;

/**
 * @author lile on 2019-01-14
 */
public class HttpServerVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Inject
    RoutesManager routesManager;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        injectDependencies();
        AsyncUtils.startSequentially(startFuture, routesManager);
    }

    private void injectDependencies() {
        RouteComponent component = DaggerRouteComponent.builder()
            .vertxModule(new VertxModule(vertx))
            .build();
        component.inject(this);
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        AsyncUtils.stopSequentially(stopFuture, routesManager);
    }
}
