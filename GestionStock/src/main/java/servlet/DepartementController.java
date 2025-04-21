package servlet;

import annexe.Status;
import connexion.Base;
import departement.Departement;
import departement.DepartementEmployer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import simpleController.CtrlAnnotation;
import simpleController.MereController;
import user.Admin;
import user.Employer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "DepartementController", value = "*.DepartementController")
public class DepartementController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException {
        String nom = request.getParameter("nom");
        Date date_ajout = Date.valueOf(request.getParameter("date_ajout"));

        Departement departement = new Departement(nom, date_ajout);
        departement.insert(null);

        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.DepartementController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "Delete")
    public void Delete () throws ServletException, IOException {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        Departement departement = new Departement();
        departement.setId(idDepartement);
        departement.delete(null);

        request.setAttribute("message", "supression effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.DepartementController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException {
        Departement [] departements = new Departement().findAll(null);
        request.setAttribute("departements", departements);
        request.getSession().setAttribute("departement", null);

        RequestDispatcher rd = request.getRequestDispatcher("/Pages/departement/list_departement.jsp");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffInfo")
    public void AffInfo () throws ServletException, IOException, SQLException {
        Departement departement = (Departement) request.getSession().getAttribute("departement");
        Connection connection = Base.PsqlConnect();
        departement.setEmployers(connection);

        request.setAttribute("departement", departement);
        request.setAttribute("nonRecruter", new Employer().getEmployerNoRecruter(departement.getId(),connection));
        request.setAttribute("listStatus", new Status().findAll(connection));
        connection.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/departement/info_departement.jsp");
        rd.forward(request, response);
    }



    @CtrlAnnotation(name = "Recruter")
    public void Recruter () throws ServletException, IOException {
        int idEmployer = Integer.parseInt(request.getParameter("idEmployer"));
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idStatus = Integer.parseInt(request.getParameter("idStatus"));
        Date date_ajout = Date.valueOf(request.getParameter("date_ajout"));

        DepartementEmployer d = new DepartementEmployer(idEmployer, idDepartement, idStatus, date_ajout);
        d.insert(null);

        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.DepartementController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "Renvoyer")
    public void Renvoyer () throws ServletException, IOException, SQLException {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idEmployer = Integer.parseInt(request.getParameter("idEmployer"));

        Connection con = Base.PsqlConnect();
        DepartementEmployer d = new DepartementEmployer().findByIdEmployerAndIdDepartement(idEmployer, idDepartement, con);
        d.delete(con);
        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.DepartementController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "ChangerPoste")
    public void ChangerPoste () throws ServletException, IOException {
        int idEmployer = Integer.parseInt(request.getParameter("idEmployer"));
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idStatus = Integer.parseInt(request.getParameter("idStatus"));

        Connection con = Base.PsqlConnect();
        DepartementEmployer d = new DepartementEmployer().findByIdEmployerAndIdDepartement(idEmployer,idDepartement,con);
        if (d.getIdStatus() != idStatus){
            d.setIdStatus(idStatus);
            d.update(con);
        }

        RequestDispatcher rd = request.getRequestDispatcher("AffInfo.DepartementController");
        rd.forward(request, response);
    }
}
