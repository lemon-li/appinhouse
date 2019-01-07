package com.seasungames.appinhouse.routes.validations.impl;


import com.seasungames.appinhouse.routes.validations.BaseValidationHandler;
import io.vertx.ext.web.api.validation.HTTPRequestValidationHandler;
import io.vertx.ext.web.api.validation.ParameterType;

/**
 * Created by lile on 1/7/2019
 */
public class VersionValidationHandler extends BaseValidationHandler {

    public static HTTPRequestValidationHandler validatePlatform() {
        return HTTPRequestValidationHandler.create()
                .addPathParamWithPattern("id", REGEX_CHECK_EMPTY)
                .addPathParamWithPattern("platform", REGEX_CHECK_EMPTY);
    }

    public static HTTPRequestValidationHandler validateForm() {
        return HTTPRequestValidationHandler.create()
                .addPathParamWithPattern("id", REGEX_CHECK_EMPTY)
                .addPathParamWithPattern("platform", REGEX_CHECK_EMPTY)
                .addFormParamWithPattern("version", REGEX_CHECK_EMPTY, true)
                .addFormParamWithPattern("desc", REGEX_CHECK_EMPTY, true)
                .addFormParamWithPattern("download_url", REGEX_CHECK_EMPTY, true)
                .addFormParamWithPattern("jenkins_url", REGEX_CHECK_EMPTY, true)
                .addFormParamWithPattern("create_time", REGEX_CHECK_EMPTY, true)
                .addFormParamWithPattern("ios_bundle_id", REGEX_CHECK_EMPTY, false)
                .addFormParamWithPattern("ios_title", REGEX_CHECK_EMPTY, false);
    }

    public static HTTPRequestValidationHandler validateVersion() {
        return HTTPRequestValidationHandler.create()
                .addPathParamWithPattern("id", REGEX_CHECK_EMPTY)
                .addPathParamWithPattern("platform", REGEX_CHECK_EMPTY)
                .addPathParamWithPattern("version", REGEX_CHECK_EMPTY);
    }

}
