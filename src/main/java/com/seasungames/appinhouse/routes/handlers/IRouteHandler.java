package com.seasungames.appinhouse.routes.handlers;

public class IRouteHandler {

    public boolean IsSuccess(int result) {
        return result == 0 ? true : false;
    }

    public String GetErrorMsg() {
        return "";
    }
}
