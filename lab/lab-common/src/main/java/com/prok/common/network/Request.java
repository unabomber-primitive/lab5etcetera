package com.prok.common.network;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String[] args;
    public Request(String commandName, String[] args) {
        this.commandName = commandName;
        this.args = args;
    }
}
