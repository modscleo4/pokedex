/*
 * Copyright 2019 Dhiego Cassiano Fogaça Barbosa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.modscleo4.framework.web.route;

import com.google.gson.Gson;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.web.Main;
import com.modscleo4.framework.web.acl.ACL;
import com.modscleo4.framework.web.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    collection.add(matcher.group(i));
                }
            }

            return collection;
        }

        return null;
    }

    public void call(Collection<String> parameters) throws Exception {
        Class<? extends Controller> c = (Class<? extends Controller>) Class.forName("app.web.controllers." + this.getClassName());
        Controller controller = c.newInstance();

        try {
            for (Map.Entry<String, Object> entry : controller.getACL().entrySet()) {
                String key = entry.getKey();
                List<String> methods = Arrays.asList((String[]) entry.getValue());

                Class<? extends ACL> ac = (Class<? extends ACL>) Class.forName("app.web.acl." + key);
                ACL acl = ac.newInstance();

                if (methods.contains(this.getMethod())) {
                    if (!acl.pass()) {
                        acl.fail();
                        return;
                    }
                }
            }

            Method method = c.getDeclaredMethod(this.getMethod(), HttpServletRequest.class, Collection.class);
            method.invoke(controller, Main.request, parameters);
        } catch (NoSuchMethodException e) {
            try {
                Method method = c.getDeclaredMethod(this.getMethod(), HttpServletRequest.class);
                method.invoke(controller, Main.request);
            } catch (NoSuchMethodException e2) {
                try {
                    Method method = c.getDeclaredMethod(this.getMethod(), Collection.class);
                    method.invoke(controller, parameters);
                } catch (NoSuchMethodException e3) {
                    Method method = c.getDeclaredMethod(this.getMethod());
                    method.invoke(controller);
                }
            }
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this, Route.class);
    }
}
