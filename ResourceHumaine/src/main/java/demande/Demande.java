package demande;

import annexe.Sexe;
import connexion.Base;
import departement.Departement;
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
public class Demande extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    int idDepartement;
    @Colonne
    String intitule;
    @Colonne
    double quantite;
    @Colonne
    int idSexe;
    @Colonne
    Date date_demande;
    List<ExperienceDemande> experiences = new ArrayList<ExperienceDemande>();
    List<DemandeValidation> demandeValidations = new ArrayList<>();
    Departement departement;
    Sexe sexe;
    public Demande() {
    }

    public Demande(int id, int idDepartement, String intitule, double quantite, int idSexe, Date date_demande) {
        this.id = id;
        this.idDepartement = idDepartement;
        this.intitule = intitule;
        this.quantite = quantite;
        this.idSexe = idSexe;
        this.date_demande = date_demande;
    }

    public Demande(int idDepartement, String intitule, double quantite, int idSexe, Date date_demande) {
        this.idDepartement = idDepartement;
        this.intitule = intitule;
        this.quantite = quantite;
        this.idSexe = idSexe;
        this.date_demande = date_demande;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public int getIdSexe() {
        return idSexe;
    }

    public void setIdSexe(int idSexe) {
        this.idSexe = idSexe;
    }

    public Date getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public List<ExperienceDemande> getExperiences() {
        return experiences;
    }

    public void setExperiences() {
        try {
            Connection conn = Base.PsqlConnect();
            this.experiences = List.of(new ExperienceDemande().findByidDemande(this.getId(),conn));
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DemandeValidation> getDemandeValidations() {
        return demandeValidations;
    }

    public void setDemandeValidations() {
        try {
            Connection conn = Base.PsqlConnect();
            this.demandeValidations = List.of(new DemandeValidation().findByidDemande(this.getId(),conn));
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setExperiences(List<ExperienceDemande> experiences) {
        this.experiences = experiences;
    }

    public void setDemandeValidations(List<DemandeValidation> demandeValidations) {
        this.demandeValidations = demandeValidations;
    }

    public Departement getDepartement() {
        if (departement == null) {
            this.setDepartement();
        }
        return departement;
    }

    public void setDepartement() {
        try {
            Connection conn = Base.PsqlConnect();
            this.departement = new Departement().findById(this.getIdDepartement(),conn);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Sexe getSexe() {
        if (this.sexe == null) {
            if (this.getIdSexe() != 0){
                this.setSexe();
            }
            else {
                this.setSexe(new Sexe("Homme et Femme"));
            }
        }
        return this.sexe;
    }

    public void setSexe() {
        try {
            Connection conn = Base.PsqlConnect();
            this.sexe = new Sexe().findById(this.getIdSexe(),conn);
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public boolean isValid() {
        if (this.getQuantite() == this.getDemandeValidations().size()){
            return true;
        }
        return false;
    }

    public String getValidation() {
        String validation = "en attente";
        if (this.isValid()){
            validation = "valider";
        }
        return validation;
    }

    public ExperienceDemande verifExperienceByidTalent(int idTalent) {
        ExperienceDemande result = null;
        for (ExperienceDemande experience : this.getExperiences()) {
            if (experience.getIdTalent() == idTalent) {
                result = experience;
                break;
            }
        }
        return result;
    }

    public Demande[] findByidDepartement (int idDepartement, Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        Demande [] result = this.findAll("WHERE idDepartement="+idDepartement,con).toArray(new Demande[]{});

        if(con!=null && new_con){
            con.close();
        }
        return result;
    }

    public Demande findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Demande result = null;
        Demande [] list = this.findAll("WHERE id="+id,con).toArray(new Demande[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

    public Demande findLast( Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Demande result = null;
        Demande [] list = this.findAll("ORDER BY id DESC",con).toArray(new Demande[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }

    public Demande[] getDemandeByValide (boolean isValid,Connection con) throws SQLException {
        boolean new_con = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_con = true;
        }
        List<Demande> result = new ArrayList<>();
        Demande [] list = this.findAll("ORDER BY id ASC",con).toArray(new Demande[]{});
        for (Demande demande : list){
            DemandeValidation [] dv = new DemandeValidation().findAll("WHERE idDemande="+demande.getId(),con).toArray(new DemandeValidation[]{});
            demande.setDemandeValidations(List.of(dv));
            if (demande.isValid() == isValid){
                result.add(demande);
            }
        }

        if(con!=null && new_con){
            con.close();
        }
        return result.toArray(new Demande[]{});
    }
}
