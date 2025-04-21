<%@ page import="demande.Demande" %>
<%@ page import="annexe.Talent" %>
<%@ page import="departement.Departement" %>
<%@ page import="demande.ExperienceDemande" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 07/11/2024
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Demande demande = (Demande) request.getSession().getAttribute("demande");
  Departement departement = (Departement) request.getSession().getAttribute("departement");
  Connection con = Base.PsqlConnect();
  Talent[] talents = new Talent().findAll("",con).toArray(new Talent[]{});
  con.close();
%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
</head>
<body>
<a href="<%=request.getContextPath()%>/AffList.DemandeController?idDepartement=<%=departement.getId()%>">Retour</a>
<section class="body">
  <% if (demande.getExperiences().size() > 0){ %>
    <a class="back-link" href="<%=request.getContextPath()%>/Insert.DemandeController?idDepartement=<%=departement.getId()%>">Valider</a>
  <% } %>
  <form action="<%=request.getContextPath()%>/AddExperience.DemandeController" method="post">
    <h2>Ajout d'experience</h2>
    <div>
      <label>duree :</label>
      <input type="number" name="duree" required>
    </div>
    <div>
      <label>Talent :</label>
      <select name="idTalent">
        <% for (Talent t:talents) { %>
        <option value="<%=t.getId()%>"><%=t.getNom()%></option>
        <% } %>
      </select>
    </div>
    <button type="submit">ajouter</button>
  </form>

  <h2>Liste d'experience :</h2>
  <table>
    <thead>
    <tr>
      <th>#</th>
      <th>Talent</th>
      <th>Duree</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      int id = 0;
      for (ExperienceDemande e : demande.getExperiences()) { %>
    <tr>
      <td><%= e.getId() %></td>
      <td><%= e.getTalent().getNom() %></td>
      <td><%= e.getDuree() %> ans</td>
      <td><a href="<%=request.getContextPath()%>/DeleteExperience.DemandeController?idExperience=<%=id%>">supprimer</a></td>
    </tr>
    <% id++;} %>
    </tbody>
  </table>
</section>
</body>
</html>
