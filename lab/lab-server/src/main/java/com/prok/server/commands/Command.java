package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;

public interface Command {
    Response execute(Request request);
}
