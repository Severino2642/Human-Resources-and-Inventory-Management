package fournisseur;

import connexion.Base;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;
import personne.Personne;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@Table
public class Fournisseur extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    String nom;
    @Colonne
    Date date_ajout;
    Personne [] personnes;

    public Fournisseur() {
    }

    public Fournisseur(int id, String nom, Date date_ajout) {
        this.id = id;
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public Fournisseur(String nom, Date date_ajout) {
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

    public Personne[] getPersonnes() {
        return personnes;
    }

    public void setPersonnes(Personne[] personnes) {
        this.personnes = personnes;
    }

    public Fournisseur findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Fournisseur result = null;
        Fournisseur [] list = this.findAll("WHERE id="+id,con).toArray(new Fournisseur[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }
}
