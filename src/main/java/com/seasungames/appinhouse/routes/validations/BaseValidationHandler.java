package com.seasungames.appinhouse.routes.validations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import com.seasungames.appinhouse.application.PlatformEnum;
import io.vertx.ext.web.api.RequestParameter;
import io.vertx.ext.web.api.validation.ParameterTypeValidator;
import io.vertx.ext.web.api.validation.ValidationException;

/**
 * @author lile on 2019-01-07
 */
public abstract class BaseValidationHandler {

    protected static final String REGEX_CHECK_EMPTY = "^(?!\\s*$).+";

    protected static <T> String getJsonSchema(Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonSchemaGenerator generator = new JsonSchemaGenerator(objectMapper);
            JsonNode jsonSchema = generator.generateJsonSchema(clazz);
            return objectMapper.writeValueAsString(jsonSchema);
        } catch (JsonProcessingException e) {
            throw new ValidationException(ValidationException.ErrorType.JSON_INVALID);
        }
    }

    protected static ParameterTypeValidator platformFieldValidator() {
        return value -> {
            boolean isExist = false;
            for (PlatformEnum platform : PlatformEnum.values()) {
                if (platform.getPlatform().equals(value)) {
                    isExist = true;
                }
            }
            if (!isExist) {
                throw new ValidationException(ValidationException.ErrorType.NO_MATCH);
            }
            return RequestParameter.create(value);
        };
    }
}
