<%@ page import="facture.BonDeLivraison" %>
<%@ page import="facture.BonDeReception" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
<%@ page import="facture.BonDeCommande" %>
<%@ page import="produit.Produit" %>
<%@ page import="fournisseur.Fournisseur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BonDeLivraison[] bonDeLivraisons = (BonDeLivraison[]) request.getAttribute("bonDeLivraisons");
    BonDeReception[] bondDeReceptions = (BonDeReception[]) request.getAttribute("bonDeReceptions");
%>
<html>
<head>
    <title>Liste des bons de réception</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeReception.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>
    <form action="#form-valide" method="get">
        <div>
            <label>Bon de Livraison :</label>
            <select name="idBonDeLivraison" required>
                <option value="">Sélectionner un bon de livraison</option>
                <% for (BonDeLivraison bl : bonDeLivraisons) { %>
                <option value="<%=bl.getId()%>"><%=bl.getId()%></option>
                <% } %>
            </select>
        </div>
        <button type="submit">Suivant</button>
    </form>

    <div id="form-valide">
        <% if (request.getParameter("idBonDeLivraison") != null) {
            int id = Integer.parseInt(request.getParameter("idBonDeLivraison"));
            BonDeLivraison bl = new BonDeLivraison().findById(id, null);
        %>
        <form action="<%=request.getContextPath()%>/AddBonDeReception.StockController" method="post">
            <input type="hidden" value="<%=id%>" name="idBonDeLivraison">
            <div>
                <label>Quantité :</label>
                <input type="number" name="quantite" min="0" value="<%=bl.getQuantite()%>" required>
            </div>
            <div>
                <label>Montant :</label>
                <input type="number" name="montant" min="0" value="<%=bl.getMontant()%>" required>
            </div>
            <div>
                <label>Date de réception :</label>
                <input type="date" name="date_recu" required>
            </div>
            <button type="submit">Valider</button>
        </form>
        <% } %>
    </div>

    <h2>Liste des Bons de Réception :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Bon de Livraison</th>
            <th>Fournisseur</th>
            <th>Produit</th>
            <th>Quantité</th>
            <th>Montant</th>
            <th>Date de Réception</th>
        </tr>
        </thead>
        <tbody>
        <%
            Connection con = Base.PsqlConnect();
            for (BonDeReception br : bondDeReceptions) {
                BonDeLivraison bonDeLivraison = new BonDeLivraison().findById(br.getIdBonDeLivraison(), con);
                BonDeCommande bonDeCommande = new BonDeCommande().findById(bonDeLivraison.getIdBonDeCommande(), con);
                Produit p = new Produit().findById(bonDeCommande.getIdProduit(), con);
                Fournisseur f = new Fournisseur().findById(bonDeCommande.getIdFournisseur(), con);
        %>
        <tr>
            <td><%=br.getId()%></td>
            <td><%=br.getIdBonDeLivraison()%></td>
            <td><%=f.getNom()%></td>
            <td><%=p.getNom()%></td>
            <td><%=br.getQuantite()%></td>
            <td><%=br.getMontant()%></td>
            <td><%=br.getDate_recu()%></td>
        </tr>
        <% } con.close(); %>
        </tbody>
    </table>
</section>
</body>
</html>
