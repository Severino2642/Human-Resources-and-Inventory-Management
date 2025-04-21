<%@ page import="departement.Departement" %>
<%@ page import="demande.Demande" %>
<%@ page import="demande.ExperienceDemande" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 07/11/2024
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Departement departement = (Departement) request.getSession().getAttribute("departement");
    Demande demande = (Demande) request.getAttribute("demande");
%>
<html>
<head>
    <title>Info demande</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffList.DemandeController?idDepartement=<%=departement.getId()%>">Retour</a>
<section class="body">
    <div>
        <h2>Information demande</h2>
        <p>Intitule : <%=demande.getIntitule()%></p>
        <p>Quantite : <%=demande.getQuantite()%></p>
        <p>Date demande : <%=demande.getDate_demande()%></p>
    </div>
    <h2>Liste d'experience :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Talent</th>
            <th>Duree</th>
        </tr>
        </thead>
        <tbody>
        <% for (ExperienceDemande e : demande.getExperiences()) { %>
        <tr>
            <td><%= e.getId() %></td>
            <td><%= e.getTalent().getNom() %></td>
            <td><%= e.getDuree() %> ans</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
