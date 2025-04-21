<%@ page import="caisse.Caisse" %><%--
  Created by IntelliJ IDEA.
  User: diva
  Date: 21/10/2024
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Caisse [] caissesEntrer = (Caisse[]) request.getAttribute("caissesEntrer");
  Caisse [] caissesSortie = (Caisse[]) request.getAttribute("caissesSortie");
  double total_entrer = 0;
  for (Caisse caisse : caissesEntrer) {
    total_entrer+= caisse.getMontant();
  }
  double total_sortie = 0;
  for (Caisse caisse : caissesSortie) {
    total_sortie+= caisse.getMontant();
  }

%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/infoProd.css">

</head>
<body>
<section class="body">
  <a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>

  <div class="product-info">
    <h2>Etat de caisse</h2>
    <p><strong>entrer de caisse :</strong> <%=total_entrer %> Ar</p>
    <p><strong>sortie de caisse :</strong> <%=total_sortie %> Ar</p>
    <p><strong>reste de caisse :</strong> <%=total_entrer-total_sortie %> Ar</p>
  </div>

  <form action="<%= request.getContextPath() %>/InsertCaisse.FinanceController" method="post">
    <h2>Encaissement</h2>
    <div>
      <label>Montant :</label>
      <input type="number" name="montant" min="0" required>
    </div>
    <div>
      <label>Etat :</label>
      <select name="isEntrer" required>
        <option value="true">Entrer</option>
        <option value="false">Sortie</option>
      </select>
    </div>
    <div>
      <label>Date</label>
      <input type="date" name="date_modif" required>
    </div>
    <button type="submit">Valider</button>
  </form>

  <div class="stock-status">
    <div class="stock-table">
      <h2>État de Caisse Sortie</h2>
      <table>
        <thead>
        <tr>
          <th>#</th>
          <th>Montant</th>
          <th>Date Sortie</th>
        </tr>
        </thead>
        <tbody>
        <% for (Caisse c : caissesSortie) { %>
        <tr>
          <td><%=c.getId()%></td>
          <td><%=c.getMontant()%></td>
          <td><%=c.getDate_modif()%></td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>

    <div class="stock-table">
      <h2>État de Caisse Entré</h2>
      <table>
        <thead>
        <tr>
          <th>#</th>
          <th>Montant</th>
          <th>Date Sortie</th>
        </tr>
        </thead>
        <tbody>
        <% for (Caisse c : caissesEntrer) { %>
        <tr>
          <td><%=c.getId()%></td>
          <td><%=c.getMontant()%></td>
          <td><%=c.getDate_modif()%></td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>
  </div>
</section>

</body>
</html>
