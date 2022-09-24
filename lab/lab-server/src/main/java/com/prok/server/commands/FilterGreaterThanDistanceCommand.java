package com.prok.server.commands;

import com.prok.common.Command;
import com.prok.common.entities.Collection;
import com.prok.common.entities.RouteFieldType;
import com.prok.common.util.Validator;

public class FilterGreaterThanDistanceCommand implements Command {
    private final Collection collection;

    public FilterGreaterThanDistanceCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        try {
            Float distance = Float.valueOf(arg);
            Validator.validateField(distance, RouteFieldType.DISTANCE);
            System.out.println(collection.greaterThanDistanceToString(distance));
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
