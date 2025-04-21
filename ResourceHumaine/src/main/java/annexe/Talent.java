package annexe;

import connexion.Base;
import demande.ExperienceDemande;
import departement.Departement;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Table
public class Talent extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    String nom;
    @Colonne
    Date date_ajout;
    Question [] questions;

    public Talent() {
    }

    public Talent(int id, String nom, Date date_ajout) {
        this.id = id;
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public Talent(String nom, Date date_ajout) {
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

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions() {
        try {
            Connection conn = Base.PsqlConnect();
            this.questions = new Question().findByidTalent(this.getId(),conn);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public Talent findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Talent result = null;
        Talent [] list = this.findAll("WHERE id="+id,con).toArray(new Talent[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }
}
