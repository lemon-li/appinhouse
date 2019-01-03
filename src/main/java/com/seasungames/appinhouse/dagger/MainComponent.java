package com.seasungames.appinhouse.dagger;

import com.seasungames.appinhouse.AppInHouseVerticle;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Created by lile on 1/3/2019
 */
@Singleton
@Component(modules = {
        VertxModule.class,
        AppInHouseModule.class
})
public interface MainComponent {

    void inject(AppInHouseVerticle verticle);
}