<%@ page import="user.Employer" %>
<%@ page import="departement.Departement" %>
<%@ page import="annexe.Status" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Departement departement = (Departement) request.getAttribute("departement");
    Employer[] nonRecruter = (Employer[]) request.getAttribute("nonRecruter");
    Status[] liststatus = (Status[]) request.getAttribute("listStatus");
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
        <p><a href="<%=request.getContextPath()%>/AffList.DemandeController">Demande</a></p>
        <% if (departement.getNom().equalsIgnoreCase("achat") || departement.getNom().equalsIgnoreCase("stock") || departement.getNom().equalsIgnoreCase("finance")) { %>
        <p><a href="<%=request.getContextPath()%>/AffListDemandeEncours.DemandeController">Demandes en attente</a></p>
        <% } %>
        <% if (departement.getNom().equalsIgnoreCase("direction")) { %>
        <p><a href="<%=request.getContextPath()%>/AffListCommandeEncours.AchatController">Bon de commande</a></p>
        <% } %>
        <% if (departement.getNom().equalsIgnoreCase("finance")) { %>
        <p><a href="<%=request.getContextPath()%>/AffEtatCaisse.FinanceController">Etat de caisse</a></p>
        <% } %>
        <% if (departement.getNom().equalsIgnoreCase("stock")) { %>
        <p><a href="<%=request.getContextPath()%>/AffListBonDeReception.StockController">Bon de réception</a></p>
        <p><a href="<%=request.getContextPath()%>/AffList.ProduitController">Produits</a></p>
        <% } %>
    </nav>
</section>
<section class="body">
    <a class="back-link" href="<%=request.getContextPath()%>/<%=path%>">Retour</a>
    <h2>Département <%=departement.getNom()%></h2>
    <form action="<%=request.getContextPath()%>/Recruter.DepartementController" method="post">
        <h2>Recrutement</h2>
        <input type="hidden" value="<%=departement.getId()%>" name="idDepartement">
        <div>
            <label>Employé :</label>
            <select name="idEmployer">
                <% for (Employer e : nonRecruter) { %>
                <option value="<%=e.getId()%>"><%=e.getNom()%></option>
                <% } %>
            </select>
        </div>
        <div>
            <label>Status :</label>
            <select name="idStatus">
                <% for (Status s : liststatus) { %>
                <option value="<%=s.getId()%>"><%=s.getIntitule()%></option>
                <% } %>
            </select>
        </div>
        <div>
            <label>Date de recrutement :</label>
            <input type="date" name="date_ajout" required>
        </div>
        <button type="submit">Valider</button>
    </form>
    <h2>Liste des employés :</h2>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Nom</th>
            <th>Adresse Email</th>
            <th>Date d'Embauche</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% for (Employer e : departement.getEmployers()) { %>
        <tr>
            <td><%=e.getId()%></td>
            <td><%=e.getNom()%></td>
            <td><%=e.getEmail()%></td>
            <td><%=e.getDate_embauche()%></td>
            <td><a href="<%=request.getContextPath()%>/Renvoyer.DepartementController?idEmployer=<%=e.getId()%>&&idDepartement=<%=departement.getId()%>">Renvoyer</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</section>
</body>
</html>
