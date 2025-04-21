<%@ page import="departement.Departement" %>
<%@ page import="annexe.Status" %>
<%@ page import="personne.Personne" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Departement departement = (Departement) request.getAttribute("departement");
    String path = "index.jsp";
    if (request.getSession().getAttribute("admin") != null) {
        path = "AffList.DepartementController";
    }
%>
<html>
<head>
    <title>Information de département</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/infoDept.css">
</head>
<body>
<section class="menu">
    <nav>
        <p><a href="<%=request.getContextPath()%>/AffList.DemandeController?idDepartement=<%=departement.getId()%>">Demande</a></p>
        <% if (departement.getNom().equalsIgnoreCase("Resource Humaine")) { %>
            <p><a href="<%=request.getContextPath()%>/AffListDemandeEnCours.DemandeController">Demande en cours</a></p>
        <% } %>
    </nav>
</section>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/<%=path%>">Retour</a>
    <h2>Département <%=departement.getNom()%></h2>
    <h2>Liste des employés :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Adresse Email</th>
            <th>Date d'Embauche</th>
        </tr>
        </thead>
        <tbody>
        <% for (Personne e : departement.getEmployers()) { %>
        <tr>
            <td><%=e.getId()%></td>
            <td><%=e.getNom()%></td>
            <td><%=e.getEmail()%></td>
            <td><%=e.getDepartementEmployer().getDate_ajout()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
