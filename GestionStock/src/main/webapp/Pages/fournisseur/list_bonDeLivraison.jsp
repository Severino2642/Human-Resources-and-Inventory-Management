<%@ page import="facture.BonDeCommande" %>
<%@ page import="facture.BonDeLivraison" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
<%@ page import="produit.Produit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  BonDeCommande[] bonDeCommandes = (BonDeCommande[]) request.getAttribute("bonDeCommandes");
  BonDeLivraison[] bonDeLivraisons = (BonDeLivraison[]) request.getAttribute("bonDeLivraisons");
%>
<html>
<head>
  <title>Liste des Bons de Livraisons</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeBonLivre.css">
</head>
<body>
<section class="body">
  <a class="back-link" href="<%=request.getContextPath()%>/AffInfo.FournisseurController">Retour</a>

  <form action="#form-valide" method="GET">
    <div>
      <label>Bon de Commande :</label>
      <select name="idBonDeCommande" required>
        <% for (BonDeCommande bc : bonDeCommandes) { %>
        <option value="<%=bc.getId()%>"><%=bc.getId()%></option>
        <% } %>
      </select>
    </div>
    <button type="submit">Suivant</button>
  </form>

  <div id="form-valide">
    <% if (request.getParameter("idBonDeCommande") != null) {
      int id = Integer.parseInt(request.getParameter("idBonDeCommande"));
      BonDeCommande bc = new BonDeCommande().findById(id, null);
    %>
    <form action="<%=request.getContextPath()%>/AddBonDeLivraison.FournisseurController" method="post">
      <input type="hidden" value="<%=id%>" name="idBonDeCommande">
      <div>
        <label>Quantité :</label>
        <input type="number" name="quantite" min="0" value="<%=bc.getQuantite()%>" required>
      </div>
      <div>
        <label>Montant :</label>
        <input type="number" name="montant" min="0" value="<%=bc.getMontant()%>" required>
      </div>
      <div>
        <label>Date de Livraison :</label>
        <input type="date" name="date_livraison" required>
      </div>
      <button type="submit">Valider</button>
    </form>
    <% } %>
  </div>

  <h2>Liste des Bons de Livraison :</h2>
  <table>
    <thead>
    <tr>
      <th>#</th>
      <th>Bon de Commande</th>
      <th>Produit</th>
      <th>Quantité</th>
      <th>Montant</th>
      <th>Date de Livraison</th>
    </tr>
    </thead>
    <tbody>
    <%
      Connection con = Base.PsqlConnect();
      for (BonDeLivraison bl : bonDeLivraisons) {
        BonDeCommande bonDeCommande = new BonDeCommande().findById(bl.getIdBonDeCommande(), con);
        Produit p = new Produit().findById(bonDeCommande.getIdProduit(), con);
    %>
    <tr>
      <td><%=bl.getId()%></td>
      <td><%=bl.getIdBonDeCommande()%></td>
      <td><%=p.getNom()%></td>
      <td><%=bl.getQuantite()%></td>
      <td><%=bl.getMontant()%></td>
      <td><%=bl.getDate_livraison()%></td>
    </tr>
    <% } con.close(); %>
    </tbody>
  </table>
</section>
</body>
</html>
