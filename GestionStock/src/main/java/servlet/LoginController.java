package servlet;

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
import java.sql.SQLException;

@WebServlet(name = "LoginController", value = "*.LoginController")
public class LoginController extends MereController {
    @CtrlAnnotation(name = "loginAdmin")
    public void loginAdmin () throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Admin admin = new Admin().findByEmailAndPassword(email,password,null);
        String path = "Pages/loginAdmin.jsp";
        if(admin != null){
            request.getSession().setAttribute("admin",admin);
            path = "Pages/home.jsp";
        }
        else {
            request.setAttribute("message","Compte inexistante");
        }
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "loginDepartement")
    public void loginDepartement () throws ServletException, IOException, SQLException {

        Admin admin = (Admin) request.getSession().getAttribute("admin");
        String path = "";
        if (admin != null) {
            int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
            request.getSession().setAttribute("departement",new Departement().findById(idDepartement,null));
            path = "/AffInfo.DepartementController";
        }
        if (admin == null) {
            path = "/Pages/logInDepartement.jsp";
            int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
            String email = request.getParameter("email");

            Connection con = Base.PsqlConnect();
            Employer employer = new Employer().findByEmail(email,null);
            if (employer != null) {
                DepartementEmployer departementEmployer = new DepartementEmployer().findByIdEmployerAndIdDepartement(employer.getId(),idDepartement,con);
                if (departementEmployer != null) {
                    if (departementEmployer.getIdStatus() == 1){
                        path = "/AffInfo.DepartementController";
                    }
                    else {
                        request.setAttribute("message","Compte inexistante");
                    }
                }
                else {
                    request.setAttribute("message","Compte inexistante");
                }
            }
            else {
                request.setAttribute("message","Compte inexistante");
            }
            request.getSession().setAttribute("departement",new Departement().findById(idDepartement,null));

            con.close();
        }

        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }

}
