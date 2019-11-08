package com.modscleo4.framework.web;

import app.web.Main;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.web.controllers.Controller;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
    private String httpMethod;
    private String pattern;
    private String className;
    private String method;
    private String name;

    public Route(String httpMethod, String pattern, String className, String method, String name) {
        this.httpMethod = httpMethod;
        this.pattern = pattern;
        this.className = className;
        this.method = method;
        this.name = name;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean matches(String val) {
        return Pattern.matches(this.getPattern(), val);
    }

    public Collection<String> getParameters(String val) {
        if (this.matches(val)) {
            Collection<String> collection = new Collection<>();

            Matcher matcher = Pattern.compile(this.getPattern()).matcher(val);
            while (matcher.find()) {
                for (int j = 1; j <= matcher.groupCount(); j++) {
                    collection.add(matcher.group(j));
                }
            }

            return collection;
        }

        return null;
    }

    public void call(Collection<String> parameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class c = Class.forName("app.web.controllers." + this.getClassName());
        try {
            Method method = c.getDeclaredMethod(this.getMethod(), HttpServletRequest.class, Collection.class);
            Controller controller = (Controller) c.newInstance();
            method.invoke(controller, Main.request, parameters);
        } catch (NoSuchMethodException e) {
            try {
                Method method = c.getDeclaredMethod(this.getMethod(), HttpServletRequest.class);
                Controller controller = (Controller) c.newInstance();
                method.invoke(controller, Main.request);
            } catch (NoSuchMethodException e2) {
                Method method = c.getDeclaredMethod(this.getMethod());
                Controller controller = (Controller) c.newInstance();
                method.invoke(controller);
            }
        }
    }
}
