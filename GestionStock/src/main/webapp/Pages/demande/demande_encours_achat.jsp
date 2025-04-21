<%@ page import="demande.Demande" %>
<%@ page import="outils.MemeDemande" %>
<%@ page import="departement.Departement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
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
    <title>Demande en cours en achat</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/demandeAchat.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>
    <h2>Demande en attente :</h2>
    <%
        Connection con = Base.PsqlConnect();
        for (MemeDemande md : memeDemandes) {
    %>
    <div class="demand-section">
        <details>
            <summary>
                <strong><%= md.getProduit().getNom() %></strong> (<%= md.getTotalQuantite() %>)
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
                    int next_action = d.getNextAction(departementStock.getId(), departementFinance.getId(), departementAchat.getId(), con);
                %>
                <tr>
                    <td><%= d.getId() %></td>
                    <td><%= d.getDepartement().getNom() %></td>
                    <td><%= d.getIntitule() %></td>
                    <td><%= d.getQuantite() %></td>
                    <td><%= d.getDate_demande() %></td>
                    <td><%= d.getDate_besoin() %></td>
                    <td>
                        <% if (next_action >= 0) { %>
                        <% if (next_action == 0) { %>
                        <a class="action-link" href="<%=request.getContextPath()%>/SendRequest.AchatController?idDepartement=<%=departementStock.getId()%>&idDemande=<%=d.getId()%>&valeur=<%=d.getQuantite()%>">Vérifier stock</a>
                        <% } %>
                        <% if (next_action == 3) { %>
                        <a class="action-link" href="<%=request.getContextPath()%>/SendBonDeCommande.AchatController?idDemande=<%=d.getId()%>">Commander</a>
                        <% } %>
                        <% } else if (next_action == -1) { %>
                        <span>Vérification en cours</span>
                        <% } else if (next_action == -2) { %>
                        <a class="action-link" href="<%=request.getContextPath()%>/AffFournisseur.AchatController?idDepartement=<%=departementAchat.getId()%>&idDemande=<%=d.getId()%>">Vérifier fournisseur</a>
                        <% } %>
                    </td>
                    <td><a class="action-link" href="<%=request.getContextPath()%>/Rejeter.DemandeController?idDemande=<%=d.getId()%>">Rejeter</a></td>
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
