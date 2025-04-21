package annexe;

import connexion.Base;
import demande.Demande;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;

import java.sql.Connection;
import java.sql.SQLException;

@Table
public class Reponse extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idQuestion;
    @Colonne
    String intitule;
    @Colonne
    int point;

    public Reponse() {
    }

    public Reponse(int id, int idQuestion, String intitule, int point) {
        this.id = id;
        this.idQuestion = idQuestion;
        this.intitule = intitule;
        this.point = point;
    }

    public Reponse(int idQuestion, String intitule, int point) {
        this.idQuestion = idQuestion;
        this.intitule = intitule;
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Reponse[] findByidQuestion (int idQuestion, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Reponse [] result = this.findAll("WHERE idQuestion="+idQuestion,con).toArray(new Reponse[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }
}
