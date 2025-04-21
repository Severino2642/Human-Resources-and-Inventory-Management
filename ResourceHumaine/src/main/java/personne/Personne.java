package personne;

import annexe.Sexe;
import annexe.Status;
import connexion.Base;
import demande.Test;
import departement.DepartementEmployer;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Table
public class Personne extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idFournisseur;
    @Colonne
    int idSexe;
    @Colonne
    String nom;
    @Colonne
    String email;
    @Colonne
    Date date_ajout;
    Experience [] experiences;
    Sexe sexe;
    Status status;
    DepartementEmployer departementEmployer;
    Test test;

    public Personne() {
    }

    public Personne(int id, int idFournisseur, int idSexe, String nom, String email, Date date_ajout) {
        this.id = id;
        this.idFournisseur = idFournisseur;
        this.idSexe = idSexe;
        this.nom = nom;
        this.email = email;
        this.date_ajout = date_ajout;
        this.setExperiences();
    }

    public Personne(int idFournisseur, int idSexe, String nom, String email, Date date_ajout) {
        this.idFournisseur = idFournisseur;
        this.idSexe = idSexe;
        this.nom = nom;
        this.email = email;
        this.date_ajout = date_ajout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DepartementEmployer getDepartementEmployer() {
        return departementEmployer;
    }

    public void setDepartementEmployer(DepartementEmployer departementEmployer) {
        this.departementEmployer = departementEmployer;
    }

    public Experience[] getExperiences() {
        return experiences;
    }

    public int getIdSexe() {
        return idSexe;
    }

    public void setIdSexe(int idSexe) {
        this.idSexe = idSexe;
    }

    public Sexe getSexe() {
        if (sexe == null) {
            this.setSexe();
        }
        return sexe;
    }

    public void setSexe() {
        try {
            Connection conn = Base.PsqlConnect();
            this.sexe = new Sexe().findById(this.getIdSexe(),conn);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setExperiences() {
        try {
            Connection conn = Base.PsqlConnect();
            this.experiences = new Experience().findByidPersonne(this.getId(),conn);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Personne [] findByidFournisseur (int idFournisseur, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Personne [] result = this.findAll("WHERE idFournisseur="+idFournisseur,con).toArray(new Personne[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public Personne findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Personne result = null;
        Personne [] list = this.findAll("WHERE id="+id,con).toArray(new Personne[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

    public Personne [] getEmployerNoRecruterByIdDepartement (int idDepartement,Connection con){
        List<Personne> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM personne WHERE id NOT IN (SELECT de.idpersonne FROM departement_employer de WHERE iddepartement=?) AND id IN (SELECT de.idpersonne FROM departement_employer de WHERE iddepartement!=?)";
            st = con.prepareStatement(sql);
            st.setInt(1, idDepartement);
            st.setInt(2, idDepartement);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idFournisseur");
                int var3 = res.getInt("idSexe");
                String var4 = res.getString("nom");
                String var5 = res.getString("email");
                Date var6 = res.getDate("date_ajout");
                result.add(new Personne(var1, var2, var3, var4, var5, var6));
            }
        }catch (SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (con != null && newConnection) con.close();
                if (res != null) res.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        return result.toArray(new Personne[]{});
    }

    public Personne [] getPersonneNoRecruterByIdFournisseur (int idFournisseur,Connection con){
        List<Personne> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM personne WHERE id NOT IN (SELECT de.idpersonne FROM departement_employer de) AND idFournisseur=?";
            st = con.prepareStatement(sql);
            st.setInt(1, idFournisseur);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idFournisseur");
                int var3 = res.getInt("idSexe");
                String var4 = res.getString("nom");
                String var5 = res.getString("email");
                Date var6 = res.getDate("date_ajout");
                result.add(new Personne(var1, var2, var3, var4, var5, var6));
            }
        }catch (SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (con != null && newConnection) con.close();
                if (res != null) res.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        return result.toArray(new Personne[]{});
    }

}
