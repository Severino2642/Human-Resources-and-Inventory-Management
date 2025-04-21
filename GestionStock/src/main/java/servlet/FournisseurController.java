package servlet;

import connexion.Base;
import demande.Demande;
import facture.BonDeCommande;
import facture.BonDeLivraison;
import fournisseur.Fournisseur;
import fournisseur.Incident;
import fournisseur.ProduitFournisseur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import produit.Produit;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "FournisseurController", value = "*.FournisseurController")
public class FournisseurController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException {
        String nom = request.getParameter("nom");
        Date date_ajout = Date.valueOf(request.getParameter("date_ajout"));

        Fournisseur f = new Fournisseur(nom, date_ajout);
        f.insert(null);

        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.FournisseurController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "Delete")
    public void Delete () throws ServletException, IOException {
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(idFournisseur);
        fournisseur.delete(null);
        request.setAttribute("message", "suppression effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.FournisseurController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddProduit")
    public void AddProduit () throws ServletException, IOException, SQLException {
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));
        int idProduit = Integer.parseInt(request.getParameter("idProduit"));
        double prix_unitaire = Double.parseDouble(request.getParameter("prix_unitaire"));
        Date date_modif = Date.valueOf(request.getParameter("date_modif"));

        ProduitFournisseur produitFournisseur = new ProduitFournisseur(idFournisseur, idProduit, prix_unitaire, date_modif);
        Connection con = Base.PsqlConnect();
        produitFournisseur.deleteByIdProduitAndIdFournisseur(con);
        produitFournisseur.insert(con);
        Fournisseur fournisseur = new Fournisseur().findById(idFournisseur,con);
        fournisseur.setProduitFournisseurs(con);
        request.getSession().setAttribute("fournisseur", fournisseur);
        con.close();

        request.setAttribute("message", "produit ajouter");
        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.FournisseurController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "DeleteProduit")
    public void DeleteProduit () throws ServletException, IOException, SQLException {
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));
        int idProduit = Integer.parseInt(request.getParameter("idProduit"));

        ProduitFournisseur produitFournisseur = new ProduitFournisseur();
        produitFournisseur.setIdFournisseur(idFournisseur);
        produitFournisseur.setIdProduit(idProduit);
        Connection con = Base.PsqlConnect();
        produitFournisseur.deleteByIdProduitAndIdFournisseur(null);
        Fournisseur fournisseur = new Fournisseur().findById(idFournisseur,con);
        fournisseur.setProduitFournisseurs(con);
        con.close();
        request.getSession().setAttribute("fournisseur", fournisseur);
        request.setAttribute("message", "produit supprimer");
        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.FournisseurController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException {
        request.setAttribute("fournisseurs", new Fournisseur().findAll(null));
        request.getSession().setAttribute("fournisseur", null);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/fournisseur/list_fournisseur.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfo")
    public void AffInfo () throws ServletException, IOException, SQLException {
        Fournisseur fournisseur = (Fournisseur) request.getSession().getAttribute("fournisseur");
        if (fournisseur == null) {
            int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));
            Connection con = Base.PsqlConnect();
            fournisseur = new Fournisseur().findById(idFournisseur,con);
            fournisseur.setProduitFournisseurs(con);
            request.getSession().setAttribute("fournisseur", fournisseur);
        }
        request.setAttribute("fournisseur", fournisseur);
        request.setAttribute("produits",new Produit().findAll(null));
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/fournisseur/info_fournisseur.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffListBonDeLivraison")
    public void AffListBonDeLivraison () throws ServletException, IOException, SQLException {
        Fournisseur fournisseur = (Fournisseur) request.getSession().getAttribute("fournisseur");
        Connection con = Base.PsqlConnect();
        request.setAttribute("bonDeCommandes", new BonDeCommande().getBonDeCommandeNonLivrer(fournisseur.getId(),true,con));
        request.setAttribute("bonDeLivraisons", new BonDeLivraison().findAllByIdFournisseur(fournisseur.getId(), con));
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/fournisseur/list_bonDeLivraison.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddBonDeLivraison")
    public void AddBonDeLivraison () throws ServletException, IOException, SQLException {
        int idBonDeCommande = Integer.parseInt(request.getParameter("idBonDeCommande"));
        double quantite = Double.parseDouble(request.getParameter("quantite"));
        double montant = Double.parseDouble(request.getParameter("montant"));
        Date date_livraison = Date.valueOf(request.getParameter("date_livraison"));

        Connection con = Base.PsqlConnect();
        
        BonDeLivraison bonDeLivraison = new BonDeLivraison(idBonDeCommande,quantite,montant,date_livraison);
        bonDeLivraison.insert(con);
        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("AffListBonDeLivraison.FournisseurController");
        rd.forward(request, response);
    }
}
