package com.seasungames.appinhouse.routes.validations.impl;

import com.seasungames.appinhouse.routes.validations.BaseValidationHandler;
import com.seasungames.appinhouse.stores.services.app.models.AppVo;
import io.vertx.ext.web.api.validation.HTTPRequestValidationHandler;

/**
 * Created by lile on 1/7/2019
 */
public class AppValidationHandler extends BaseValidationHandler {

    public static HTTPRequestValidationHandler validateId() {
        return HTTPRequestValidationHandler.create()
            .addPathParamWithPattern("id", REGEX_CHECK_EMPTY);
    }

    public static HTTPRequestValidationHandler validateAppForm() {
        return HTTPRequestValidationHandler.create()
            .addJsonBodySchema(getJsonSchema(AppVo.class));
    }
}
