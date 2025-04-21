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
public class Annonce extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idDemande;
    @Colonne
    int idFournisseur;
    @Colonne
    Date date_ajout;
    Demande demande;
    Suggestion [] suggestions;
    Fournisseur fournisseur;
    public Annonce() {
    }

    public Annonce(int id, int idDemande, int idFournisseur, Date date_ajout) {
        this.id = id;
        this.idDemande = idDemande;
        this.idFournisseur = idFournisseur;
        this.date_ajout = date_ajout;
    }

    public Annonce(int idDemande, int idFournisseur, Date date_ajout) {
        this.idDemande = idDemande;
        this.idFournisseur = idFournisseur;
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

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public Demande getDemande() {
        if (demande==null) {
            this.setDemande();
        }
        return demande;
    }

    public void setDemande(){
        try {
            Connection conn = Base.PsqlConnect();
            this.demande = new Demande().findById(this.getIdDemande(),conn);
            this.demande.setExperiences();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Fournisseur getFournisseur() {
        if (fournisseur==null) {
            this.setFournisseur();
        }
        return fournisseur;
    }

    public void setFournisseur(){
        try {
            Connection conn = Base.PsqlConnect();
            this.fournisseur = new Fournisseur().findById(this.getIdFournisseur(),conn);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Annonce[] findByidFournisseur (int idFournisseur, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Annonce[] result = this.findAll("WHERE idFournisseur="+idFournisseur,con).toArray(new Annonce[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public Annonce[] findByidDemande (int idDemande, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Annonce[] result = this.findAll("WHERE idDemande="+idDemande,con).toArray(new Annonce[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public Fournisseur [] getFournisseurNotAnnoncer(Annonce [] annonces,Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Fournisseur [] list = new Fournisseur().findAll("",con).toArray(new Fournisseur[]{});
        List<Fournisseur> result = new ArrayList<Fournisseur>();
        for (Fournisseur fournisseur : list){
            boolean isAnnonce = false;
            for (Annonce annonce : annonces){
                if (fournisseur.getId() == annonce.getIdFournisseur()){
                    isAnnonce = true;
                    break;
                }
            }
            if (!isAnnonce){
                result.add(fournisseur);
            }
        }
        if(con!=null && new_con){
            con.close();
        }
        return result.toArray(new Fournisseur[]{});
    }

    public Annonce findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Annonce result = null;
        Annonce [] list = this.findAll("WHERE id="+id,con).toArray(new Annonce[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

    public Suggestion[] getSuggestions() {
        return suggestions;
    }
    public void setSuggestions(Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        this.suggestions = new Suggestion().findByidAnnonce(this.getId(),con);
        if (con!=null && new_connection){
            con.close();
        }
    }
    public void setSuggestions(Suggestion[] suggestions) {
        this.suggestions = suggestions;
    }

    public Personne[] getAllPersonneSuggerer (Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Personne[] result =new Personne().findAll("WHERE id IN ( SELECT idPersonne FROM suggestion WHERE idAnnonce="+this.getId()+")",con).toArray(new Personne[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

}
