package servlet;

import caisse.Caisse;
import connexion.Base;
import demande.Demande;
import departement.DepartementValidation;
import facture.BonDeCommande;
import facture.ProFormat;
import fournisseur.ProduitFournisseur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

@WebServlet(name = "AchatController", value = "*.AchatController")
public class AchatController extends MereController {
    @CtrlAnnotation(name = "SendRequest")
    public void SendRequest () throws Exception {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        double valeur = Double.parseDouble(request.getParameter("valeur"));

        DepartementValidation departementValidation = new DepartementValidation(idDemande,idDepartement,valeur,false, Base.getDate());
        departementValidation.insert(null);

        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffListDemandeEncours.DemandeController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffFournisseur")
    public void AffFournisseur () throws Exception {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        Connection con = Base.PsqlConnect();

        Demande demande = new Demande().findById(idDemande,con);
        DepartementValidation dpvAchat = new DepartementValidation().findByIdDemandeAndIdDepartement(idDemande,idDepartement,con);
        ProduitFournisseur [] produitFournisseurs = new ProduitFournisseur().findAllByIdProduit(demande.getIdProduit(),con);

        con.close();
        request.setAttribute("demande", demande);
        request.setAttribute("produitFournisseurs", produitFournisseurs);
        request.setAttribute("dpvAchat", dpvAchat);

        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/list_proformat.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "SendRequestVersFinance")
    public void SendRequestVersFinance () throws Exception {
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));
        int idDepartementAchat = Integer.parseInt(request.getParameter("idDepartementAchat"));
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        double valeur = Double.parseDouble(request.getParameter("valeur"));
        Date date_ajout = Base.getDate();

        Connection con = Base.PsqlConnect();
        DepartementValidation dpvAchat = new DepartementValidation().findByIdDemandeAndIdDepartement(idDemande,idDepartementAchat,con);
        dpvAchat.setValid(true);
        dpvAchat.updateByIdDemandeAndIdDepartement(con);

        DepartementValidation departementValidation = new DepartementValidation(idDemande,idDepartement,valeur,false, date_ajout);
        departementValidation.insert(con);

        ProFormat proFormat = new ProFormat(idDemande,idFournisseur,dpvAchat.getValeur(),valeur,date_ajout);
        proFormat.insert(con);

        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("AffListDemandeEncours.DemandeController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "SendBonDeCommande")
    public void SendBonDeCommande () throws Exception {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));

        Connection con = Base.PsqlConnect();
        Demande demande = new Demande().findById(idDemande,con);
        ProFormat proFormat = new ProFormat().findByIdDemande(idDemande,con);
        BonDeCommande bonDeCommande = new BonDeCommande(demande.getIdProduit(),proFormat.getIdFournisseur(),proFormat.getQuantite(),proFormat.getMontant(),false,demande.getDate_demande(),demande.getDate_besoin());
        bonDeCommande.insert(con);
        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("AffListDemandeEncours.DemandeController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "ValideBonDeCommande")
    public void ValideBonDeCommande () throws Exception {
        int idBonDeCommande = Integer.parseInt(request.getParameter("idBonDeCommande"));
        Date date_besoin = Date.valueOf(request.getParameter("date_besoin"));
        Date date = Base.getDate();
        Connection con = Base.PsqlConnect();
        BonDeCommande b = new BonDeCommande().findById(idBonDeCommande,con);
        b.setValide(true);
        b.setDate_commande(date);
        b.setDate_besoin(date_besoin);
        b.update(con);

        Caisse caisse = new Caisse(false,b.getMontant(),date);
        caisse.insert(con);
        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("AffListCommandeEncours.AchatController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffListCommandeEncours")
    public void AffListCommandeEncours () throws Exception {
        request.setAttribute("bonDeCommandes",new BonDeCommande().findAllByIsValide(false,null));

        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/list_bonDeCommande.jsp");
        rd.forward(request, response);
    }
}
