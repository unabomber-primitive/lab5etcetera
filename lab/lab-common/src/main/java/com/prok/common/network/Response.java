package com.prok.common.network;

import java.io.Serializable;

public class Response implements Serializable {
    public boolean loggedInSuccess = true;
    public Boolean success;
    public String message;
    public Response(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public Response(boolean loggedInSuccess, Boolean success, String message) {
        this.loggedInSuccess = loggedInSuccess;
        this.success = success;
        this.message = message;
    }
}