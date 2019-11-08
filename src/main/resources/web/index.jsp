<%@page import="app.web.Main" %>
<%@page import="com.modscleo4.framework.collection.Collection" %>
<%@page import="com.modscleo4.framework.web.Route" %>
<%@page import="com.modscleo4.framework.web.RouteCollection" %>
<%@page import="com.modscleo4.framework.web.Routes" %>

<%
    Main.start(request, response);

    String URI = request.getParameter("__uri");

    RouteCollection routes = Routes.getRoutes(application);
    Route route = routes.getMatch(URI, request.getMethod());

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
%>
