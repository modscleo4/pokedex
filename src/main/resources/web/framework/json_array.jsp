<%@page import="com.modscleo4.framework.collection.ICollection" %>

<% ICollection array = (ICollection) session.getAttribute("array"); %>

<%= array %>

<% session.removeAttribute("array"); %>
