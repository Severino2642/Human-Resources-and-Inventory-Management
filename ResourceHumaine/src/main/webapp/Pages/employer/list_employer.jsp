<%@ page import="personne.Personne" %>
<%@ page import="annexe.Sexe" %>
<%@ page import="fournisseur.Fournisseur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Fournisseur fournisseur = (Fournisseur) request.getSession().getAttribute("fournisseur");
    Personne[] personnes = (Personne[]) request.getAttribute("personnes");
    Sexe [] sexes = (Sexe[]) request.getAttribute("sexes");
%>
<html>
<head>
    <title>Liste des Employés</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffInfo.FournisseurController">Retour</a>
<section class="body">
    <form action="<%=request.getContextPath()%>/Insert.PersonneController" method="post">
        <h2>Insertion d'une personne</h2>
        <input type="hidden" name="idFournisseur" value="<%=fournisseur.getId()%>">
        <div>
            <label>Nom :</label>
            <input type="text" name="nom" required>
        </div>
        <div>
            <label>Sexe :</label>
            <select name="idSexe">
                <% for (Sexe s:sexes) { %>
                    <option value="<%=s.getId()%>"><%=s.getIntitule()%></option>
                <% } %>
            </select>
        </div>
        <div>
            <label>Adresse Email :</label>
            <input type="email" name="email" required>
        </div>
        <div>
            <label>Date d'ajout :</label>
            <input type="date" name="date_ajout" required>
        </div>
        <button type="submit">Valider</button>
    </form>
    <h2>Liste des personnes :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Adresse Email</th>
            <th>Date d'ajout</th>
        </tr>
        </thead>
        <tbody>
        <% for (Personne e : personnes) { %>
        <tr>
            <td><%= e.getId() %></td>
            <td><a href="<%=request.getContextPath()%>/AffInfo.PersonneController?idPersonne=<%=e.getId()%>"><%= e.getNom() %></a></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getDate_ajout() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
