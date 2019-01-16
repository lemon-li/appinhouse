package com.seasungames.appinhouse.routes.exception;

import com.amazonaws.AmazonServiceException;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ServiceException;

/**
 * Created by lile on 1/16/2019
 */
public class DBException {

    private int code;
    private String message;

    public DBException(Throwable throwable) {
        setDBErrorCode(throwable);
        setDBErrorMessage(throwable);
    }

    private void setDBErrorCode(Throwable throwable) {
        this.code = -1;
    }

    private void setDBErrorMessage(Throwable throwable) {
        if (throwable instanceof AmazonServiceException) {
            this.message = "DBException: " + ((AmazonServiceException) throwable).getErrorMessage();
        } else {
            this.message = throwable.getMessage();
        }
    }

    public int getDBErrorCode() {
        return this.code;
    }

    public String getDBErrorMessage() {
        return this.message;
    }

    public static <T> AsyncResult<T> fail(Throwable throwable) {
        DBException exception = new DBException(throwable);
        return Future.failedFuture(new ServiceException(exception.getDBErrorCode(),
            exception.getDBErrorMessage()));
    }
}
