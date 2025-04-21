<%@ page import="facture.BonDeCommande" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
<%@ page import="produit.Produit" %>
<%@ page import="fournisseur.Fournisseur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BonDeCommande[] bonDeCommandes = (BonDeCommande[]) request.getAttribute("bonDeCommandes");
%>
<html>
<head>
    <title>Liste des bons de commande</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeCommande.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>
    <h2>Bons de commande en attente :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Produit</th>
            <th>Fournisseur</th>
            <th>Quantité</th>
            <th>Montant</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            Connection con = Base.PsqlConnect();
            for (BonDeCommande b : bonDeCommandes) {
                Produit p = new Produit().findById(b.getIdProduit(), con);
                Fournisseur f = new Fournisseur().findById(b.getIdFournisseur(), con);
        %>
        <tr>
            <td><%=b.getId()%></td>
            <td><%=p.getNom()%></td>
            <td><%=f.getNom()%></td>
            <td><%=b.getQuantite()%></td>
            <td><%=b.getMontant()%></td>
            <td>
                <form action="<%=request.getContextPath()%>/ValideBonDeCommande.AchatController" method="post">
                    <input type="hidden" value="<%=b.getId()%>" name="idBonDeCommande">
                    <input type="date" name="date_besoin" required>
                    <button type="submit" class="action-button">Accepter</button>
                </form>
            </td>
        </tr>
        <% } con.close(); %>
        </tbody>
    </table>
</section>
</body>
</html>
