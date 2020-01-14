<%@page pageEncoding="utf-8" %>
<%@page import="app.entity.*" %>
<%@page import="com.modscleo4.framework.entity.IModelCollection" %>
<%@page import="com.modscleo4.framework.entity.ModelCollection" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>

<%
    Pokemon pokemon = (Pokemon) session.getAttribute("pokemon");

    Category category = pokemon.category();
    IModelCollection<Ability> abilities = pokemon.abilities();
    IModelCollection<Gender> genders = pokemon.genders().sortBy("name");
    Stats stats = pokemon.stats();
    IModelCollection<Type> types = pokemon.types();
    IModelCollection<Weakness> weaknesses = pokemon.weaknesses().sortByDesc("effectiveness").where(w -> w.getEffectiveness() > 3);

    List<String> gs = new ArrayList<>();
    for (Gender g : genders) {
        gs.add(g.getName());
    }
%>

<html>
    <head>
        <title><%= pokemon.getName() %></title>

        <meta charset="utf-8">

        <style type=text/css>
            body {
                font-family: sans-serif;
                text-align: center;
            }

            #main {
                display: grid;
                justify-items: center;
                text-align: left;
            }

            #name {
                padding: 10px;
            }

            #p1 {
                display: grid;
                grid-template-columns: 1fr 1fr;
                justify-items: center;
            }

            #desc {
                display: grid;
                grid-template-rows: 1fr 1.5fr;
            }

            #info {
                display: grid;
                grid-template-columns: 1fr 1fr;
                grid-template-rows: 1fr 1fr 1fr;
                grid-row-gap: 10px;
                grid-column-gap: 10px;
            }

            #p2 {
                display: grid;
                grid-template-columns: 1.5fr 1fr;
                justify-items: center;
            }

            #stats {

            }

            #types_weaknesses {
                display: grid;
                justify-items: left;
                grid-row-gap: 10px;
            }

            #types {
                display: grid;
                grid-template-columns: 1fr 1fr 1fr;
                grid-row-gap: 5px;
                grid-column-gap: 5px;
                justify-items: center;
            }

            #weaknesses {
                display: grid;
                grid-template-columns: 1fr 1fr 1fr;
                grid-row-gap: 5px;
                grid-column-gap: 5px;
                justify-items: center;
            }

            .card {
                display: grid;
                grid-template-rows: 4fr 1fr 1fr 1fr;
            }

            .card img {
                width: 150px;
            }

            .card.mini img {
                width: 75px;
                grid-template-rows: 4fr 1fr 1fr 1fr;
            }

            .multicard {
                display: grid;
                grid-template-columns: 1fr 1fr 1fr 1fr;
                grid-template-rows: 1fr 1fr;
                grid-row-gap: 10px;
                grid-column-gap: 10px;
            }

            #p3 {
                display: grid;
                grid-template-columns: 1fr 1fr 1fr;
                grid-column-gap: 40px;
                justify-items: center;
            }
        </style>
    </head>

    <body>
        <div id="main">
            <div id="name">
                <%= pokemon.getName() %> | Nº <%= String.format("%03d", pokemon.getId()) %>
            </div>

            <div id="p1">
                <img src="<%= String.format("https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png", pokemon.getId()) %>" height="200"/>

                <div id="desc">
                    <span><%= pokemon.getDescription().replace("\\n", "<br />") %></span>

                    <div id="info">
                        <span>
                            <span>Height</span><br />
                            <span><%= pokemon.getHeight() %> m</span>
                        </span>

                        <span>
                            <span>Weight</span><br />
                            <span><%= pokemon.getWeight() %> kg</span>
                        </span>

                        <span>
                            <span>Category</span><br />
                            <span><%= category.getName() %></span>
                        </span>

                        <span>
                            <span>Abilities</span><br />
                            <span>
                                <% for (Ability a : abilities) { %>
                                    <span title="<%= a.getDescription() %>"><%= a.getName() %></span><br />
                                <% } %>
                            </span>
                        </span>

                        <span>
                            <span>Genders</span><br />
                            <span>
                                <%= String.join(", ", gs) %>
                            </span>
                        </span>
                    </div>
                </div>
            </div>

            <hr />
            <span>Stats</span><br />

            <div id="p2">
                <table id="stats">
                    <thead>
                        <tr>
                            <th>HP</th>
                            <th>Attack</th>
                            <th>Defense</th>
                            <th>Sp. Attack</th>
                            <th>Sp. Defense</th>
                            <th>Speed</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td><%= stats.getHP() %></td>
                            <td><%= stats.getAttack() %></td>
                            <td><%= stats.getDefense() %></td>
                            <td><%= stats.getSpecialAttack() %></td>
                            <td><%= stats.getSpecialDefense() %></td>
                            <td><%= stats.getSpeed() %></td>
                        </tr>
                    </tbody>
                </table>

                <div id="types_weaknesses">
                    <div>
                        <span>Types</span>

                        <div id="types">
                            <% for (Type t : types) { %>
                                <span style="color: <%= t.getHTMLColor() %>">
                                    <%= t.getName() %>
                                </span>
                            <% } %>
                        </div>
                    </div>

                    <div>
                        <span>Weaknesses</span>

                        <div id="weaknesses">
                            <% for (Weakness w : weaknesses) {
                                int effectiveness = w.getEffectiveness();
                                String eff = "";
                                switch (effectiveness) {
                                    case 0:
                                        eff = "0";
                                        break;
                                    case 1:
                                        eff = "⅛";
                                        break;
                                    case 2:
                                        eff = "¼";
                                        break;
                                    case 3:
                                        eff = "½";
                                        break;
                                    case 5:
                                        eff = "2";
                                        break;
                                    case 6:
                                        eff = "4";
                                        break;
                                } %>
                                <span title="Effectiveness: <%= eff %>" style="color: <%= w.getHTMLColor() %>">
                                    <%= w.getName() %>
                                </span>
                            <% } %>
                        </div>
                    </div>
                </div>
            </div>

            <hr />
            <span>Evolutions</span><br />

            <div id="p3">
                <%
                    IModelCollection<Pokemon> evolutions = new ModelCollection<>();
                    Pokemon p = pokemon;
                    while (p.predecessor() != null) {
                        p = p.predecessor();
                    }

                    evolutions.add(p);
                    if (p.successor() != null) {
                        while (p.successor() != null) {
                            p = p.successor();
                            evolutions.add(p);
                        }

                        for (Pokemon evolution : evolutions) { %>
                            <div class="card">
                                <img src="<%= String.format("https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png", evolution.getId()) %>" />
                                <span>Nº <%= evolution.getId() %></span>
                                <span><%= evolution.getName() %></span>
                                <span>
                                    <% for (Type t : evolution.types()) { %>
                                        <span style="color: <%= t.getHTMLColor() %>">
                                            <%= t.getName() %>
                                        </span>
                                    <% } %>
                                </span>
                            </div>
                        <% }
                    } else {
                        for (Pokemon evolution : evolutions) {%>
                            <div class="card">
                                <img src="<%= String.format("https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png", evolution.getId()) %>" />
                                <span>Nº <%= evolution.getId() %></span>
                                <span><%= evolution.getName() %></span>
                                <span>
                                    <% for (Type t : evolution.types()) { %>
                                        <span style="color: <%= t.getHTMLColor() %>">
                                            <%= t.getName() %>
                                        </span>
                                    <% } %>
                                </span>
                            </div>
                        <% }

                        IModelCollection<Pokemon> sucessors = p.successors().sortBy("id");
                        if (sucessors.size() > 0) { %>
                            <div class="multicard">
                                <% for (Pokemon s : sucessors) { %>
                                    <div class="card mini">
                                        <img src="<%= String.format("https://assets.pokemon.com/assets/cms2/img/pokedex/full/%03d.png", s.getId()) %>" />
                                        <span>Nº <%= s.getId() %></span>
                                        <span><%= s.getName() %></span>
                                        <span>
                                            <% for (Type t : s.types()) { %>
                                                <span style="color: <%= t.getHTMLColor() %>">
                                                    <%= t.getName() %>
                                                </span>
                                            <% } %>
                                        </span>
                                    </div>
                                <% } %>
                            </div>
                        <% }
                    }
                %>
            </div>
        </div>



        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

        <script type="text/javascript">
            jQuery(document).ready(function () {
                let len = jQuery('#p3').children().length;
                jQuery('#p3').css('grid-template-columns', '1fr '.repeat(len));
            });
        </script>
    </body>
</html>
