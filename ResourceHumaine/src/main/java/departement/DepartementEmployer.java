package departement;

import connexion.Base;
import demande.Demande;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Table(nom = "departement_employer")
public class DepartementEmployer extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idPersonne;
    @Colonne
    int idDepartement;
    @Colonne
    int idStatus;
    @Colonne
    Date date_ajout;

    public DepartementEmployer() {
    }

    public DepartementEmployer(int id, int idPersonne, int idDepartement, int idStatus, Date date_ajout) {
        this.id = id;
        this.idPersonne = idPersonne;
        this.idDepartement = idDepartement;
        this.idStatus = idStatus;
        this.date_ajout = date_ajout;
    }

    public DepartementEmployer(int idPersonne, int idDepartement, int idStatus, Date date_ajout) {
        this.idPersonne = idPersonne;
        this.idDepartement = idDepartement;
        this.idStatus = idStatus;
        this.date_ajout = date_ajout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public DepartementEmployer[] findByidDepartement (int idDepartement, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        DepartementEmployer [] result = this.findAll("WHERE idDepartement="+idDepartement,con).toArray(new DepartementEmployer[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }
}
