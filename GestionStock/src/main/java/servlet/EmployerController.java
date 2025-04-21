package servlet;

import departement.Departement;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import simpleController.CtrlAnnotation;
import simpleController.MereController;
import user.Employer;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "EmployerController", value = "*.EmployerController")
public class EmployerController extends MereController {
    @CtrlAnnotation(name = "Insert")
    public void Insert () throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        Date date_embauche = Date.valueOf(request.getParameter("date_embauche"));

        Employer employer = new Employer(nom, email, date_embauche);
        employer.insert(null);

        request.setAttribute("message", "insertion effectuer");
        RequestDispatcher rd = request.getRequestDispatcher("AffList.EmployerController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffList")
    public void AffList () throws ServletException, IOException {
        Employer [] employers = new Employer().findAll(null);
        request.setAttribute("employers", employers);

        RequestDispatcher rd = request.getRequestDispatcher("/Pages/employer/list_employer.jsp");
        rd.forward(request, response);
    }
}
