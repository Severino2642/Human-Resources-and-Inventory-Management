package servlet;

import connexion.Base;
import demande.Demande;
import demande.DemandeValidation;
import departement.Departement;
import departement.DepartementValidation;
import facture.BonDeCommande;
import facture.BonDeLivraison;
import facture.BonDeReception;
import fournisseur.Fournisseur;
import fournisseur.Incident;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import produit.Produit;
import produit.StockProduit;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "StockController", value = "*.StockController")
public class StockController extends MereController {
    @CtrlAnnotation(name = "Valider")
    public void Valider () throws Exception {
        int idProduit = Integer.parseInt(request.getParameter("idProduit"));
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        double valeur = Double.parseDouble(request.getParameter("valeur"));
        Date date = Base.getDate();

        Connection con = Base.PsqlConnect();
        DemandeValidation validation = new DemandeValidation(idDemande,valeur,true,date);
        validation.insert(con);
        DepartementValidation departementValidation = new DepartementValidation();
        departementValidation.setIdDemande(idDemande);
        departementValidation.deleteByidDemande(con);
        Produit produit = new Produit().findById(idProduit,con);
        produit.sortieStock(date,valeur,con);
        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("AffListDemandeEncours.DemandeController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "SendRequest")
    public void SendRequest () throws Exception {
        int idDepartementStock = Integer.parseInt(request.getParameter("idDepartementStock"));
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        double valeur = Double.parseDouble(request.getParameter("valeur"));

        Connection con = Base.PsqlConnect();
        DepartementValidation dvpStock = new DepartementValidation().findByIdDemandeAndIdDepartement(idDemande,idDepartementStock,con);
        dvpStock.setValid(true);
        dvpStock.updateByIdDemandeAndIdDepartement(con);
        DepartementValidation departementValidation = new DepartementValidation(idDemande,idDepartement,valeur,false, Base.getDate());
        departementValidation.insert(con);
        con.close();

        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffListDemandeEncours.DemandeController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffListBonDeReception")
    public void AffListBonDeReception () throws ServletException, IOException, SQLException {
        Connection con = Base.PsqlConnect();
        request.setAttribute("bonDeLivraisons", new BonDeLivraison().findAllNoRecu(con));
        request.setAttribute("bonDeReceptions",new BonDeReception().findAll(con));
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/list_bonDeReception.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddBonDeReception")
    public void AddBonDeReception () throws ServletException, IOException, SQLException {
        Departement departement = (Departement) request.getSession().getAttribute("departement");
        int idBonDeLivraison = Integer.parseInt(request.getParameter("idBonDeLivraison"));
        double quantite = Double.parseDouble(request.getParameter("quantite"));
        double montant = Double.parseDouble(request.getParameter("montant"));
        Date date_recu = Date.valueOf(request.getParameter("date_recu"));

        Connection con = Base.PsqlConnect();
        BonDeLivraison bonDeLivraison = new BonDeLivraison().findById(idBonDeLivraison,con);
        BonDeCommande bonDeCommande = new BonDeCommande().findById(bonDeLivraison.getIdBonDeCommande(),con);
        if (bonDeCommande.getDate_besoin().before(bonDeLivraison.getDate_livraison())) {
            Incident incident = new Incident(bonDeCommande.getIdFournisseur(),"livraison en retard",date_recu);
            incident.insert(con);
        }

        BonDeReception bonDeReception = new BonDeReception(idBonDeLivraison,quantite,montant,date_recu);
        bonDeReception.insert(con);
        double prix_unitaire = montant / quantite;
        StockProduit stockProduit = new StockProduit(bonDeCommande.getIdProduit(),true,quantite,prix_unitaire,date_recu);
        stockProduit.insert(con);

        Demande [] demandes = new Demande().getDemandeNotValidationByIdProduit(bonDeCommande.getIdProduit(),con);
        for (Demande demande: demandes) {
            DepartementValidation dvpStock = new DepartementValidation().findByIdDemandeAndIdDepartement(demande.getId(),departement.getId(),con);
            if(dvpStock != null){
                dvpStock.setValid(false);
                dvpStock.updateByIdDemandeAndIdDepartement(con);
            }
        }

        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("AffListBonDeReception.StockController");
        rd.forward(request, response);
    }

}
