<%@ page import="demande.Demande" %>
<%@ page import="personne.Candidat" %>
<%@ page import="demande.ExperienceDemande" %>
<%@ page import="fournisseur.Fournisseur" %>
<%@ page import="demande.Annonce" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
<%@ page import="demande.Test" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 08/11/2024
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Demande demande = (Demande) request.getAttribute("demande");
  Candidat [] sug_employers = (Candidat[]) request.getAttribute("sug_employers");
  Candidat [] sug_personnes = (Candidat[]) request.getAttribute("sug_personnes");
  Candidat [] personneTesters = (Candidat[]) request.getAttribute("personneTesters");
  Fournisseur [] fournisseurs = (Fournisseur[]) request.getAttribute("fournisseurs");
  Annonce [] annonces = (Annonce[]) request.getAttribute("annonces");
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/questionnaire.css">

</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffListDemandeEnCours.DemandeController">Retour</a>
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
      <p><a id="bt1" class="bt-nav" onclick="nextSlide(1)">Stock</a></p>
      <p><a id="bt2" class="bt-nav" onclick="nextSlide(2)">Annonce</a></p>
      <p><a id="bt3" class="bt-nav" onclick="nextSlide(3)">Candidature</a></p>
      <p><a id="bt4" class="bt-nav" onclick="nextSlide(4)">Entretient</a></p>
    </nav>
    <div class="components">
      <div id="s1" class="sous-components">
        <h2>Liste :</h2>
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
          <% for (Candidat c : sug_employers) { %>
          <tr>
            <td><%= c.getPersonne().getId() %></td>
            <td><a href="<%=request.getContextPath()%>/AffCV.PersonneController?idDemande=<%=demande.getId()%>&&idPersonne=<%=c.getPersonne().getId()%>"><%= c.getPersonne().getNom() %></a></td>
            <td><%= c.getPersonne().getEmail() %></td>
            <td><%= c.getExperienceDemande().size() %>/<%=demande.getExperiences().size()%></td>
            <td><a href="<%=request.getContextPath()%>/AddDemandeValidation.DemandeController?idDemande=<%=demande.getId()%>&&idPersonne=<%=c.getPersonne().getId()%>">Affecter</a></td>
          </tr>
          <% } %>
          </tbody>
        </table>
      </div>
      <div id="s2" class="sous-components">
        <h2>Envoye d'annonce :</h2>
        <form action="<%=request.getContextPath()%>/AddAnnonce.DemandeController" method="post">
          <input type="hidden" name="idDemande" value="<%=demande.getId()%>">
          <div>
            <label>Fournisseur :</label>
            <select name="idFournisseur">
              <% for (Fournisseur f:fournisseurs) { %>
              <option value="<%=f.getId()%>"><%=f.getNom()%></option>
              <% } %>
            </select>
          </div>
          <div>
            <label>Date d'annonce :</label>
            <input type="date" name="date_annonce" required>
          </div>
          <button type="submit">envoyer</button>
        </form>
        <h2>Liste des annonces :</h2>
        <table>
          <thead>
          <tr>
            <th>#</th>
            <th>Fournisseur</th>
            <th>Intitule</th>
            <th>Date d'annonce</th>
          </tr>
          </thead>
          <tbody>
          <% for (Annonce a : annonces) {
            a.setDemande(demande);
          %>
          <tr>
            <td><%= a.getId() %></td>
            <td><%= a.getFournisseur().getNom() %></td>
            <td><%= a.getDemande().getIntitule()%></td>
            <td><%= a.getDate_ajout() %></td>
          </tr>
          <% } %>
          </tbody>
        </table>
      </div>
      <div id="s3" class="sous-components">
        <h2>Liste des candidat:</h2>
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
          <% Connection con = Base.PsqlConnect();
            for (Candidat c : sug_personnes) {
              Test test = new Test().findByIdPersonneAndIdDemande(demande.getId(), c.getPersonne().getId(),con);
              double note = -1;
              if (test!=null){
                note = test.getNote();
              }
              if (note==-1) {
          %>
          <tr>
            <td><%= c.getPersonne().getId() %></td>
            <td><a href="<%=request.getContextPath()%>/AffCV.PersonneController?idDemande=<%=demande.getId()%>&&idPersonne=<%=c.getPersonne().getId()%>"><%= c.getPersonne().getNom() %></a></td>
            <td><%= c.getPersonne().getEmail() %></td>
            <td><%= c.getExperienceDemande().size() %>/<%=demande.getExperiences().size()%></td>
            <% if (test==null) {%>
            <td><a href="<%=request.getContextPath()%>/AddTest.DemandeController?idDemande=<%=demande.getId()%>&&idPersonne=<%=c.getPersonne().getId()%>">passer un teste</a></td>
            <% } %>
            <% if (test!=null) {%>
            <td>teste encours</td>
            <% } %>
          </tr>
          <% } } %>
          </tbody>
        </table>
      </div>
      <div id="s4" class="sous-components">
        <h2>Entretien :</h2>
        <table>
          <thead>
          <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Adresse Email</th>
            <th>Experience</th>
            <th>Point</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          <%
            for (Candidat c : personneTesters) {
              Test test = new Test().findByIdPersonneAndIdDemande(demande.getId(), c.getPersonne().getId(),con);
          %>
          <tr>
            <td><%= c.getPersonne().getId() %></td>
            <td><a href="<%=request.getContextPath()%>/AffCV.PersonneController?idDemande=<%=demande.getId()%>&&idPersonne=<%=c.getPersonne().getId()%>"><%= c.getPersonne().getNom() %></a></td>
            <td><%= c.getPersonne().getEmail() %></td>
            <td><%= c.getExperienceDemande().size() %>/<%=demande.getExperiences().size()%></td>
            <td><%= test.getNote() %></td>
            <td><a href="<%=request.getContextPath()%>/AddDemandeValidation.DemandeController?idDemande=<%=demande.getId()%>&&idPersonne=<%=c.getPersonne().getId()%>">Embaucher</a></td>
          </tr>
          <% } %>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</section>
<script src="<%=request.getContextPath()%>/assets/js/script.js"></script>

</body>
</html>
