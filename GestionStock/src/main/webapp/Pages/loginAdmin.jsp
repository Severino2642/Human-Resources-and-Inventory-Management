<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/admin.css">
</head>
<body>
<% if (request.getAttribute("message") != null) { %>
<script type="text/javascript">
    alert("<%=request.getAttribute("message")%>");
</script>
<% } %>
<div class="login-container">
    <form action="<%=request.getContextPath()%>/loginAdmin.LoginController" method="post">
        <h2>Connexion</h2>
        <div class="form-group">
            <label for="email">Adresse Email</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="password">Mot de passe</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">Se connecter</button>
        <a href="<%=request.getContextPath()%>/index.jsp">Retour</a>
    </form>
</div>
</body>
</html>
