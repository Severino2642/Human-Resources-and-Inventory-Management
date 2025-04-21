<%@ page import="annexe.GestionStock" %>
<%@ page import="produit.Produit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Produit[] produits = (Produit[]) request.getAttribute("produits");
    GestionStock[] gestionStocks = (GestionStock[]) request.getAttribute("gestionStocks");
    String path = "AffInfo.DepartementController";
    if (request.getSession().getAttribute("admin") != null) {
        path = "Pages/home.jsp";
    }
%>
<html>
<head>
    <title>Liste des Produits</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/listeProd.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%= request.getContextPath() %>/<%= path %>">Retour</a>

    <form action="<%= request.getContextPath() %>/Insert.ProduitController" method="post">
        <h2>Insertion de Produit</h2>
        <div>
            <label>Nom :</label>
            <input type="text" name="nom" required>
        </div>
        <div>
            <label>Type de Gestion de Stock :</label>
            <select name="idGestionStock" required>
                <% for (GestionStock g : gestionStocks) { %>
                <option value="<%= g.getId() %>"><%= g.getNom() %></option>
                <% } %>
            </select>
        </div>
        <button type="submit">Valider</button>
    </form>

    <h2>Liste des Produits :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Type de Gestion de Stock</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Produit p : produits) { %>
        <tr>
            <td><%= p.getId() %></td>
            <td><a href="<%= request.getContextPath() %>/AffInfo.ProduitController?idProduit=<%= p.getId() %>"><%= p.getNom() %></a></td>
            <td><%= p.getGestionStock().getNom() %></td>
            <td><a href="<%= request.getContextPath() %>/Delete.ProduitController?idProduit=<%= p.getId() %>">Supprimer</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
