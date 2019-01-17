package com.seasungames.appinhouse.dagger.db;

import com.seasungames.appinhouse.DynamoDBServiceVerticle;
import com.seasungames.appinhouse.dagger.common.module.ConfModule;
import com.seasungames.appinhouse.dagger.common.module.VertxModule;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import dagger.Component;

/**
 * Created by lile on 1/18/2019
 */
@AppInHouse
@Component(modules = {
    DBModule.class,
    ConfModule.class
})
public interface DBComponent {

    void inject(DynamoDBServiceVerticle verticle);
}
