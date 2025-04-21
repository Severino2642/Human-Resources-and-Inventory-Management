<%@ page import="user.Admin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Admin admin = (Admin) request.getSession().getAttribute("admin");
  String user_name = "inconnue";
  if (admin != null) {
    user_name = admin.getEmail();
  }
%>
<html>
<head>
  <title>Accueil</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/home.css">
</head>
<body>
<header class="header">
  <h1>Bienvenue, <%= user_name %>!</h1>
  <nav class="navbar">
    <a href="<%=request.getContextPath()%>/index.jsp">Déconnexion</a>
  </nav>
</header>

<section class="menu">
  <nav>
    <p><a href="<%=request.getContextPath()%>/AffList.DepartementController">Département</a></p>
    <p><a href="<%=request.getContextPath()%>/AffList.FournisseurController">Fournisseur</a></p>
    <p><a href="<%=request.getContextPath()%>/AffList.TalentController">Talent</a></p>
  </nav>
</section>
</body>
</html>
