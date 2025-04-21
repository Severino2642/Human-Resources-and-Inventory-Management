<%@ page import="outils.MemeDemande" %>
<%@ page import="connexion.Base" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="departement.Departement" %>
<%@ page import="demande.Demande" %>
<%@ page import="departement.DepartementValidation" %>
<%@ page import="caisse.Caisse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemeDemande[] memeDemandes = (MemeDemande[]) request.getSession().getAttribute("memeDemandes");
    Connection connection = Base.PsqlConnect();
    Departement departementStock = new Departement().findByNom("Stock", connection);
    Departement departementAchat = new Departement().findByNom("Achat", connection);
    Departement departementFinance = new Departement().findByNom("Finance", connection);
    Caisse caisse = new Caisse().getDernierCaisse(connection);
    connection.close();
%>
<html>
<head>
    <title>Demande en cours en finance</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/demandeFinance.css">
</head>
<body>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>
    <p class="caisse-info">Montant de caisse : <%=caisse.getMontant()%> Ar</p>
    <h2>Demande en attente :</h2>
    <%
        Connection con = Base.PsqlConnect();
        for (MemeDemande md : memeDemandes) {
    %>
    <div class="demand-section">
        <details>
            <summary>
                <strong><%=md.getProduit().getNom()%></strong>
            </summary>
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Département</th>
                    <th>Intitulé</th>
                    <th>Quantité</th>
                    <th>Montant</th>
                    <th>Date demande</th>
                    <th>Date besoin</th>
                    <th colspan="2">Action</th>
                </tr>
                </thead>
                <tbody>
                <% for (Demande d : md.getDemandes()) {
                    DepartementValidation dpv = new DepartementValidation().findByIdDemandeAndIdDepartement(d.getId(), departementFinance.getId(), con);
                    double reste = caisse.getMontant() - dpv.getValeur();
                %>
                <tr>
                    <td><%=d.getId()%></td>
                    <td><%=d.getDepartement().getNom()%></td>
                    <td><%=d.getIntitule()%></td>
                    <td><%=d.getQuantite()%></td>
                    <td><%=dpv.getValeur()%> Ar</td>
                    <td><%=d.getDate_demande()%></td>
                    <td><%=d.getDate_besoin()%></td>
                    <td>
                        <% if (!dpv.isValid() && reste >= 0) { %>
                        <a class="action-link" href="<%=request.getContextPath()%>/Valide.FinanceController?idDepartement=<%=departementFinance.getId()%>&idDemande=<%=d.getId()%>">Accepter</a>
                        <% } %>
                    </td>
                    <td>
                        <% if (!dpv.isValid() && reste < 0) { %>
                        <span class="warning">Montant insuffisant</span>
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
