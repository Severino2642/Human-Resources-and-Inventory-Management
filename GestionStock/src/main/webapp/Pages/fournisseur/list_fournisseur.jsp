<%@ page import="fournisseur.Fournisseur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Fournisseur[] fournisseurs = (Fournisseur[]) request.getAttribute("fournisseurs");
%>
<html>
<head>
    <title>Liste des Fournisseurs</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeFournisseur.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/Pages/home.jsp">Retour</a>

    <form action="<%=request.getContextPath()%>/Insert.FournisseurController" method="post">
        <h2>Insertion de Fournisseur</h2>
        <div>
            <label>Nom :</label>
            <input type="text" name="nom" required>
        </div>
        <div>
            <label>Date d'ajout :</label>
            <input type="date" name="date_ajout" required>
        </div>
        <button type="submit">Valider</button>
    </form>

    <h2>Liste des Fournisseurs :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Date d'ajout</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Fournisseur f : fournisseurs) { %>
        <tr>
            <td><%= f.getId() %></td>
            <td><a href="<%= request.getContextPath() %>/AffInfo.FournisseurController?idFournisseur=<%= f.getId() %>"><%= f.getNom() %></a></td>
            <td><%= f.getDate_ajout() %></td>
            <td><a class="delete-link" href="<%= request.getContextPath() %>/Delete.FournisseurController?idFournisseur=<%= f.getId() %>">Supprimer</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
