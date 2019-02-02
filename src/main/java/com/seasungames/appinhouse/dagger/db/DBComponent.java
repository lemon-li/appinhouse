package com.seasungames.appinhouse.dagger.db;

import com.seasungames.appinhouse.DynamoDBServiceVerticle;
import com.seasungames.appinhouse.dagger.common.module.ConfModule;
import com.seasungames.appinhouse.dagger.common.module.VertxModule;
import com.seasungames.appinhouse.dagger.common.scope.AppInHouse;
import dagger.Component;

/**
 * @author lile on 2019-01-18
 */
@AppInHouse
@Component(modules = {
    DBModule.class,
    ConfModule.class
})
public interface DBComponent {

    void inject(DynamoDBServiceVerticle verticle);
}
