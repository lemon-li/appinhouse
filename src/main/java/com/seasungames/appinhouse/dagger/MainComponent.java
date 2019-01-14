package com.seasungames.appinhouse.dagger;

import com.seasungames.appinhouse.AppInHouseVerticle;
import com.seasungames.appinhouse.DynamoDBServiceVerticle;
import com.seasungames.appinhouse.HttpServerVerticle;
import com.seasungames.appinhouse.dagger.scope.AppInHouse;
import dagger.Component;

/**
 * Created by lile on 1/3/2019
 */
@AppInHouse
@Component(modules = {
    VertxModule.class,
    AppInHouseModule.class,
    DBModule.class,
    RoutesModule.class
})
public interface MainComponent {

    void inject(AppInHouseVerticle verticle);

    void inject(HttpServerVerticle verticle);

    void inject(DynamoDBServiceVerticle verticle);

}
