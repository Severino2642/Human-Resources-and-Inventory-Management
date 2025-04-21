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

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(name = "DepartementController", value = "*.DepartementController")
public class DepartementController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException, SQLException {
        String nom = request.getParameter("nom");
        Date date_ajout = Date.valueOf(request.getParameter("date_ajout"));

        Connection connection = Base.PsqlConnect();
        Departement departement = new Departement(nom, date_ajout);
        departement.insert(connection);
        connection.close();

        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.DepartementController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "Delete")
    public void Delete () throws ServletException, IOException, SQLException {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        Departement departement = new Departement();
        departement.setId(idDepartement);
        Connection connection = Base.PsqlConnect();
        departement.delete("id",connection);
        connection.close();

        request.setAttribute("message", "supression effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.DepartementController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException {
        Connection connection = Base.PsqlConnect();
        Departement [] departements = new Departement().findAll("",connection).toArray(new Departement[]{});
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
        connection.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/departement/info_departement.jsp");
        rd.forward(request, response);
    }
}
