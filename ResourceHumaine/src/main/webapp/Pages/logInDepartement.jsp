<%@ page import="departement.Departement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Departement[] departements = new Departement().findAll(null);
%>
<html>
<head>
    <title>Connexion Département</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/login-dept.css">
</head>
<body>
<% if (request.getAttribute("message") != null) { %>
<script type="text/javascript">
    alert("<%=request.getAttribute("message")%>");
</script>
<% } %>
<div class="login-container">
    <form action="<%=request.getContextPath()%>/loginDepartement.LoginController" method="post">
        <h2>Connexion Département</h2>
        <div class="form-group">
            <label for="idDepartement">Département :</label>
            <select name="idDepartement" id="idDepartement" required>
                <% for (Departement d : departements) { %>
                <option value="<%=d.getId()%>"><%=d.getNom()%></option>
                <% } %>
            </select>
        </div>
        <div class="form-group">
            <label for="email">Adresse Email :</label>
            <input type="email" id="email" name="email" required>
        </div>
        <button type="submit">Se connecter</button>
        <a href="<%=request.getContextPath()%>/index.jsp">Retour</a>
    </form>
</div>
</body>
</html>
