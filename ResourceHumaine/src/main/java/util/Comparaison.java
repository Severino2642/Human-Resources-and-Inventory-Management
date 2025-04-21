package util;

import connexion.Base;
import demande.Demande;
import demande.ExperienceDemande;
import personne.Candidat;
import personne.Experience;
import personne.Personne;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Comparaison {
    Demande demande;
    List<Candidat> candidats = new ArrayList<Candidat>();

    public Comparaison() {
    }

    public Comparaison(Demande demande) {
        this.demande = demande;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public List<Candidat> getCandidats() {
        return candidats;
    }

    public void setCandidats(List<Candidat> candidats) {
        this.candidats = candidats;
    }


}
