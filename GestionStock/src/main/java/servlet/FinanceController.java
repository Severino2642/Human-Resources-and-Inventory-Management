package servlet;

import caisse.Caisse;
import connexion.Base;
import departement.DepartementValidation;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import simpleController.CtrlAnnotation;
import simpleController.MereController;

import java.sql.Connection;
import java.sql.Date;

@WebServlet(name = "FinanceController", value = "*.FinanceController")
public class FinanceController extends MereController {
    @CtrlAnnotation(name = "Valide")
    public void Valide () throws Exception {
        int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
        int idDemande = Integer.parseInt(request.getParameter("idDemande"));

        Connection con = Base.PsqlConnect();
        DepartementValidation dpvFinance = new DepartementValidation().findByIdDemandeAndIdDepartement(idDemande,idDepartement,con);
        dpvFinance.setValid(true);
        dpvFinance.updateByIdDemandeAndIdDepartement(con);
        con.close();

        RequestDispatcher rd = request.getRequestDispatcher("AffListDemandeEncours.DemandeController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "InsertCaisse")
    public void InsertCaisse () throws Exception {
        boolean isEntrer = Boolean.parseBoolean(request.getParameter("isEntrer"));
        double montant = Double.parseDouble(request.getParameter("montant"));
        Date date_modif = Date.valueOf(request.getParameter("date_modif"));

        Caisse caisse = new Caisse(isEntrer, montant, date_modif);
        caisse.insert(null);

        RequestDispatcher rd = request.getRequestDispatcher("AffEtatCaisse.FinanceController");
        rd.forward(request, response);
    }

    @CtrlAnnotation(name = "AffEtatCaisse")
    public void AffEtatCaisse () throws Exception {
        Connection con = Base.PsqlConnect();
        Caisse c = new Caisse();
        Caisse [] caissesEntrer = c.getEtatDeCaisse(true,con);
        Caisse [] caissesSortie = c.getEtatDeCaisse(false,con);

        request.setAttribute("caissesEntrer",caissesEntrer);
        request.setAttribute("caissesSortie",caissesSortie);
        con.close();
        RequestDispatcher rd = request.getRequestDispatcher("/Pages/caisse/info_caisse.jsp");
        rd.forward(request, response);
    }


}
