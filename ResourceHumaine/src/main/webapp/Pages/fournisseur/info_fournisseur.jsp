<%@ page import="fournisseur.Fournisseur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Fournisseur fournisseur = (Fournisseur) request.getAttribute("fournisseur");
%>
<html>
<head>
  <title>Information du Fournisseur</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/home.css">
</head>
<body>
<header class="header">
  <h1><%= fournisseur.getNom() %></h1>
  <nav class="navbar">
    <a href="<%=request.getContextPath()%>/AffList.FournisseurController">Retour</a>
  </nav>
</header>
<section class="menu">
  <nav>
    <p><a href="<%=request.getContextPath()%>/AffList.PersonneController?idFournisseur=<%=fournisseur.getId()%>">Personnes</a></p>
    <p><a href="<%=request.getContextPath()%>/AffListAnnonce.FournisseurController?idFournisseur=<%=fournisseur.getId()%>">Annonce</a></p>
  </nav>
</section>
</body>
</html>
