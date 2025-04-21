package annexe;

import connexion.Base;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@Table
public class Question extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idTalent;
    @Colonne
    String intitule;
    @Colonne
    Date date_ajout;
    Reponse [] reponses;

    public Question() {
    }

    public Question(int id, int idTalent, String intitule, Date date_ajout) {
        this.id = id;
        this.idTalent = idTalent;
        this.intitule = intitule;
        this.date_ajout = date_ajout;
    }

    public Question(int idTalent, String intitule, Date date_ajout) {
        this.idTalent = idTalent;
        this.intitule = intitule;
        this.date_ajout = date_ajout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTalent() {
        return idTalent;
    }

    public void setIdTalent(int idTalent) {
        this.idTalent = idTalent;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public Reponse[] getReponses() {
        return reponses;
    }

    public void setReponses(Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        this.reponses = new Reponse().findByidQuestion(this.getId(),con);
        if (con!=null && new_connection){
            con.close();
        }
    }

    public void setReponses(Reponse[] reponses) {
        this.reponses = reponses;
    }

    public Question findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Question result = null;
        Question [] list = this.findAll("WHERE id="+id,con).toArray(new Question[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

    public Question[] findByidTalent (int idTalent, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Question [] result = this.findAll("WHERE idTalent="+idTalent,con).toArray(new Question[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }
}
