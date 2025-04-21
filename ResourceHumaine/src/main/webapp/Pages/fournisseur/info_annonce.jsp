<%@ page import="demande.Demande" %>
<%@ page import="personne.Candidat" %>
<%@ page import="fournisseur.Fournisseur" %>
<%@ page import="demande.ExperienceDemande" %>
<%@ page import="demande.Annonce" %>
<%@ page import="demande.Test" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 08/11/2024
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Fournisseur fournisseur = (Fournisseur) request.getSession().getAttribute("fournisseur");
  Annonce annonce = (Annonce) request.getAttribute("annonce");
  Demande demande = annonce.getDemande();
  Candidat[] sug_personnes = (Candidat[]) request.getAttribute("sug_personnes");
%>
<html>
<head>
  <title>Title</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">

</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffListAnnonce.FournisseurController?idFournisseur=<%=fournisseur.getId()%>">Retour</a>
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
  <div class="navigation">
    <nav>
    </nav>
    <div class="components">
      <div class="sous-components">
        <h2>Liste des personnes :</h2>
        <table>
          <thead>
          <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Adresse Email</th>
            <th>Note</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          <% for (Candidat c : sug_personnes) { %>
          <tr>
            <td><%= c.getPersonne().getId() %></td>
            <td><a href="<%=request.getContextPath()%>/AffInfo.PersonneController?idPersonne=<%=c.getPersonne().getId()%>"><%= c.getPersonne().getNom() %></a></td>
            <td><%= c.getPersonne().getEmail() %></td>
            <td><%= c.getExperienceDemande().size() %>/<%=demande.getExperiences().size()%></td>
            <td><a href="<%=request.getContextPath()%>/AddSuggestion.FournisseurController?idAnnonce=<%=annonce.getId()%>&&idPersonne=<%=c.getPersonne().getId()%>">Suggerer</a></td>
          </tr>
          <% } %>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</section>
</body>
</html>
