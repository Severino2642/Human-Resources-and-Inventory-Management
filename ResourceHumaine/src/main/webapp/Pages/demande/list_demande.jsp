<%@ page import="demande.Demande" %>
<%@ page import="annexe.Sexe" %>
<%@ page import="departement.Departement" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 07/11/2024
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  request.getSession().setAttribute("demande",null);
  Departement departement = (Departement) request.getSession().getAttribute("departement");
  Demande [] demandes = (Demande[]) request.getAttribute("demandes");
  Sexe [] sexes = (Sexe[]) request.getAttribute("sexes");
%>
<html>
<head>
    <title>Liste demande</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeDept.css">

</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>
<section class="body">
  <form action="<%=request.getContextPath()%>/AffAddExperience.DemandeController" method="post">
    <h2>Insertion de demande</h2>
    <input type="hidden" name="idDepartement" value="<%=departement.getId()%>">
    <div>
      <label>Intitule :</label>
      <input type="text" name="intitule" required>
    </div>
    <div>
      <label>Sexe :</label>
      <select name="idSexe">
        <option value="0">Tout</option>
        <% for (Sexe s:sexes) { %>
        <option value="<%=s.getId()%>"><%=s.getIntitule()%></option>
        <% } %>
      </select>
    </div>
    <div>
      <label>Quantite :</label>
      <input type="text" name="quantite" required>
    </div>
    <div>
      <label>Date de demande :</label>
      <input type="date" name="date_demande" required>
    </div>
    <button type="submit">Valider</button>
  </form>
  <h2>Liste des demandes :</h2>
  <table>
    <thead>
    <tr>
      <th>#</th>
      <th>Intitule</th>
      <th>Experience</th>
      <th>Quantite</th>
      <th>Date demande</th>
      <th>Etat</th>
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
      <td><a href="<%=request.getContextPath()%>/AffInfo.DemandeController?idDemande=<%=d.getId()%>"><%=d.getIntitule()%></a></td>
      <td><%=d.getExperiences().size()%></td>
      <td><%=d.getQuantite()%></td>
      <td><%=d.getDate_demande()%></td>
      <td><%=d.getValidation()%></td>
    </tr>
    <% } %>
    </tbody>
  </table>
</section>

</body>
</html>
