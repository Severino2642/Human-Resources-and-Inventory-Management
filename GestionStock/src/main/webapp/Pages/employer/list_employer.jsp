<%@ page import="user.Employer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Employer[] employers = (Employer[]) request.getAttribute("employers");
%>
<html>
<head>
    <title>Liste des Employés</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeEmp.css">
</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/Pages/home.jsp">Retour</a>
<section class="body">
    <form action="<%=request.getContextPath()%>/Insert.EmployerController" method="post">
        <h2>Insertion d'un Employé</h2>
        <div>
            <label>Nom :</label>
            <input type="text" name="nom" required>
        </div>
        <div>
            <label>Adresse Email :</label>
            <input type="email" name="email" required>
        </div>
        <div>
            <label>Date d'embauche :</label>
            <input type="date" name="date_embauche" required>
        </div>
        <button type="submit">Valider</button>
    </form>
    <h2>Liste des Employés :</h2>
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
        <% for (Employer e : employers) { %>
        <tr>
            <td><%= e.getId() %></td>
            <td><%= e.getNom() %></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getDate_embauche() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
