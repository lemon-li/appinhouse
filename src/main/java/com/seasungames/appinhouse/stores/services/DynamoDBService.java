package com.seasungames.appinhouse.stores.services;

import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Created by lile on 1/14/2019
 */

@ProxyGen
public interface DynamoDBService {

    String SERVICE_ADDRESS = "com.seasungames.appinhouse.dynamodb-service";

    void test(Handler<AsyncResult<String>> resultHandler);

    @ProxyClose
    void close(Handler<AsyncResult<Void>> resultHandler);
}
