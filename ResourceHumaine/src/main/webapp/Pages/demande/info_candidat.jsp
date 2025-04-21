<%@ page import="personne.Personne" %>
<%@ page import="annexe.Talent" %>
<%@ page import="demande.Demande" %>
<%@ page import="personne.Experience" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 09/11/2024
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Demande demande = (Demande) request.getAttribute("demande");
  Personne personne = (Personne) request.getAttribute("personne");
%>
<html>
<head>
  <title>Info CV</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
</head>
<body>
<a href="<%=request.getContextPath()%>/AffInfoAvis.DemandeController?idDemande=<%=demande.getId()%>">Retour</a>
<section class="body">
  <h2>CV</h2>
  <p><strong>Nom :</strong> <%=personne.getNom()%></p>
  <p><strong>Email :</strong> <%=personne.getEmail()%></p>
  <p><strong>Sexe :</strong> <%=personne.getSexe().getIntitule()%></p>

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
    <% for (Experience e : personne.getExperiences()) { %>
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
