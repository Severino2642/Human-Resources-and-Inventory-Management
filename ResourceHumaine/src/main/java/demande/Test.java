package demande;

import annexe.Talent;
import connexion.Base;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;
import personne.Personne;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@Table
public class Test extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idDemande;
    @Colonne
    int idPersonne;
    @Colonne
    double note;
    @Colonne
    Date date_ajout;
    Personne personne;
    Demande demande;

    public Test() {
    }

    public Test(int id, int idDemande, int idPersonne, double note, Date date_ajout) {
        this.id = id;
        this.idDemande = idDemande;
        this.idPersonne = idPersonne;
        this.note = note;
        this.date_ajout = date_ajout;
    }

    public Test(int idDemande, int idPersonne, double note, Date date_ajout) {
        this.idDemande = idDemande;
        this.idPersonne = idPersonne;
        this.note = note;
        this.date_ajout = date_ajout;
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

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public Personne getPersonne() {
        if (personne == null) {
            this.setPersonne();
        }
        return personne;
    }
    public void setPersonne(){
        try {
            Connection con = Base.PsqlConnect();
            this.personne = new Personne().findById(this.getIdPersonne(),con);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public Demande getDemande() {
        if (demande == null) {
            this.setDemande();
        }
        return demande;
    }
    public void setDemande(){
        try {
            Connection con = Base.PsqlConnect();
            this.demande = new Demande().findById(this.getIdDemande(),con);
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Test[] getTesteNotValideByidDemande (int idDemande, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Test[] result = this.findAll("WHERE note=-1 AND idDemande="+idDemande+" ORDER BY note DESC",con).toArray(new Test[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public Test[] getTestValideByidDemande (int idDemande, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Test[] result = this.findAll("WHERE note>-1 AND idDemande="+idDemande+" ORDER BY note DESC",con).toArray(new Test[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public Test findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Test result = null;
        Test [] list = this.findAll("WHERE id="+id,con).toArray(new Test[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }
    public Test findByIdPersonneAndIdDemande(int idDemande,int idPersonne, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Test result = null;
        Test [] list = this.findAll("WHERE idDemande="+idDemande+" AND idPersonne="+idPersonne,con).toArray(new Test[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

    public Test [] findByIdPersonne(int idPersonne, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Test [] result = this.findAll("WHERE idPersonne="+idPersonne,con).toArray(new Test[]{});
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

    public Personne[] getAllPersonneTesterByIdDemande (int idDemande,Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Personne[] result =new Personne().findAll("WHERE id IN ( SELECT idPersonne FROM test WHERE note>-1 AND idDemande="+idDemande+")",con).toArray(new Personne[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }
}
