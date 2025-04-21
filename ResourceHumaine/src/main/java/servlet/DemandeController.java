package servlet;

import annexe.Question;
import annexe.Sexe;
import com.itextpdf.text.Document;
import connexion.Base;
import demande.*;
import departement.Departement;
import departement.DepartementEmployer;
import fournisseur.Fournisseur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import personne.Candidat;
import personne.Experience;
import personne.Personne;
import simpleController.CtrlAnnotation;
import simpleController.MereController;
import util.PdfCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DemandeController",value = "*.DemandeController")
public class DemandeController extends MereController {
    @CtrlAnnotation(name = "AffAddExperience")
    public void AffAddExperience () throws ServletException, IOException, SQLException {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idSexe = Integer.parseInt(request.getParameter("idSexe"));
        double quantite = Double.parseDouble(request.getParameter("quantite"));
        String intitule = request.getParameter("intitule");
        Date date_demande = Date.valueOf(request.getParameter("date_demande"));
        Demande demande = new Demande(idDepartement,intitule,quantite,idSexe,date_demande);

        request.getSession().setAttribute("demande",demande);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/ajout_demande.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddExperience")
    public void AddExperience () throws ServletException, IOException, SQLException {
        int idTalent = Integer.parseInt(request.getParameter("idTalent"));
        int duree = Integer.parseInt(request.getParameter("duree"));

        Demande demande = (Demande) request.getSession().getAttribute("demande");
        ExperienceDemande ed = demande.verifExperienceByidTalent(idTalent);
        if (ed == null) {
            demande.getExperiences().add(new ExperienceDemande(0,idTalent,duree));
        }
        else {
            ed.setDuree(duree);
        }
        request.getSession().setAttribute("demande",demande);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/ajout_demande.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "DeleteExperience")
    public void DeleteExperience () throws ServletException, IOException, SQLException {
        int idExperience = Integer.parseInt(request.getParameter("idExperience"));
        Demande demande = (Demande) request.getSession().getAttribute("demande");
        demande.getExperiences().remove(idExperience);
        request.getSession().setAttribute("demande",demande);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/ajout_demande.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException, SQLException {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        Demande demande = (Demande) request.getSession().getAttribute("demande");
        Connection connection = Base.PsqlConnect();
        demande.insert(connection);
        Demande d = demande.findLast(connection);
        for (ExperienceDemande e : demande.getExperiences()) {
            e.setIdDemande(d.getId());
            e.insert(connection);
        }
        connection.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffList.DemandeController?idDepartement="+idDepartement);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException, SQLException {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        Connection connection = Base.PsqlConnect();
        Demande [] demandes = new Demande().findByidDepartement(idDepartement,connection);
        Sexe [] sexes = new Sexe().findAll("",connection).toArray(new Sexe[]{});
        connection.close();
        request.setAttribute("demandes",demandes);
        request.setAttribute("sexes",sexes);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/list_demande.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfo")
    public void AffInfo () throws ServletException, IOException, SQLException {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        Connection connection = Base.PsqlConnect();
        Demande demande = new Demande().findById(idDemande,connection);
        demande.setExperiences();
        connection.close();
        request.setAttribute("demande",demande);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/info_demande.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffListDemandeEnCours")
    public void AffListDemandeEnCours () throws ServletException, IOException, SQLException {
        Connection connection = Base.PsqlConnect();
        Demande [] demandes = new Demande().getDemandeByValide(false,connection);
        connection.close();
        request.setAttribute("demandes",demandes);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/demande_encours_rh.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfoAvis")
    public void AffInfoAvis () throws ServletException, IOException, SQLException {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        Connection connection = Base.PsqlConnect();
        Demande demande = new Demande().findById(idDemande,connection);
        demande.setExperiences();
        Personne [] employers = new Personne().getEmployerNoRecruterByIdDepartement(demande.getIdDepartement(),connection);
        Candidat [] sug_employers = new Candidat().generateCandidatForDemande(demande,employers,connection).toArray(new Candidat[]{});
        Annonce [] annonces = new Annonce().findByidDemande(idDemande,connection);
        Fournisseur [] fournisseurs = new Annonce().getFournisseurNotAnnoncer(annonces,connection);

        List<Personne> personnes = new ArrayList<>();
        for (Annonce a:annonces){
            Personne [] list = a.getAllPersonneSuggerer(connection);
            for (Personne p:list){
                personnes.add(p);
            }
        }
        Candidat [] sug_personnes = new Candidat().generateCandidatForDemande(demande,personnes.toArray(new Personne[]{}), connection).toArray(new Candidat[]{});

        Candidat [] personneTesters = new Candidat().generateCandidatForDemande(demande,new Test().getAllPersonneTesterByIdDemande(idDemande,connection),connection).toArray(new Candidat[]{});
        connection.close();
        request.setAttribute("demande",demande);
        request.setAttribute("sug_employers",sug_employers);
        request.setAttribute("fournisseurs",fournisseurs);
        request.setAttribute("sug_personnes",sug_personnes);
        request.setAttribute("personneTesters",personneTesters);
        request.setAttribute("annonces",annonces);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/info_demande_avis.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddAnnonce")
    public void AddAnnonce () throws ServletException, IOException, SQLException {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        int idFournisseur = Integer.parseInt(request.getParameter("idFournisseur"));
        Date date_annonce = Date.valueOf(request.getParameter("date_annonce"));
        Connection connection = Base.PsqlConnect();
        Annonce annonce = new Annonce(idDemande,idFournisseur,date_annonce);
        annonce.insert(connection);
        Demande demande = new Demande().findById(idDemande,connection);
        PdfCreator pdfCreator = new PdfCreator();
        String fileName = "Annonce_"+idDemande+"_"+idFournisseur+"_"+date_annonce.toString()+".pdf";
        pdfCreator.UploadAnnoncePdf(request,demande,fileName);
        connection.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfoAvis.DemandeController?idDemande="+idDemande);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddTest")
    public void AddTest () throws Exception {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
        Date date_ajout = Base.getDate();
        Connection connection = Base.PsqlConnect();
        Test test = new Test(idDemande,idPersonne,-1,date_ajout);
        test.insert(connection);
        connection.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfoAvis.DemandeController?idDemande="+idDemande);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "RepondreTeste")
    public void RepondreTeste () throws Exception {
        int idTeste = Integer.parseInt(request.getParameter("idTeste"));
        int nbQuestion = Integer.parseInt(request.getParameter("nbQuestion"));
        Connection connection = Base.PsqlConnect();
        Test test = new Test().findById(idTeste,connection);
        double note = 0;
        for (int i = 0; i < nbQuestion; i++) {
            int point = Integer.parseInt(request.getParameter("point"+i));
            note += point;
        }
        test.setNote(note);
        test.update("note","id",connection);
        connection.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.PersonneController?idPersonne="+test.getIdPersonne());
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffTeste")
    public void AffTeste () throws Exception {
        int idTeste = Integer.parseInt(request.getParameter("idTeste"));
        Connection connection = Base.PsqlConnect();
        Test test = new Test().findById(idTeste,connection);
        Demande demande = new Demande().findById(test.getIdDemande(),connection);
        List<Personne> personnes = new ArrayList<>();
        personnes.add( new Personne().findById(test.getIdPersonne(),connection));
        Candidat [] candidats = new Candidat().generateCandidatForDemande(demande,personnes.toArray(new Personne[]{}),connection).toArray(new Candidat[]{});
        Question [] questions = candidats[0].getQuestionnaire(connection);
        connection.close();
        request.setAttribute("teste",test);
        request.setAttribute("questions",questions);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/demande/info_test.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddDemandeValidation")
    public void AddDemandeValidation () throws Exception {
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
        Date date_ajout = Base.getDate();
        Connection connection = Base.PsqlConnect();
        DemandeValidation demandeValidation = new DemandeValidation(idDemande,idPersonne,date_ajout);
        demandeValidation.insert(connection);

        Demande demande = new Demande().findById(idDemande,connection);
        demande.setDemandeValidations();
        Departement departement = new Departement().findById(demande.getIdDepartement(),connection);
        DepartementEmployer departementEmployer = new DepartementEmployer(idPersonne,departement.getId(),2,date_ajout);
        departementEmployer.insert(connection);
        Suggestion s = new Suggestion();
        s.setIdPersonne(idPersonne);
        s.delete("idPersonne",connection);
        Test t = new Test();
        t.setIdDemande(idDemande);
        t.setIdPersonne(idPersonne);
        t.delete("idDemande,idPersonne",connection);
        if (demande.getDemandeValidations().size() == demande.getQuantite()){
            Annonce annonce = new Annonce();
            annonce.setIdDemande(idDemande);
            annonce.delete("idDemande",connection);
            Test test = new Test();
            test.setIdDemande(idDemande);
            test.delete("idDemande",connection);
        }
        connection.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfoAvis.DemandeController?idDemande="+idDemande);
        rd.forward(request, response);
    }

}
