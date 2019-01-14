package com.seasungames.appinhouse.stores.services;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

/**
 * Created by lile on 1/14/2019
 */
public class DynamoDBServiceImpl implements DynamoDBService {

    @Override
    public void test(Handler<AsyncResult<String>> resultHandler) {
        resultHandler.handle(Future.succeededFuture("123"));
    }

    @Override
    public void close(Handler<AsyncResult<Void>> resultHandler) {
        resultHandler.handle(Future.succeededFuture());
    }
}
