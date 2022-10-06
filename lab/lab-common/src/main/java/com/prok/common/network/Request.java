package com.prok.common.network;

import com.prok.common.entities.Route;

import java.io.Serializable;

public class Request implements Serializable {
    public String username;
    public String password;
    public boolean isObjectRequired = false;
    public Route route = null;
    public String commandName;
    public String[] args;
    public Request(String username, String password, String commandName, String[] args) {
        this.username = username;
        this.password = password;
        this.commandName = commandName;
        this.args = args;
    }

    public Request(String username, String password, String commandName, Boolean isObjectRequired,Route route) {
        this.username = username;
        this.password = password;
        this.commandName = commandName;
        this.route = route;
        this.isObjectRequired = isObjectRequired;
    }
}
