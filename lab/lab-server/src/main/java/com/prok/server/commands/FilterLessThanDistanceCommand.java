package com.prok.server.commands;

import com.prok.common.network.Request;
import com.prok.common.network.Response;
import com.prok.server.Collection;
import com.prok.common.entities.RouteFieldType;
import com.prok.common.util.Validator;
import com.prok.server.RequestProcess;

public class FilterLessThanDistanceCommand implements Command {
    private final Collection collection;

    public FilterLessThanDistanceCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Response execute(Request request) {
        try {
            String arg = request.args[0];
            Float distance = Float.valueOf(arg);
            Validator.validateField(distance, RouteFieldType.DISTANCE);
            return new Response(true,collection.lessThanDistanceToString(distance));
        } catch (NullPointerException | IllegalArgumentException e) {
            return new Response(true,e.getMessage());
        }
    }
}
