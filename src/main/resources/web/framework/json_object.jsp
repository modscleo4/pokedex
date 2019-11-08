<%@page import="com.modscleo4.framework.collection.IRow" %>

<% IRow object = (IRow) session.getAttribute("object"); %>

<%= object %>

<% session.removeAttribute("object"); %>
