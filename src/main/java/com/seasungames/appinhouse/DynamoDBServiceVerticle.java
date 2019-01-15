package com.seasungames.appinhouse;

import com.seasungames.appinhouse.dagger.DaggerMainComponent;
import com.seasungames.appinhouse.dagger.MainComponent;
import com.seasungames.appinhouse.dagger.VertxModule;
import com.seasungames.appinhouse.stores.services.app.AppDBService;
import com.seasungames.appinhouse.stores.services.app.AppDBServiceImpl;
import com.seasungames.appinhouse.stores.services.version.VersionDBService;
import com.seasungames.appinhouse.stores.services.version.VersionDBServiceImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ServiceBinder;
/**
 * Created by lile on 1/14/2019
 */
public class DynamoDBServiceVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDBServiceVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        injectDependencies();

        new ServiceBinder(vertx)
            .setAddress(AppDBService.SERVICE_ADDRESS)
            .register(AppDBService.class, new AppDBServiceImpl());

        new ServiceBinder(vertx)
            .setAddress(VersionDBService.SERVICE_ADDRESS)
            .register(VersionDBService.class, new VersionDBServiceImpl());

        startFuture.complete();
    }

    private void injectDependencies() {
        MainComponent component = DaggerMainComponent.builder()
            .vertxModule(new VertxModule(vertx))
            .build();
        component.inject(this);
    }

    @Override
    public void stop() throws Exception {
        LOG.info("DynamoDBService stopped");
    }
}
