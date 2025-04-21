<%@ page import="annexe.Question" %>
<%@ page import="annexe.Reponse" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 08/11/2024
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Question question = (Question) request.getAttribute("question");
%>
<html>
<head>
    <title>Questionnaire</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">

</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffInfo.TalentController">Retour</a>
<section class="body">
    <p>Question : <%=question.getIntitule()%></p>
    <form action="<%=request.getContextPath()%>/AddReponse.TalentController" method="post">
        <h2>Insertion de reponse</h2>
        <input type="hidden" name="idQuestion" value="<%=question.getId()%>">
        <div>
            <label>Reponse :</label>
            <input type="text" name="intitule" required>
        </div>
        <div>
            <label>Point :</label>
            <input type="number" name="point" required>
        </div>
        <button type="submit">Valider</button>
    </form>
    <h2>Liste des reponses :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Reponse</th>
            <th>Point</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Reponse r : question.getReponses()) { %>
        <tr>
            <td><%= r.getId() %></td>
            <td><%= r.getIntitule() %></td>
            <td><%= r.getPoint() %></td>
            <td><a href="<%=request.getContextPath()%>/DeleteReponse.TalentController?idQuestion=<%=question.getId()%>&&idReponse=<%=r.getId()%>">supprimer</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>

</body>
</html>
