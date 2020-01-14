<%@page pageEncoding="utf-8" %>
<%@page import="app.entity.Pokemon" %>
<%@page import="com.modscleo4.framework.entity.IModelCollection" %>

<% IModelCollection<Pokemon> pokemon = (IModelCollection<Pokemon>) session.getAttribute("pokemon"); %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Pokemon</title>
</head>
<body>
<% if (session.getAttribute("saved") != null) {
    boolean saved = (boolean) session.getAttribute("saved");
    String action = (String) session.getAttribute("action"); %>
<p>
    <% if (saved) { %>
    <%= action.equals("store") ? "Stored" : action.equals("update") ? "Updated" : "Destroyed" %>
    <% } else { %>
    <%= action.equals("store") ? "Error when storing" : action.equals("update") ? "Error when updating" : "Error when destroying" %>
    <% } %>
</p>
<% } %>

<a href="/admin/pokemon/new">New Pokemon</a>

<table>
    <thead>
    <tr>
        <th scope="col">ID</th>
        <th>Name</th>

        <th>Actions</th>
    </tr>
    </thead>

    <tbody>
    <% for (Pokemon p : pokemon) { %>
    <tr>
        <th scope="row"><%= p.getId() %>
        </th>
        <td><%= p.getName() %>
        </td>

        <td>
            <a href="/admin/pokemon/<%= p.getId() %>">Details</a>
            <a href="/admin/pokemon/<%= p.getId() %>/edit">Edit</a>
            <a href="#" onclick="del(<%= p.getId() %>)">Delete</a>

            <form id="delete_<%= p.getId() %>" action="/admin/pokemon/<%= p.getId() %>" method="post">
                <input type="hidden" name="__method" value="DELETE">
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>

<script type="text/javascript">
    function del(id) {
        if (confirm(`Delete \${id}?`)) {
            const form = document.querySelector(`#delete_\${id}`);
            form.submit();
        }
    }
</script>
</html>

<% if (session.getAttribute("saved") != null) {
    session.removeAttribute("saved");
    session.removeAttribute("action");
}
%>
