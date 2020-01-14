<%@page pageEncoding="utf-8" %>
<%@page import="com.modscleo4.framework.collection.ICollection" %>
<%@page import="com.modscleo4.framework.collection.IRow" %>
<%@page import="com.modscleo4.framework.collection.Row" %>

<%
    IRow errors = (IRow) session.getAttribute("errors");
    if (errors == null) {
        errors = new Row();
    }
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Register</title>
</head>
<body>
<div id="main">
    <form action="/register" method="post">
        <label>
            Name: <input type="text" name="name"/>
            <% if (errors.containsKey("name")) {
                for (String error : (ICollection<String>) errors.get("name")) { %>
                    <p><%= error %></p>
                <% }
                }
            %>
        </label>

        <label>
            Email: <input type="email" name="username"/>
            <% if (errors.containsKey("username")) {
                for (String error : (ICollection<String>) errors.get("username")) { %>
                    <p><%= error %></p>
                <% }
            }
            %>
        </label>

        <label>
            Password: <input type="password" name="password"/>
            <% if (errors.containsKey("password")) {
                for (String error : (ICollection<String>) errors.get("password")) { %>
                    <p><%= error %></p>
                <% }
            }
            %>
        </label>
        <button type="submit">Register</button>
    </form>
</div>
</body>
</html>

<% session.removeAttribute("errors"); %>
