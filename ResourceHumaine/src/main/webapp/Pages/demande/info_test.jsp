<%@ page import="annexe.Question" %>
<%@ page import="personne.Personne" %>
<%@ page import="demande.Demande" %>
<%@ page import="demande.Test" %>
<%@ page import="annexe.Reponse" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 09/11/2024
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Test teste = (Test) request.getAttribute("teste");
    Question [] questions = (Question[]) request.getAttribute("questions");
%>
<html>
<head>
    <title>Info Test</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/questionnaire.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeDept.css">

</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/AffInfo.PersonneController?idPersonne=<%=teste.getIdPersonne()%>">Retour</a>
<section class="body">
    <form id="questionnaire" action="<%=request.getContextPath()%>/RepondreTeste.DemandeController" method="post" onsubmit="return validateFinalForm()">
        <h2>Teste</h2>
        <input type="hidden" name="idTeste" value="<%=teste.getId()%>">
        <%  int indice = 0;
            for (Question q : questions) {
            String s = "";
            if (indice == 0){
                s = "active";
            }
        %>
            <div class="question <%=s%>" id="q<%=indice+1%>">
                <h3>Question : <%=q.getIntitule()%></h3>
                <% for (Reponse r : q.getReponses()) { %>
                <div>
                    <label>
                        <input type="radio" name="point<%=indice%>" value="<%=r.getPoint()%>">
                        <%=r.getIntitule()%>
                    </label>
                </div>
                <% } %>
                <% if (indice< questions.length-1) {%>
                    <button type="button" onclick="nextQuestion(<%=indice+1%>)">Suivant</button>
                <% } %>
                <% if (indice== questions.length-1) {%>
                    <button type="submit">valider</button>
                <% } %>
            </div>
        <% indice++;} %>
        <input type="hidden" name="nbQuestion" value="<%=indice%>">
    </form>
</section>

<script src="<%=request.getContextPath()%>/assets/js/script.js"></script>

</body>
</html>