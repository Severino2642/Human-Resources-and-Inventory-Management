package servlet;

import annexe.Question;
import annexe.Reponse;
import annexe.Sexe;
import annexe.Talent;
import connexion.Base;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import personne.Personne;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "TalentController",value = "*.TalentController")
public class TalentController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws Exception {
        String nom = request.getParameter("nom");
        Date date_ajout = Base.getDate();

        Connection con = Base.PsqlConnect();
        Talent t = new Talent(nom, date_ajout);
        t.insert(con);
        con.close();
        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.TalentController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException, SQLException {
        Connection con = Base.PsqlConnect();
        Talent[] talents = new Talent().findAll("",con).toArray(new Talent[]{});
        request.setAttribute("talents", talents);
        request.getSession().setAttribute("talent", null);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/employer/list_talent.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfo")
    public void AffInfo () throws ServletException, IOException, SQLException {
        Talent talent = (Talent) request.getSession().getAttribute("talent");
        Connection con = Base.PsqlConnect();
        if (talent == null) {
            int idTalent = Integer.parseInt(request.getParameter("idTalent"));
            talent = new Talent().findById(idTalent,con);
            request.getSession().setAttribute("talent", talent);
        }
        Question [] questions = new Question().findByidTalent(talent.getId(), con);
        talent.setQuestions(questions);
        con.close();
        request.setAttribute("talent", talent);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/employer/info_talent.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfoQuestion")
    public void AffInfoQuestion () throws ServletException, IOException, SQLException {
        int idQuestion = Integer.parseInt(request.getParameter("idQuestion"));
        Connection con = Base.PsqlConnect();
        Question question = new Question().findById(idQuestion,con);
        Reponse [] reponses = new Reponse().findByidQuestion(idQuestion,con);
        question.setReponses(reponses);
        con.close();
        request.setAttribute("question", question);
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/employer/info_question.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddQuestion")
    public void AddQuestion () throws Exception {
        int idTalent = Integer.parseInt(request.getParameter("idTalent"));
        String intitule = request.getParameter("intitule");
        Date date_ajout = Base.getDate();
        Connection con = Base.PsqlConnect();
        Question question = new Question(idTalent, intitule, date_ajout);
        question.insert(con);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.TalentController?idTalent="+idTalent);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "DeleteQuestion")
    public void DeleteQuestion () throws Exception {
        int idQuestion = Integer.parseInt(request.getParameter("idQuestion"));
        Connection con = Base.PsqlConnect();
        Question question = new Question();
        question.setId(idQuestion);
        question.delete("id",con);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.TalentController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AddReponse")
    public void AddReponse () throws Exception {
        int idQuestion = Integer.parseInt(request.getParameter("idQuestion"));
        String intitule = request.getParameter("intitule");
        int point = Integer.parseInt(request.getParameter("point"));
        Connection con = Base.PsqlConnect();
        Reponse reponse = new Reponse(idQuestion, intitule, point);
        reponse.insert(con);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfoQuestion.TalentController?idQuestion="+idQuestion);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "DeleteReponse")
    public void DeleteReponse () throws Exception {
        int idQuestion = Integer.parseInt(request.getParameter("idQuestion"));
        int idReponse = Integer.parseInt(request.getParameter("idReponse"));
        Connection con = Base.PsqlConnect();
        Reponse reponse = new Reponse();
        reponse.setId(idReponse);
        reponse.delete("id",con);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("AffInfoQuestion.TalentController?idQuestion="+idQuestion);
        rd.forward(request, response);
    }
}
