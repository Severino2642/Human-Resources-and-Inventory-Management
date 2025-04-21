package servlet;

import connexion.Base;
import demande.Demande;
import demande.DemandeValidation;
import departement.Departement;
import departement.DepartementValidation;
import fournisseur.ProduitFournisseur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import outils.MemeDemande;
import produit.Produit;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "DemandeController", value = "*.DemandeController")
public class DemandeController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idProduit = Integer.parseInt(request.getParameter("idProduit"));
        String intitule = request.getParameter("intitule");
        double quantite = Double.parseDouble(request.getParameter("quantite"));
        Date date_demande = Date.valueOf(request.getParameter("date_demande"));
        Date date_besoin = Date.valueOf(request.getParameter("date_besoin"));

        Demande demande = new Demande(idDepartement,idProduit,intitule,quantite,date_demande,date_besoin);
        demande.insert(null);

        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.DemandeController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList() throws ServletException, IOException, SQLException {
        Departement departement = (Departement) request.getSession().getAttribute("departement");
        Connection con = Base.PsqlConnect();
        Demande [] demandes = new Demande().findByIdDepartement(departement.getId(),con);
        Produit [] produits = new Produit().findAll(con);
        request.setAttribute("produits", produits);
        request.setAttribute("demandes", demandes);
        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/list_demande.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffListDemandeEncours")
    public void AffListDemandeEncours() throws ServletException, IOException, SQLException {
        Departement departement = (Departement) request.getSession().getAttribute("departement");

        String path = "/Pages/demande/demande_encours_achat.jsp";
        if (departement.getNom().compareToIgnoreCase("achat")==0){
            MemeDemande [] memeDemandes = new MemeDemande().findAllMemeDemande(null);
            request.getSession().setAttribute("memeDemandes", memeDemandes);
        }
        else {
            MemeDemande [] memeDemandes = new MemeDemande().findAllMemeDemandeByDepartement(departement.getId(),null);
            request.getSession().setAttribute("memeDemandes", memeDemandes);
            if (departement.getNom().compareToIgnoreCase("stock")==0){
                path = "/Pages/demande/demande_encours_stock.jsp";
            }
            if (departement.getNom().compareToIgnoreCase("finance")==0){
                path = "/Pages/demande/demande_encours_finance.jsp";
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "Rejeter")
    public void Rejeter () throws Exception {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));

        DemandeValidation demandeValidation = new DemandeValidation(idDemande,0,false,Base.getDate());
        demandeValidation.insert(null);

        RequestDispatcher rd = request.getRequestDispatcher("AffListDemandeEncours.DemandeController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffVerification")
    public void AffVerification () throws Exception {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));

        Connection con = Base.PsqlConnect();
        Demande demande = new Demande().findById(idDemande,con);
        demande.getProduit().setStock();
        request.getSession().setAttribute("demande",demande);
        request.setAttribute("ProduitFournisseur",new ProduitFournisseur().findAllByIdProduit(demande.getIdProduit(),con));

        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/demande_verification.jsp");
        rd.forward(request, response);
    }


}
