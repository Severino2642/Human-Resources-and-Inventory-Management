<%@ page import="fournisseur.Fournisseur" %>
<%@ page import="produit.Produit" %>
<%@ page import="fournisseur.ProduitFournisseur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Fournisseur fournisseur = (Fournisseur) request.getAttribute("fournisseur");
  Produit[] produits = (Produit[]) request.getAttribute("produits");
%>
<html>
<head>
  <title>Information du Fournisseur</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/infoFournisseur.css">
</head>
<body>
<section class="body">
  <a class="back-link" href="<%=request.getContextPath()%>/AffList.FournisseurController">Retour</a>
  <a class="back-link" href="<%=request.getContextPath()%>/AffListBonDeLivraison.FournisseurController">Bon de Livraison</a>

  <form action="<%=request.getContextPath()%>/AddProduit.FournisseurController" method="post">
    <h2>Ajouter Produit</h2>
    <input type="hidden" value="<%=fournisseur.getId()%>" name="idFournisseur">
    <div>
      <label>Produits :</label>
      <select name="idProduit" required>
        <% for (Produit p : produits) { %>
        <option value="<%=p.getId()%>"><%=p.getNom()%></option>
        <% } %>
      </select>
    </div>
    <div>
      <label>Prix Unitaire :</label>
      <input type="number" name="prix_unitaire" min="0" required>
    </div>
    <div>
      <label>Date d'Ajout :</label>
      <input type="date" name="date_modif" required>
    </div>
    <button type="submit">Valider</button>
  </form>

  <h2>Liste des Produits :</h2>
  <table>
    <thead>
    <tr>
      <th>#</th>
      <th>Nom</th>
      <th>Prix Unitaire</th>
      <th>Date d'Ajout</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% for (ProduitFournisseur pf : fournisseur.getProduitFournisseurs()) { %>
    <tr>
      <td><%=pf.getId()%></td>
      <td><%=pf.getProduit().getNom()%></td>
      <td><%=pf.getPrix_unitaire()%></td>
      <td><%=pf.getDate_modif()%></td>
      <td>
        <a href="<%=request.getContextPath()%>/DeleteProduit.FournisseurController?idFournisseur=<%=pf.getIdFournisseur()%>&idProduit=<%=pf.getIdProduit()%>">Supprimer</a>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</section>
</body>
</html>
