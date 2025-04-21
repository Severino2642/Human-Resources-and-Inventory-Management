<%@ page import="personne.Personne" %>
<%@ page import="fournisseur.Fournisseur" %>
<%@ page import="annexe.Talent" %>
<%@ page import="personne.Experience" %>
<%@ page import="demande.Test" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 06/11/2024
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Fournisseur fournisseur = (Fournisseur) request.getSession().getAttribute("fournisseur");
    Personne personne = (Personne) request.getAttribute("personne");
    Talent [] talents = (Talent[]) request.getAttribute("talents");
    Test[] testes = (Test[]) request.getAttribute("tests");
%>
<html>
<head>
    <title>Info Personne</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/questionnaire.css">

</head>
<body>
<a href="<%=request.getContextPath()%>/AffList.PersonneController?idFournisseur=<%=fournisseur.getId()%>">Retour</a>
<section class="body">
    <form action="<%=request.getContextPath()%>/AddExperience.PersonneController" method="post">
        <h2>Ajout d'experience</h2>
        <input type="hidden" name="idPersonne" value="<%=personne.getId()%>">
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
        <div>
            <label>Date d'ajout :</label>
            <input type="date" name="date_ajout" required>
        </div>
        <button type="submit">Valider</button>
    </form>
    <div class="navigation">
        <nav>
            <p><a id="bt1" class="bt-nav" onclick="nextSlide(1)">Experiences</a></p>
            <p><a id="bt2" class="bt-nav" onclick="nextSlide(2)">Convocations</a></p>
        </nav>
        <div class="components">
            <div id="s1" class="sous-components">
                <h2>Liste d'experience :</h2>
                <table>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Talent</th>
                        <th>Duree</th>
                        <th>Date_ajout</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Experience e : personne.getExperiences()) { %>
                    <tr>
                        <td><%= e.getId() %></td>
                        <td><%= e.getTalent().getNom() %></td>
                        <td><%= e.getDuree() %> ans</td>
                        <td><%= e.getDate_ajout() %></td>
                        <td><a href="<%=request.getContextPath()%>/DeleteExperience.PersonneController?idPersonne=<%=personne.getId()%>&&idExperience=<%=e.getId()%>">supprimer</a></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <div id="s2" class="sous-components">
                <h2>Liste des convocations:</h2>
                <table>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Demande</th>
                        <th>Date de convocation</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for (Test t : testes) {
                    %>
                    <tr>
                        <td><%= t.getId() %></td>
                        <td><%= t.getDemande().getIntitule()%></td>
                        <td><%= t.getDate_ajout()%></td>
                        <% if (t.getNote()<=-1) {%>
                        <td><a href="<%=request.getContextPath()%>/AffTeste.DemandeController?idTeste=<%=t.getId()%>">passer le teste</a></td>
                        <% } %>
                        <% if (t.getNote()>-1) {%>
                        <td>verification encours</td>
                        <% } %>
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

