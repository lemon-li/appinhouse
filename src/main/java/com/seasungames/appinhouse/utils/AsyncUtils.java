package com.seasungames.appinhouse.utils;

import com.seasungames.appinhouse.application.Async;
import io.vertx.core.Future;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Created by lile on 1/18/2019
 */
public class AsyncUtils {

    public static void startSequentially(Future<Void> resultFuture, Async... inputs) {
        startSequentially(resultFuture, Arrays.asList(inputs));
    }

    public static void startSequentially(Future<Void> resultFuture, List<Async> inputs) {
        compose(resultFuture, inputs, (async, future) -> async.start(future));
    }

    public static void stopSequentially(Future<Void> resultFuture, Async... inputs) {
        stopSequentially(resultFuture, Arrays.asList(inputs));
    }

    public static void stopSequentially(Future<Void> resultFuture, List<Async> inputs) {
        compose(resultFuture, inputs, (async, future) -> async.stop(future));
    }

    private static void compose(Future<Void> resultFuture,
                                List<Async> inputs,
                                BiConsumer<Async, Future<Void>> consumer) {
        final int len = inputs.size();
        if (len > 0) {
            final Future<Void> seed = Future.future();
            Future<Void> last = seed;
            for (int i = 0; i < len; i++) {
                Async async = inputs.get(i);
                last = last.compose(v -> Future.future(f -> consumer.accept(async, f)));
            }
            last.setHandler(resultFuture);
            seed.complete();
        } else {
            resultFuture.complete();
        }
    }
}
