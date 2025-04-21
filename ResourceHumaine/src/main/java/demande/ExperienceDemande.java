package demande;

import annexe.Talent;
import connexion.Base;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;
import personne.Experience;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@Table( nom = "experience_demande")
public class ExperienceDemande extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idDemande;
    @Colonne
    int idTalent;
    @Colonne
    int duree;
    Talent talent;

    public ExperienceDemande() {
    }

    public ExperienceDemande(int id, int idDemande, int idTalent, int duree) {
        this.id = id;
        this.idDemande = idDemande;
        this.idTalent = idTalent;
        this.duree = duree;
    }

    public ExperienceDemande(int idDemande, int idTalent, int duree) {
        this.idDemande = idDemande;
        this.idTalent = idTalent;
        this.duree = duree;
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

    public ExperienceDemande[] findByidDemande (int idDemande, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        ExperienceDemande [] result = this.findAll("WHERE idDemande="+idDemande,con).toArray(new ExperienceDemande[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public ExperienceDemande findByidDemandeAndidTalent (int idDemande,int idTalent, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        ExperienceDemande result = null;
        ExperienceDemande [] list = this.findAll("WHERE idDemande="+idDemande+" AND idTalent="+idTalent,con).toArray(new ExperienceDemande[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if(con!=null && new_con){
            con.close();
        }
        return result;
    }
}
