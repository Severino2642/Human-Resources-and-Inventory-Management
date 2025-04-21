package personne;

import annexe.Question;
import annexe.Talent;
import connexion.Base;
import demande.Demande;
import demande.ExperienceDemande;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Candidat {
    Personne personne;
    List<Experience> experienceDemande = new ArrayList<Experience>();

    public Candidat() {
    }

    public Candidat(Personne personne, List<Experience> experienceDemande) {
        this.personne = personne;
        this.experienceDemande = experienceDemande;
    }

    public Candidat(Personne personne) {
        this.personne = personne;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public List<Experience> getExperienceDemande() {
        return experienceDemande;
    }

    public void setExperienceDemande(List<Experience> experienceDemande) {
        this.experienceDemande = experienceDemande;
    }

    public double getNote (){
        return this.getExperienceDemande().size();
    }

    public boolean isSexeValide(Demande demande,Personne personne){
        if (demande.getIdSexe()!=0){
            if (demande.getIdSexe()==personne.getIdSexe()){
                return true;
            }
        }
        else {
            return true;
        }
        return false;
    }
    public List<Candidat> generateCandidatForDemande(Demande demande, Personne [] personnes, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        demande.setExperiences();
        List<Candidat> result = new ArrayList<Candidat>();
        for (Personne personne : personnes) {
            if (this.isSexeValide(demande,personne)) {
                Candidat candidat = new Candidat(personne);
                for (ExperienceDemande ed : demande.getExperiences()){
                    Experience e = new Experience().findByidPersonneAndidTalent(personne.getId(), ed.getIdTalent(), con);
                    if (e != null){
                        if (e.getDuree() >= ed.getDuree()){
                            candidat.getExperienceDemande().add(e);
                        }
                    }
                }
                if (candidat.getExperienceDemande().size() > 0){
                    result.add(candidat);
                }
            }
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

    // Fonction principale de tri fusion avec Bac
    public void triage(Candidat [] array) {
        if (array.length < 2) {
            return;
        }

        int mid = array.length / 2;
        Candidat[] left = new Candidat[mid];
        Candidat[] right = new Candidat[array.length - mid];

        // Diviser le tableau en deux moitiés
        System.arraycopy(array, 0, left, 0, mid);
        System.arraycopy(array, mid, right, 0, array.length - mid);

        // Tri récursif des deux moitiés
        triage(left);
        triage(right);
        // Fusion des deux moitiés triées
        merge(array, left, right);
    }

    // Fonction de fusion des deux sous-tableaux triés
    private void merge(Candidat [] array, Candidat[] left, Candidat[] right) {
        int i = 0, j = 0, k = 0;

        // Fusionner les deux sous-tableaux
        while (i < left.length && j < right.length) {
            if (left[i].getNote()<= right[j].getNote()) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        // Copier les éléments restants du tableau gauche (le cas échéant)
        while (i < left.length) {
            array[k++] = left[i++];
        }

        // Copier les éléments restants du tableau droit (le cas échéant)
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    public Question [] getQuestionnaire (Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        List<Question> result = new ArrayList<>();
        for (Experience experience : this.getExperienceDemande()) {
            Question [] questions = new Question().findByidTalent(experience.getIdTalent(),con);
            for (Question question : questions) {
                question.setReponses(con);
                result.add(question);
            }
        }
        if (con != null && new_connection){
            con.close();
        }
        return result.toArray(new Question[]{});
    }
}
