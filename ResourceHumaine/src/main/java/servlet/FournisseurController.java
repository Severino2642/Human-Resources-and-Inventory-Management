package servlet;

import connexion.Base;

import demande.Annonce;
import demande.Suggestion;
import fournisseur.Fournisseur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import personne.Candidat;
import personne.Personne;
import simpleController.CtrlAnnotation;
import simpleController.MereController;
import util.PdfCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "FournisseurController", value = "*.FournisseurController")
public class FournisseurController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException, SQLException {
        String nom = request.getParameter("nom");
        Date date_ajout = Date.valueOf(request.getParameter("date_ajout"));

        Fournisseur f = new Fournisseur(nom, date_ajout);
        Connection con = Base.PsqlConnect();
        f.insert(con);
        con.close();
        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.FournisseurController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "Delete")
    public void Delete () throws ServletException, IOException, SQLException {
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));

        Connection connection = Base.PsqlConnect();
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(idFournisseur);
        fournisseur.delete("id",connection);
        connection.close();
        request.setAttribute("message", "suppression effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.FournisseurController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException, SQLException {
        Connection connection = Base.PsqlConnect();
        request.setAttribute("fournisseurs", new Fournisseur().findAll("",connection).toArray(new Fournisseur[]{}));
        request.getSession().setAttribute("fournisseur", null);
        connection.close();
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
            request.getSession().setAttribute("fournisseur", fournisseur);
            con.close();
        }
        request.setAttribute("fournisseur", fournisseur);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/fournisseur/info_fournisseur.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffListAnnonce")
    public void AffListAnnonce () throws ServletException, IOException, SQLException {
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));
        Connection con = Base.PsqlConnect();
        Annonce [] annonces = new Annonce().findByidFournisseur(idFournisseur,con);
        con.close();

        request.setAttribute("annonces", annonces);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/fournisseur/list_annonce.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfoAnnonce")
    public void AffInfoAnnonce () throws ServletException, IOException, SQLException {
        int idAnnonce = Integer.parseInt(request.getParameter("idAnnonce"));
        Connection con = Base.PsqlConnect();
        Annonce annonce = new Annonce().findById(idAnnonce,con);
        Suggestion [] suggestions = new Suggestion().findByidAnnonce(idAnnonce,con);
        Personne [] personnes = new Suggestion().getPersonneNotSuggerer(annonce.getIdFournisseur(),suggestions,con);
        Candidat [] sug_personnes = new Candidat().generateCandidatForDemande(annonce.getDemande(),personnes,con).toArray(new Candidat[]{});
        con.close();

        request.setAttribute("annonce", annonce);
        request.setAttribute("sug_personnes", sug_personnes);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/fournisseur/info_annonce.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddSuggestion")
    public void AddSuggestion () throws Exception {
        int idAnnonce = Integer.parseInt(request.getParameter("idAnnonce"));
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
        Date date_ajout = Base.getDate();
        Connection con = Base.PsqlConnect();
        Suggestion suggestion = new Suggestion(idAnnonce,idPersonne,date_ajout);
        suggestion.insert(con);
        Personne personne = new Personne().findById(idPersonne,con);
        PdfCreator pdfCreator = new PdfCreator();
        String fileName = "CV_"+idAnnonce+"_"+idPersonne+"_"+date_ajout.toString()+".pdf";
        pdfCreator.UploadCVPdf(request,personne,fileName);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfoAnnonce.FournisseurController?idAnnonce="+idAnnonce);
        rd.forward(request, response);
    }
}
