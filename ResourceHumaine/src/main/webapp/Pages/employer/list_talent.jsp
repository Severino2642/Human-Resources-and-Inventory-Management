<%@ page import="annexe.Talent" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 06/11/2024
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Talent [] talents = (Talent[]) request.getAttribute("talents");
%>
<html>
<head>
    <title>Liste des Employés</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/Pages/home.jsp">Retour</a>
<section class="body">
    <form action="<%=request.getContextPath()%>/Insert.TalentController" method="post">
        <h2>Insertion de talent</h2>
        <div>
            <label>Nom :</label>
            <input type="text" name="nom" required>
        </div>
        <div>
            <label>Date d'ajout :</label>
            <input type="date" name="date_ajout" required>
        </div>
        <button type="submit">Valider</button>
    </form>
    <h2>Liste des talents :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Nom</th>
        </tr>
        </thead>
        <tbody>
        <% for (Talent t : talents) { %>
        <tr>
            <td><%= t.getId() %></td>
            <td><a href="<%=request.getContextPath()%>/AffInfo.TalentController?idTalent=<%=t.getId()%>"><%= t.getNom() %></a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
