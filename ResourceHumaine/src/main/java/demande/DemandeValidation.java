package demande;

import connexion.Base;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@Table(nom = "demande_validation")
public class DemandeValidation extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idDemande;
    @Colonne
    int idPersonne;
    @Colonne
    Date date_validation;

    public DemandeValidation() {
    }

    public DemandeValidation(int id, int idDemande, int idPersonne, Date date_validation) {
        this.id = id;
        this.idDemande = idDemande;
        this.idPersonne = idPersonne;
        this.date_validation = date_validation;
    }

    public DemandeValidation(int idDemande, int idPersonne, Date date_validation) {
        this.idDemande = idDemande;
        this.idPersonne = idPersonne;
        this.date_validation = date_validation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public Date getDate_validation() {
        return date_validation;
    }

    public void setDate_validation(Date date_validation) {
        this.date_validation = date_validation;
    }

    public DemandeValidation[] findByidDemande (int idDemande, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        DemandeValidation [] result = this.findAll("WHERE idDemande="+idDemande,con).toArray(new DemandeValidation[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public DemandeValidation findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        DemandeValidation result = null;
        DemandeValidation [] list = this.findAll("WHERE id="+id,con).toArray(new DemandeValidation[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }
}
