package demande;

import connexion.Base;
import fournisseur.Fournisseur;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;
import personne.Personne;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Table
public class Suggestion extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idAnnonce;
    @Colonne
    int idPersonne;
    @Colonne
    Date date_ajout;
    Personne personne;
    public Suggestion() {
    }

    public Suggestion(int id, int idAnnonce, int idPersonne, Date date_ajout) {
        this.id = id;
        this.idAnnonce = idAnnonce;
        this.idPersonne = idPersonne;
        this.date_ajout = date_ajout;
    }

    public Suggestion(int idAnnonce, int idPersonne, Date date_ajout) {
        this.idAnnonce = idAnnonce;
        this.idPersonne = idPersonne;
        this.date_ajout = date_ajout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(int idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
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

    public Suggestion[] findByidAnnonce (int idAnnonce, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Suggestion[] result = this.findAll("WHERE idAnnonce="+idAnnonce,con).toArray(new Suggestion[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public Personne[] getPersonneNotSuggerer(int idFournisseur,Suggestion [] suggestions, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Personne [] personnes = new Personne().getPersonneNoRecruterByIdFournisseur(idFournisseur,con);
        List<Personne> result = new ArrayList<Personne>();
        for (Personne p : personnes){
            boolean isSuggerer = false;
            for (Suggestion s : suggestions){
                if (p.getId() == s.getIdPersonne()){
                    isSuggerer = true;
                    break;
                }
            }
            if (!isSuggerer){
                result.add(p);
            }
        }
        if(con!=null && new_con){
            con.close();
        }
        return result.toArray(new Personne[]{});
    }
}
