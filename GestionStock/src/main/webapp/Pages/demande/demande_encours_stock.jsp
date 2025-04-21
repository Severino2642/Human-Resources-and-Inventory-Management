<%@ page import="outils.MemeDemande" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
<%@ page import="departement.Departement" %>
<%@ page import="demande.Demande" %>
<%@ page import="departement.DepartementValidation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemeDemande[] memeDemandes = (MemeDemande[]) request.getSession().getAttribute("memeDemandes");
    Connection connection = Base.PsqlConnect();
    Departement departementStock = new Departement().findByNom("Stock", connection);
    Departement departementAchat = new Departement().findByNom("Achat", connection);
    Departement departementFinance = new Departement().findByNom("Finance", connection);
    connection.close();
%>
<html>
<head>
    <title>Demande en cours en stock</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/demandeStock.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>
    <h2>Demandes en attente :</h2>
    <%
        Connection con = Base.PsqlConnect();
        for (MemeDemande md : memeDemandes) {
            md.getProduit().setStock();
    %>
    <div class="demand-section">
        <details>
            <summary>
                <strong><%=md.getProduit().getNom()%></strong>
                <p>Quantité demandée : <%=md.getTotalQuantite()%></p>
                <p>Stock restant : <%=md.getProduit().getStock().getQuantite()%></p>
            </summary>
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Département</th>
                    <th>Intitulé</th>
                    <th>Quantité</th>
                    <th>Date demande</th>
                    <th>Date besoin</th>
                    <th colspan="2">Action</th>
                </tr>
                </thead>
                <tbody>
                <% for (Demande d : md.getDemandes()) {
                    DepartementValidation dpv = new DepartementValidation().findByIdDemandeAndIdDepartement(d.getId(), departementStock.getId(), con);
                    double reste = md.getProduit().getStock().getQuantite() - d.getQuantite();
                %>
                <tr>
                    <td><%=d.getId()%></td>
                    <td><%=d.getDepartement().getNom()%></td>
                    <td><%=d.getIntitule()%></td>
                    <td><%=d.getQuantite()%></td>
                    <td><%=d.getDate_demande()%></td>
                    <td><%=d.getDate_besoin()%></td>
                    <td>
                        <% if (!dpv.isValid() && reste >= 0) { %>
                        <a class="action-link" href="<%=request.getContextPath()%>/Valider.StockController?idProduit=<%=d.getIdProduit()%>&idDemande=<%=d.getId()%>&valeur=<%=d.getQuantite()%>">Accepter</a>
                        <% } %>
                    </td>
                    <td>
                        <% if (!dpv.isValid() && reste < 0) { %>
                        <a class="action-link" href="<%=request.getContextPath()%>/SendRequest.StockController?idDepartementStock=<%=departementStock.getId()%>&idDepartement=<%=departementAchat.getId()%>&idDemande=<%=d.getId()%>&valeur=<%=(reste * -1)%>">Demande d'achat</a>
                        <% } %>
                    </td>
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
