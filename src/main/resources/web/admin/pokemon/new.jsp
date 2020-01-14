<%@page import="app.entity.Category" %>
<%@page import="app.entity.Gender" %>
<%@page import="app.entity.Pokemon" %>
<%@page import="app.entity.Type" %>
<%@page import="com.modscleo4.framework.collection.IRow" %>
<%@page import="com.modscleo4.framework.collection.Row" %>
<%@page import="com.modscleo4.framework.entity.IModelCollection" %>
<%@page pageEncoding="utf-8" %>

<%
    long id = (long) session.getAttribute("id");
    IModelCollection<Pokemon> pokemonL = (IModelCollection<Pokemon>) session.getAttribute("pokemonL");
    IModelCollection<Category> categories = (IModelCollection<Category>) session.getAttribute("categories");
    IModelCollection<Gender> genders = (IModelCollection<Gender>) session.getAttribute("genders");
    IModelCollection<Type> types = (IModelCollection<Type>) session.getAttribute("types");

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
    <title>New Pokemon</title>
</head>
<body>
ID: <%= id %><br/>

<form action="/admin/pokemon" method="post">
    <label>
        Name: <input type="text" name="name" value="">
    </label><br/>

    <label for="inputDescription">Description: </label>
    <textarea name="description" id="inputDescription" cols="30"
              rows="10"></textarea><br/>

    <label>
        Height: <input type="number" name="height" value="" step="0.1">
    </label><br/>

    <label for="inputCategory">Category: </label>
    <select name="category" id="inputCategory">
        <% for (Category c : categories) { %>
        <option value="<%= c.getId() %>">
            <%= c.getName() %>
        </option>
        <% } %>
    </select><br/>

    <label for="inputGenders">Genders: </label>
    <select name="genders" id="inputGenders" multiple>
        <% for (Gender g : genders) { %>
        <option value="<%= g.getId() %>">
            <%= g.getName() %>
        </option>
        <% } %>
    </select><br/>

    <label>
        Weight: <input type="number" name="weight" value="" step="0.1">
    </label><br/>

    <label for="inputTypes">Types: </label>
    <select name="types" id="inputTypes" multiple>
        <% for (Type t : types) { %>
        <option value="<%= t.getId() %>">
            <%= t.getName() %>
        </option>
        <% } %>
    </select><br/>

    <label for="weaknesses">Weaknesses: </label>
    <table id="weaknesses">
        <thead>
        <tr>
            <th>Has effect?</th>
            <th>Type</th>
            <th>Effectiveness</th>
        </tr>
        </thead>

        <tbody>
        <% for (Type t : types) { %>
        <tr>
            <td><input name="hasWeakness_<%= t.getId() %>" type="checkbox"></td>

            <td style="color: <%= t.getHTMLColor() %>">
                <%= t.getName() %>
            </td>

            <td>
                <input name="effectiveness_<%= t.getId() %>" type="number" step="1" min="0" max="5">
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <br/>

    <label for="inputPredecessor">Predecessor: </label>
    <select name="predecessor" id="inputPredecessor">
        <option value="0">None</option>
        <% for (Pokemon p : pokemonL) { %>
        <option value="<%= p.getId() %>">
            <%= String.format("%03d", p.getId()) %> - <%= p.getName() %>
        </option>
        <% } %>
    </select><br/>

    <label for="inputSuccessor">Successor: </label>
    <select name="successor" id="inputSuccessor">
        <option value="0">None</option>
        <% for (Pokemon p : pokemonL) { %>
        <option value="<%= p.getId() %>">
            <%= String.format("%03d", p.getId()) %> - <%= p.getName() %>
        </option>
        <% } %>
    </select><br/>

    <table id="stats">
        <thead>
        <tr>
            <th><label for="inputHP">HP</label></th>
            <th><label for="inputAttack">Attack</label></th>
            <th><label for="inputDefense">Defense</label></th>
            <th><label for="inputSPAttack">Sp. Attack</label></th>
            <th><label for="inputSPDefense">Sp. Defense</label></th>
            <th><label for="inputSpeed">Speed</label></th>
        </tr>
        </thead>

        <tbody>
        <tr>
            <td>
                <input id="inputHP" type="number" name="hp" step="1" value="">
            </td>

            <td>
                <input id="inputAttack" type="number" name="attack" step="1" value="">
            </td>

            <td>
                <input id="inputDefense" type="number" name="defense" step="1" value="">
            </td>

            <td>
                <input id="inputSPAttack" type="number" name="sp_attack" step="1"
                       value="">
            </td>

            <td>
                <input id="inputSPDefense" type="number" name="sp_defense" step="1"
                       value="">
            </td>

            <td>
                <input id="inputSpeed" type="number" name="speed" step="1" value="">
            </td>
        </tr>
        </tbody>
    </table>

    <button type="submit">Save</button>
</form>
</body>
</html>

<% session.removeAttribute("errors"); %>
