package com.seasungames.appinhouse.routes.validations;

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
                .addFormParamWithPattern("id", REGEX_CHECK_EMPTY, true)
                .addFormParamWithPattern("desc", REGEX_CHECK_EMPTY, true)
                .addFormParamWithPattern("alias", REGEX_CHECK_EMPTY, true);
    }
}
