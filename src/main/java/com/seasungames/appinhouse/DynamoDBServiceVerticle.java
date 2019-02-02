package com.seasungames.appinhouse;

import com.seasungames.appinhouse.configs.impl.ServiceConfig;
import com.seasungames.appinhouse.dagger.db.DBComponent;
import com.seasungames.appinhouse.dagger.db.DaggerDBComponent;
import com.seasungames.appinhouse.stores.services.app.AppDBService;
import com.seasungames.appinhouse.stores.services.version.VersionDBService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ServiceBinder;

import javax.inject.Inject;

/**
 * @author lile on 2019-01-14
 */
public class DynamoDBServiceVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(DynamoDBServiceVerticle.class);

    @Inject
    AppDBService appDBService;

    @Inject
    VersionDBService versionDBService;

    @Inject
    ServiceConfig serviceConfig;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        injectDependencies();
        startAppDBService();
        startVersionDBService();
        startFuture.complete();
    }

    private void injectDependencies() {
        DBComponent component = DaggerDBComponent.builder().build();
        component.inject(this);
    }

    private void startAppDBService() {
        new ServiceBinder(vertx)
            .setAddress(serviceConfig.serviceAppAddress())
            .register(AppDBService.class, appDBService);
    }

    private void startVersionDBService() {
        new ServiceBinder(vertx)
            .setAddress(serviceConfig.serviceVersionAddress())
            .register(VersionDBService.class, versionDBService);
    }

    @Override
    public void stop() throws Exception {
        LOG.info("DynamoDBService stopped");
    }
}
