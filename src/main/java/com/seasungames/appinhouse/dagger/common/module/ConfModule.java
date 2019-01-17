package com.seasungames.appinhouse.dagger.common.module;

import com.seasungames.appinhouse.application.Configuration;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import dagger.Module;
import dagger.Provides;
import org.aeonbits.owner.ConfigFactory;

/**
 * Created by lile on 1/18/2019
 */
@Module
public class ConfModule {

    @Provides
    @AppInHouse
    Configuration provideConfiguration() {
        return ConfigFactory.create(Configuration.class);
    }
}
