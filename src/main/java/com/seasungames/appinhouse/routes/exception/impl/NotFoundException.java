package com.seasungames.appinhouse.routes.exception.impl;

import com.seasungames.appinhouse.routes.exception.HttpException;

/**
 * Created by lile on 1/7/2019
 */
public class NotFoundException extends HttpException {

    public NotFoundException() {
        super(404, "Not found");
    }

    public NotFoundException(String resourceName) {
        super(404, "Not found: " + resourceName);
    }
}
