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

package com.modscleo4.framework.web.controller;

import app.web.Auth;
import com.modscleo4.framework.collection.ICollection;
import com.modscleo4.framework.collection.IRow;
import com.modscleo4.framework.collection.Row;
import com.modscleo4.framework.web.Main;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public abstract class Controller {
    private final IRow acl = new Row();

    public IRow getACL() {
        return this.acl;
    }

    protected void loadTemplate(String view, IRow data) throws IOException {
        if (!view.endsWith(".vm")) {
            view += ".vm";
        }

        Properties properties = new Properties();
        properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, Main.application.getRealPath("/views"));
        Velocity.init(properties);
        VelocityContext context = new VelocityContext();

        if (data != null) {
            data.forEach(context::put);
        }

        try {
            context.put("_user", Auth.user());
            context.put("String", String.class);
        } catch (Exception ignored) {

        }

        Template template = Velocity.getTemplate(view);
        StringWriter sw = new StringWriter();

        template.merge(context, sw);

        Main.out.println(new String(sw.toString().getBytes(), StandardCharsets.UTF_8));
    }

    protected void loadTemplate(String view) throws IOException {
        this.loadTemplate(view, null);
    }

    protected void loadView(String view, IRow data) throws ServletException, IOException {
        if (!view.endsWith(".jsp")) {
            view += ".jsp";
        }

        HttpServletRequest request = Main.request;
        HttpServletResponse response = Main.response;
        HttpSession session = Main.session;

        if (data != null) {
            data.forEach(session::setAttribute);
        }

        request.getRequestDispatcher(view).forward(request, response);
    }

    protected void loadView(String view) throws ServletException, IOException {
        this.loadView(view, null);
    }

    protected void loadJson(ICollection array) throws ServletException, IOException {
        this.loadView("/framework/json_array.jsp", new Row() {{
            put("array", array);
        }});
    }

    protected void loadJson(IRow object) throws ServletException, IOException {
        this.loadView("/framework/json_object.jsp", new Row() {{
            put("object", object);
        }});
    }

    protected void abort(int code, String message) throws IOException {
        HttpServletResponse response = Main.response;

        response.sendError(code, message);
    }

    protected void abort(int code) throws IOException {
        this.abort(code, null);
    }

    protected void redirect(String url, IRow data) throws IOException {
        HttpServletResponse response = Main.response;
        HttpSession session = Main.session;

        if (data != null) {
            data.forEach(session::setAttribute);
        }

        response.sendRedirect(url);
    }

    protected void redirect(String url) throws IOException {
        this.redirect(url, null);
    }

    protected void acl(String className, String[] methods) {
        this.acl.put(className, methods);
    }
}
