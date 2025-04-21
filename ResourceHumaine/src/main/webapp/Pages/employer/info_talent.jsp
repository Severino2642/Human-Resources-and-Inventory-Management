<%@ page import="annexe.Talent" %>
<%@ page import="annexe.Question" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 08/11/2024
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Talent talent = (Talent) request.getAttribute("talent");
%>
<html>
<head>
    <title>Info Talent</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffList.TalentController">Retour</a>
<section class="body">
    <p>Talent : <%=talent.getNom()%></p>
    <form action="<%=request.getContextPath()%>/AddQuestion.TalentController" method="post">
        <h2>Insertion de questionnaire</h2>
        <input type="hidden" name="idTalent" value="<%=talent.getId()%>">
        <div>
            <label>Question :</label>
            <input type="text" name="intitule" required>
        </div>
        <button type="submit">Valider</button>
    </form>
    <h2>Liste des questionnaires :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Question</th>
            <th>Date d'ajout</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Question q : talent.getQuestions()) { %>
        <tr>
            <td><%= q.getId() %></td>
            <td><a href="<%=request.getContextPath()%>/AffInfoQuestion.TalentController?idQuestion=<%=q.getId()%>"><%= q.getIntitule() %></a></td>
            <td><%= q.getDate_ajout() %></td>
            <td><a href="<%=request.getContextPath()%>/DeleteQuestion.TalentController?idQuestion=<%=q.getId()%>">supprimer</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
