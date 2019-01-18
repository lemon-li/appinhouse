package com.seasungames.appinhouse.application;

import io.vertx.core.Future;

/**
 * Created by lile on 1/18/2019
 */
public interface Async {

    void start(Future<Void> startFuture);

    void stop(Future<Void> stopFuture);
}
