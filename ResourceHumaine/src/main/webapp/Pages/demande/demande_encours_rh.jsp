<%@ page import="demande.Demande" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 07/11/2024
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Demande [] demandes = (Demande[]) request.getAttribute("demandes");
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">

</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>
<section class="body">
    <h2>Demande en cours :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Departement</th>
            <th>Intitule</th>
            <th>Experience</th>
            <th>Quantite</th>
            <th>Date demande</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Demande d : demandes) {
                d.setDemandeValidations();
                d.setExperiences();
        %>
        <tr>
            <td><%=d.getId()%></td>
            <td><%=d.getDepartement().getNom()%></td>
            <td><a href="<%=request.getContextPath()%>/AffInfoAvis.DemandeController?idDemande=<%=d.getId()%>"><%=d.getIntitule()%></a></td>
            <td><%=d.getExperiences().size()%></td>
            <td><%=d.getQuantite()%></td>
            <td><%=d.getDate_demande()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>

</body>
</html>
