package servlet;

import annexe.Sexe;
import annexe.Talent;
import connexion.Base;
import demande.Demande;
import demande.Test;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import personne.Experience;
import personne.Personne;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "PersonneController", value = "*.PersonneController")
public class PersonneController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException, SQLException {
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));
        int idSexe = Integer.parseInt(request.getParameter("idSexe"));
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        Date date_ajout = Date.valueOf(request.getParameter("date_ajout"));

        Connection con = Base.PsqlConnect();
        Personne p = new Personne(idFournisseur,idSexe, nom, email, date_ajout);
        p.insert(con);
        con.close();
        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.PersonneController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddExperience")
    public void AddExperience () throws ServletException, IOException, SQLException {
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
        int idTalent = Integer.parseInt(request.getParameter("idTalent"));
        int duree = Integer.parseInt(request.getParameter("duree"));
        Date date_ajout = Date.valueOf(request.getParameter("date_ajout"));

        Connection con = Base.PsqlConnect();

        Experience experience = new Experience().findByidPersonneAndidTalent(idPersonne,idTalent,con);
        if (experience == null) {
            experience = new Experience(idPersonne,idTalent,duree,date_ajout);
            experience.insert(con);
        }
        else {
            if (experience.getDuree() != duree) {
                experience.setDuree(duree);
                experience.update("duree","id",con);
            }
        }
        con.close();
        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.PersonneController?idPersonne=" + idPersonne);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "DeleteExperience")
    public void DeleteExperience () throws ServletException, IOException, SQLException {
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
        int idExperience = Integer.parseInt(request.getParameter("idExperience"));

        Connection con = Base.PsqlConnect();
        Experience experience = new Experience();
        experience.setId(idExperience);
        experience.delete("id",con);
        con.close();
        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.PersonneController?idPersonne=" + idPersonne);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException, SQLException {
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));
        Connection con = Base.PsqlConnect();
        Personne [] personnes = new Personne().findByidFournisseur(idFournisseur,con);
        Sexe [] sexes = new Sexe().findAll("",con).toArray(new Sexe[]{});
        request.setAttribute("personnes", personnes);
        request.setAttribute("sexes", sexes);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/employer/list_employer.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfo")
    public void AffInfo () throws ServletException, IOException, SQLException {
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
        Connection con = Base.PsqlConnect();
        Personne personne = new Personne().findById(idPersonne,con);
        personne.setExperiences();
        Talent[] talents = new Talent().findAll("",con).toArray(new Talent[]{});
        Test [] tests = new Test().findByIdPersonne(idPersonne,con);
        request.setAttribute("talents", talents);
        request.setAttribute("personne", personne);
        request.setAttribute("tests", tests);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/employer/info_personne.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffCV")
    public void AffCV () throws ServletException, IOException, SQLException {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
        Connection con = Base.PsqlConnect();
        Personne personne = new Personne().findById(idPersonne,con);
        personne.setExperiences();
        Demande demande = new Demande().findById(idDemande,con);

        request.setAttribute("personne", personne);
        request.setAttribute("demande", demande);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/info_candidat.jsp");
        rd.forward(request, response);
    }
}
