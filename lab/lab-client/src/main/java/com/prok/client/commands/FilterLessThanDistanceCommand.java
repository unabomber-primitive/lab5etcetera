package com.prok.client.commands;

import com.prok.common.entities.Collection;
import com.prok.common.entities.RouteFieldType;
import com.prok.common.util.Validator;

public class FilterLessThanDistanceCommand implements Command {
    private final Collection collection;

    public FilterLessThanDistanceCommand(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String arg) {
        try {
            Float distance = Float.valueOf(arg);
            Validator.validateField(distance, RouteFieldType.DISTANCE);
            System.out.println(collection.lessThanDistanceToString(distance));
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
