package com.seasungames.appinhouse.application;

import io.vertx.core.Future;

/**
 * @author lile on 2019-01-18
 */
public interface Async {

    void start(Future<Void> startFuture);

    void stop(Future<Void> stopFuture);
}
