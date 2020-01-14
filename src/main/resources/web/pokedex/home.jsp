<%@page pageEncoding="utf-8" %>
<%@page import="app.dao.AppDAO" %>
<%@page import="app.entity.User" %>

<% User user = AppDAO.userDAO.find(session.getAttribute("__user")); %>

<form action="/logout" method="post">
    <button type="submit">Logout</button>
</form>

Hello <%= user.getName() %>!

<% if (user.isAdmin()) { %>
    <jsp:include page="/admin/index.jsp" flush="true"/>
<% } %>
