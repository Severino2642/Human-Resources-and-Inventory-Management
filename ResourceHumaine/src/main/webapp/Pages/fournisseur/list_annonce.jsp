<%@ page import="demande.Annonce" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 08/11/2024
  Time: 20:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Annonce [] annonces = (Annonce[]) request.getAttribute("annonces");
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">

</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffInfo.FournisseurController">Retour</a>
<section class="body">
  <h2>Liste des annonces :</h2>
  <table>
    <thead>
    <tr>
      <th>#</th>
      <th>Intitule</th>
      <th>Experience</th>
      <th>Quantite</th>
      <th>Date d'annonce</th>
    </tr>
    </thead>
    <tbody>
    <%
      for (Annonce a : annonces) {
        a.getDemande().setDemandeValidations();
        a.getDemande().setExperiences();
    %>
    <tr>
      <td><%=a.getId()%></td>
      <td><a href="<%=request.getContextPath()%>/AffInfoAnnonce.FournisseurController?idAnnonce=<%=a.getId()%>"><%=a.getDemande().getIntitule()%></a></td>
      <td><%=a.getDemande().getExperiences().size()%></td>
      <td><%=a.getDemande().getQuantite()%></td>
      <td><%=a.getDate_ajout()%></td>
    </tr>
    <% } %>
    </tbody>
  </table>
</section>
</body>
</html>
