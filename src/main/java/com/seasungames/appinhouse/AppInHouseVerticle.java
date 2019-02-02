package com.seasungames.appinhouse;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author lile on 2018-12-26
 */
public class AppInHouseVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(AppInHouseVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        this.deployDynamoDBVerticle()
            .compose(i -> this.deployHttpVerticle())
            .setHandler(ar -> {
                if (ar.succeeded()) {
                    LOG.info("All verticles deployed");
                    startFuture.complete();
                } else {
                    LOG.error("Fail to deploy some verticle");
                    startFuture.fail(ar.cause());
                }
            });
    }

    private Future<Void> deployDynamoDBVerticle() {
        return this.deployVerticle(DynamoDBServiceVerticle.class, new DeploymentOptions().setWorker(true));
    }

    private Future<Void> deployHttpVerticle() {
        return this.deployVerticle(HttpServerVerticle.class, new DeploymentOptions());
    }

    private Future<Void> deployVerticle(Class clazz, DeploymentOptions options) {
        Future<Void> verticleFuture = Future.future();
        vertx.deployVerticle(clazz.getName(), options, ar -> {
            if (ar.succeeded()) {
                LOG.info("Deployed {} with ID {}", clazz.getName(), ar.result());
                verticleFuture.complete();
            } else {
                verticleFuture.fail(ar.cause());
            }
        });
        return verticleFuture;
    }

    @Override
    public void stop() throws Exception {
        LOG.info("Stopping main verticle");
        super.stop();
    }
}
