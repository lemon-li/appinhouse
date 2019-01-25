package com.seasungames.appinhouse.application;

/**
 * Created by lile on 1/25/2019
 */
public final class Errors {

    public static final String CLIENT_ERROR_TYPE = "BadRequest";

    public static final String SERVICE_ERROR_TYPE = "InternalError";

    public static final String VALIDATION_ERROR_TYPE = "ValidationError";

    public static final String DECODE_JSON_ERROR_TYPE = "Failed to decode Json";

    public static final String NOT_FOUND_ERROR_TYPE = "Not Found";

    public static final String NOT_FOUND_APP_ID_MESSAGE = "The app with id: %s can not be found";

    public static final String NOT_FOUND_VERSION_MESSAGE = "The Version not found , id: %s, platform: %s, version: %s";

    private Errors() {
    }
}
