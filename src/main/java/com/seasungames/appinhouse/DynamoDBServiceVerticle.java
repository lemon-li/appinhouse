package com.seasungames.appinhouse;

import com.seasungames.appinhouse.stores.services.DynamoDBService;
import com.seasungames.appinhouse.stores.services.DynamoDBServiceImpl;
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
        DynamoDBServiceImpl service = new DynamoDBServiceImpl();

        new ServiceBinder(vertx)
            .setAddress(DynamoDBService.SERVICE_ADDRESS)
            .register(DynamoDBService.class, service);

        startFuture.complete();
    }

    @Override
    public void stop() throws Exception {
        LOG.info("DynamoDBService stopped");
    }
}
