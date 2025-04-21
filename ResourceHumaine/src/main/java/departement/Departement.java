package departement;

import annexe.Status;
import connexion.Base;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;
import personne.Personne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Table
public class Departement extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    String nom;
    @Colonne
    Date date_ajout;
    List<Personne> employers = new ArrayList<>();

    public Departement() {
    }

    public Departement(String nom, Date date_ajout) {
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public Departement(int id, String nom, Date date_ajout) {
        this.id = id;
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public List<Personne> getEmployers() {
        return employers;
    }

    public void setEmployers (Connection con) throws SQLException {
        boolean newConnex = false;
        if (con == null){
            newConnex = true;
            con = Base.PsqlConnect();
        }
        this.employers = new ArrayList<>();
        DepartementEmployer [] departementEmployer = new DepartementEmployer().findByidDepartement(this.getId(),con);
        for (DepartementEmployer de : departementEmployer){
            Status status = new Status().findById(de.getIdStatus(),con);
            Personne emp = new Personne().findById(de.getIdPersonne(),con);
            emp.setStatus(status);
            emp.setDepartementEmployer(de);
            this.employers.add(emp);
        }
        if (newConnex && con != null){
            con.close();
        }
    }

    public Departement findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Departement result = null;
        Departement [] list = this.findAll("WHERE id="+id,con).toArray(new Departement[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

}
