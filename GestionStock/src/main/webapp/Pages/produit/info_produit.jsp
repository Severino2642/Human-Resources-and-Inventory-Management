<%@ page import="produit.Produit" %>
<%@ page import="produit.PrixProduit" %>
<%@ page import="produit.StockProduit" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Produit produit = (Produit) request.getAttribute("produit");
    PrixProduit[] prixVentes = (PrixProduit[]) request.getAttribute("prixVentes");
    PrixProduit[] prixAchats = (PrixProduit[]) request.getAttribute("prixAchats");

    Connection con = Base.PsqlConnect();
    StockProduit[] stockEntrer = new StockProduit().findByIdProduitAndIsEntrer(produit.getId(), true, con);
    StockProduit[] stockSortie = new StockProduit().findByIdProduitAndIsEntrer(produit.getId(), false, con);
    con.close();

    Double[] valeurProduit = produit.getValeur(stockEntrer, stockSortie);
%>
<html>
<head>
    <title>Information des Produits</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/infoProd.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%= request.getContextPath() %>/AffList.ProduitController">Retour</a>

    <div class="product-info">
        <h2>Information Produit</h2>
        <p><strong>Nom :</strong> <%= produit.getNom() %></p>
        <p><strong>Type de gestion de stock :</strong> <%= produit.getGestionStock().getNom() %></p>
        <p><strong>Prix Unitaire :</strong> <%= valeurProduit[0] %> Ar</p>
        <p><strong>Stock Entré :</strong> <%= valeurProduit[1] %></p>
        <p><strong>Stock Sortie :</strong> <%= valeurProduit[2] %></p>
    </div>

    <div class="stock-status">
        <div class="stock-table">
            <h2>État de Stock Sortie</h2>
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Quantité</th>
                    <th>Prix Unitaire</th>
                    <th>Date Sortie</th>
                </tr>
                </thead>
                <tbody>
                <% for (StockProduit sp : stockSortie) { %>
                <tr>
                    <td><%= sp.getId() %></td>
                    <td><%= sp.getQuantite() %></td>
                    <td><%= sp.getPrix_unitaire() %></td>
                    <td><%= sp.getDate_modification() %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>

        <div class="stock-table">
            <h2>État de Stock Entré</h2>
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Quantité</th>
                    <th>Prix Unitaire</th>
                    <th>Date Entrée</th>
                </tr>
                </thead>
                <tbody>
                <% for (StockProduit sp : stockEntrer) { %>
                <tr>
                    <td><%= sp.getId() %></td>
                    <td><%= sp.getQuantite() %></td>
                    <td><%= sp.getPrix_unitaire() %></td>
                    <td><%= sp.getDate_modification() %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</section>
</body>
</html>
