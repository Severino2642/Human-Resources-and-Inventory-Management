<%@ page import="demande.Demande" %>
<%@ page import="departement.DepartementValidation" %>
<%@ page import="fournisseur.ProduitFournisseur" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
<%@ page import="fournisseur.Fournisseur" %>
<%@ page import="fournisseur.Incident" %>
<%@ page import="departement.Departement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Demande demande = (Demande) request.getAttribute("demande");
    DepartementValidation dpvAchat = (DepartementValidation) request.getAttribute("dpvAchat");
    ProduitFournisseur[] produitFournisseurs = (ProduitFournisseur[]) request.getAttribute("produitFournisseurs");
%>
<html>
<head>
    <title>Liste des pro-formats</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeProformat.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/AffListDemandeEncours.DemandeController">Retour</a>
    <h2>Liste des fournisseurs :</h2>
    <%
        Connection con = Base.PsqlConnect();
        Departement departementFinance = new Departement().findByNom("Finance", con);
        Departement departementAchat = new Departement().findByNom("Achat", con);
        for (ProduitFournisseur pf : produitFournisseurs) {
            Fournisseur fournisseur = new Fournisseur().findById(pf.getIdFournisseur(), con);
            Incident[] incidents = new Incident().findByIdFournisseur(pf.getIdFournisseur(), con);
            double montant_total = pf.getPrix_unitaire() * dpvAchat.getValeur();
    %>
    <div class="fournisseur">
        <details>
            <summary>
                <strong><%=fournisseur.getNom()%></strong>
                <p>Quantité demandée : <%=dpvAchat.getValeur()%></p>
                <p>Prix unitaire : <%=pf.getPrix_unitaire()%></p>
                <p>Prix total : <%=montant_total%></p>
                <p>Nombre d'incidents : <%=incidents.length%></p>
                <a class="accept-link" href="<%=request.getContextPath()%>/SendRequestVersFinance.AchatController?idFournisseur=<%=fournisseur.getId()%>&&idDepartementAchat=<%=departementAchat.getId()%>&&idDepartement=<%=departementFinance.getId()%>&&idDemande=<%=demande.getId()%>&&valeur=<%=montant_total%>">Accepter</a>
            </summary>
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Remarque</th>
                    <th>Date d'incident</th>
                </tr>
                </thead>
                <tbody>
                <% for (Incident ic : incidents) { %>
                <tr>
                    <td><%=ic.getId()%></td>
                    <td><%=ic.getRemarque()%></td>
                    <td><%=ic.getDate_incident()%></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </details>
    </div>
    <% } con.close(); %>
</section>
</body>
</html>
