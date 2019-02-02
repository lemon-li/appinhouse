package com.seasungames.appinhouse.dagger.route;

import com.seasungames.appinhouse.HttpServerVerticle;
import com.seasungames.appinhouse.dagger.common.module.ConfModule;
import com.seasungames.appinhouse.dagger.common.module.VertxModule;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import dagger.Component;

/**
 * @author lile on 2019-01-03
 */
@AppInHouse
@Component(modules = {
    VertxModule.class,
    RouteModule.class,
    ConfModule.class,
    RouteServiceModule.class
})
public interface RouteComponent {

    void inject(HttpServerVerticle verticle);
}
