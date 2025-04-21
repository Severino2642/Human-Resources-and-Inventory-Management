package servlet;

import annexe.GestionStock;
import connexion.Base;
import demande.Demande;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import produit.PrixProduit;
import produit.Produit;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "ProduitController", value = "*.ProduitController")
public class ProduitController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException {
        String nom = request.getParameter("nom");
        int idGestionStock = Integer.parseInt(request.getParameter("idGestionStock"));

        Produit produit = new Produit(idGestionStock,nom);
        produit.insert(null);

        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.ProduitController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "Delete")
    public void Delete () throws ServletException, IOException {
        int idProduit = Integer.parseInt(request.getParameter("idProduit"));

        Produit produit = new Produit();
        produit.setId(idProduit);
        produit.delete(null);

        request.setAttribute("message", "suppression effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.ProduitController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "InsertPrix")
    public void InsertPrix() throws SQLException, Exception {
        int idProduit = Integer.parseInt(request.getParameter("idProduit"));
        double valeur = Double.parseDouble(request.getParameter("valeur"));
        boolean isVente = Boolean.parseBoolean(request.getParameter("isVente"));
        Date date_modif = Base.getDate();
        if (valeur > 0){
            PrixProduit prixProduit = new PrixProduit(idProduit,isVente,valeur,date_modif);
            prixProduit.insert(null);
            request.setAttribute("message" , "insertion effectuer");
        }
        else {
            request.setAttribute("message" , "le prix est invalide");
        }

        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.ProduitController?idProduit=" + idProduit);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException, SQLException {
        Connection con = Base.PsqlConnect();
        request.setAttribute("produits",new Produit().findAll(con));
        request.setAttribute("gestionStocks",new GestionStock().findAll(con));
        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("/Pages/produit/list_produit.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfo")
    public void AffInfo() throws SQLException, Exception {
        Connection connection = Base.PsqlConnect();
        int idProduit = Integer.parseInt(request.getParameter("idProduit"));
        request.setAttribute("produit", new Produit().findById(idProduit,connection));
        request.setAttribute("prixVentes", new PrixProduit().getAllPrixByidProduit(idProduit,true,connection));
        request.setAttribute("prixAchats", new PrixProduit().getAllPrixByidProduit(idProduit,false,connection));
        connection.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/produit/info_produit.jsp");
        rd.forward(request, response);
    }

}
