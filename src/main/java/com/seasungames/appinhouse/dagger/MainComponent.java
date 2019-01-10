package com.seasungames.appinhouse.dagger;

import com.seasungames.appinhouse.AppInHouseVerticle;
import com.seasungames.appinhouse.dagger.scope.AppiInHouse;
import com.seasungames.appinhouse.routes.RoutesManager;
import dagger.Component;

/**
 * Created by lile on 1/3/2019
 */
@AppiInHouse
@Component(modules = {
        VertxModule.class,
        AppInHouseModule.class,
        DBModule.class,
        RoutesModule.class
})
public interface MainComponent {

    void inject(AppInHouseVerticle verticle);

}