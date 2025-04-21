<%@ page import="produit.Produit" %>
<%@ page import="demande.Demande" %>
<%@ page import="departement.Departement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Produit[] produits = (Produit[]) request.getAttribute("produits");
  Demande[] demandes = (Demande[]) request.getAttribute("demandes");
  Departement departement = (Departement) request.getSession().getAttribute("departement");
%>
<html>
<head>
  <title>Liste des demandes</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/listeDemande.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/questionnaire.css">
</head>
<body>

<section class="body">
  <a class="back-link" href="<%=request.getContextPath()%>/AffInfo.DepartementController">Retour</a>

  <form id="questionnaire" action="<%=request.getContextPath()%>/Insert.DemandeController" method="post" onsubmit="return validateFinalForm()">
    <div class="question active" id="q1">
      <div>
        <label>Produit :</label>
        <select name="idProduit" required>
          <option value="">Sélectionner un produit</option>
          <% for (Produit p : produits) { %>
          <option value="<%=p.getId()%>"><%=p.getNom()%></option>
          <% } %>
        </select>
      </div>
      <div>
        <label>Quantité :</label>
        <input type="number" name="quantite" min="1" required>
      </div>
      <button type="button" onclick="nextQuestion(1)">Suivant</button>
    </div>
    <div class="question" id="q2">
        <div>
          <label>Intitulé :</label>
          <input type="text" name="intitule" required>
        </div>
        <button type="button" onclick="nextQuestion(2)">Suivant</button>
    </div>
    <div class="question" id="q3">
      <h2>Insertion de demande</h2>
      <input type="hidden" value="<%=departement.getId()%>" name="idDepartement">
      <div>
        <label>Date demande :</label>
        <input type="date" name="date_demande" required>
      </div>
      <div>
        <label>Date besoin :</label>
        <input type="date" name="date_besoin" required>
      </div>
      <button type="button" onclick="nextQuestion(3)">Suivant</button>
    </div>
    <div class="question" id="q4">
      <div>
        <label>Motif de la demande :</label>
        <select id="motif" required>
          <option value="">--Choisissez une option--</option>
          <option value="Remplacement">Remplacement</option>
          <option value="Nouveau projet">Nouveau projet</option>
          <option value="Augmentation d'effectif">Augmentation d'effectif</option>
          <option value="Amélioration des performances">Amélioration des performances</option>
        </select>
      </div>
      <button type="submit">valider</button>
    </div>


  </form>

  <h2>Liste des demandes :</h2>
  <table>
    <thead>
    <tr>
      <th>#</th>
      <th>Produit</th>
      <th>Intitulé</th>
      <th>Date demande</th>
      <th>Date besoin</th>
      <th>Validation</th>
    </tr>
    </thead>
    <tbody>
    <% for (Demande d : demandes) { %>
    <tr>
      <td><%=d.getId()%></td>
      <td><%=d.getProduit().getNom()%></td>
      <td><%=d.getIntitule()%></td>
      <td><%=d.getDate_demande()%></td>
      <td><%=d.getDate_besoin()%></td>
      <td><%=d.getTermeValidation()%></td>
    </tr>
    <% } %>
    </tbody>
  </table>
</section>

<script src="<%=request.getContextPath()%>/assets/js/script.js"></script>

</body>
</html>
