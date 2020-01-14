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

package com.modscleo4.framework.web;

import app.database.Connections;
import com.modscleo4.framework.collection.Collection;
import com.modscleo4.framework.web.route.Route;
import com.modscleo4.framework.web.route.RouteCollection;
import com.modscleo4.framework.web.route.Routes;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

public class Main {
    public static ServletContext application;
    public static HttpServletRequest request;
    public static HttpServletResponse response;
    public static HttpSession session;
    public static JspWriter out;
    public static ServletConfig config;

    public static void start(PageContext pageContext) throws IOException {
        application = pageContext.getServletContext();
        request = (HttpServletRequest) pageContext.getRequest();
        response = (HttpServletResponse) pageContext.getResponse();
        session = pageContext.getSession();
        out = pageContext.getOut();
        config = pageContext.getServletConfig();

        Connections.envdir = application.getRealPath("/WEB-INF/.env");

        String URI = request.getParameter("__uri");

        try {
            String method = request.getMethod();
            if (request.getParameterMap().containsKey("__method")) {
                // Extend method support for PUT, PATCH, DELETE, etc.
                method = request.getParameter("__method");
            }

            RouteCollection routes = Routes.getRoutes();
            Route route = routes.getMatch(URI, method);

            if (route == null) {
                if (URI.endsWith("/")) {
                    response.sendRedirect(URI.substring(0, URI.length() - 1));
                } else if (routes.getMatch(URI) != null) {
                    response.sendError(response.SC_METHOD_NOT_ALLOWED, URI);
                } else {
                    response.sendError(response.SC_NOT_FOUND, URI);
                }
            } else {
                Collection<String> parameters = route.getParameters(URI);
                route.call(parameters);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            response.sendError(response.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
