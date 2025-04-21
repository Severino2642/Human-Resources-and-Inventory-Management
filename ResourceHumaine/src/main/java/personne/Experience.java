package personne;

import annexe.Sexe;
import annexe.Talent;
import connexion.Base;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@Table
public class Experience extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idPersonne;
    @Colonne
    int idTalent;
    @Colonne
    int duree;
    @Colonne
    Date date_ajout;
    Talent talent;
    public Experience() {
    }

    public Experience(int id, int idPersonne, int idTalent, int duree, Date date_ajout) {
        this.id = id;
        this.idPersonne = idPersonne;
        this.idTalent = idTalent;
        this.duree = duree;
        this.date_ajout = date_ajout;
    }

    public Experience(int idPersonne, int idTalent, int duree, Date date_ajout) {
        this.idPersonne = idPersonne;
        this.idTalent = idTalent;
        this.duree = duree;
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

    public int getIdTalent() {
        return idTalent;
    }

    public void setIdTalent(int idTalent) {
        this.idTalent = idTalent;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public Talent getTalent() {
        if (talent == null) {
            this.setTalent();
        }
        return talent;
    }

    public void setTalent() {
        try {
            Connection conn = Base.PsqlConnect();
            this.talent = new Talent().findById(this.getIdTalent(),conn);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Experience [] findByidPersonne (int idPersonne, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Experience [] result = this.findAll("WHERE idPersonne="+idPersonne,con).toArray(new Experience[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public Experience findByidPersonneAndidTalent (int idPersonne,int idTalent, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Experience result = null;
        Experience [] list = this.findAll("WHERE idPersonne="+idPersonne+" AND idTalent="+idTalent,con).toArray(new Experience[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if(con!=null && new_con){
            con.close();
        }
        return result;
    }
}
