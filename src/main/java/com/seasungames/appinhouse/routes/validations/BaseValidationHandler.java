package com.seasungames.appinhouse.routes.validations;

import com.seasungames.appinhouse.application.PlatformEnum;
import io.vertx.ext.web.api.RequestParameter;
import io.vertx.ext.web.api.validation.ParameterTypeValidator;
import io.vertx.ext.web.api.validation.ValidationException;

/**
 * Created by lile on 1/7/2019
 */
public abstract class BaseValidationHandler {

    protected static final String REGEX_CHECK_EMPTY = "^(?!\\s*$).+";

    protected static ParameterTypeValidator platformFieldValidator() {
        return value -> {
            boolean isExist = false;
            for (PlatformEnum platform : PlatformEnum.values()) {
                if (platform.getPlatform().equals(value)) {
                    isExist = true;
                }
            }

            if (!isExist) {
                throw new ValidationException("platform field is invalid");
            }
            return RequestParameter.create(value);
        };
    }
}
