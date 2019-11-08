package com.modscleo4.framework.web;

import com.modscleo4.framework.collection.Collection;

public class RouteCollection extends Collection<Route> {
    public Route getMatch(String find) {
        for (Route r : this) {
            if (r.matches(find)) {
                return r;
            }
        }

        return null;
    }

    public Route getMatch(String find, String method) {
        for (Route r : this) {
            if (r.getHttpMethod().equalsIgnoreCase(method) && r.matches(find)) {
                return r;
            }
        }

        return null;
    }

    public Route getByName(String name) {
        for (Route r : this) {
            if (r.getName().equals(name)) {
                return r;
            }
        }

        return null;
    }
}
