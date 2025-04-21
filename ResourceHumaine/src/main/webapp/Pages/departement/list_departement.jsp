<%@ page import="departement.Departement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="connexion.Base" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Departement[] departements = (Departement[]) request.getAttribute("departements");
%>
<html>
<head>
    <title>Liste des départements</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeDept.css">
</head>
<body>
<a class="back-link" href="<%=request.getContextPath()%>/Pages/home.jsp">Retour</a>
<section class="body">
    <form action="<%=request.getContextPath()%>/Insert.DepartementController" method="post">
        <h2>Insertion de département</h2>
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
    <h2>Liste des départements :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Employés</th>
            <th>Date d'ajout</th>
        </tr>
        </thead>
        <tbody>
        <%
            Connection con = Base.PsqlConnect();
            for (Departement d : departements) {
                d.setEmployers(con);
        %>
        <tr>
            <td><%=d.getId()%></td>
            <td><a href="<%=request.getContextPath()%>/loginDepartement.LoginController?idDepartement=<%=d.getId()%>"><%=d.getNom()%></a></td>
            <td><%=d.getEmployers().size()%></td>
            <td><%=d.getDate_ajout()%></td>
        </tr>
        <% } con.close(); %>
        </tbody>
    </table>
</section>
</body>
</html>
