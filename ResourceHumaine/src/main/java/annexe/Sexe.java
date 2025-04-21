package annexe;

import connexion.Base;
import mg.Annotation.Colonne;
import mg.Annotation.Table;
import mg.Utils.Dao;
import personne.Personne;

import java.sql.Connection;
import java.sql.SQLException;

@Table
public class Sexe extends Dao {
    @Colonne(isPK = true)
    int id;
    @Colonne
    String intitule;

    public Sexe() {
    }

    public Sexe(int id, String intitule) {
        this.id = id;
        this.intitule = intitule;
    }

    public Sexe(String intitule) {
        this.intitule = intitule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Sexe findById(int id, Connection con) throws SQLException {
        boolean new_connection = false;
        if (con == null){
            con = Base.PsqlConnect();
            new_connection = true;
        }
        Sexe result = null;
        Sexe [] list = this.findAll("WHERE id="+id,con).toArray(new Sexe[]{});
        if (list.length > 0) {
            result = list[0];
        }
        if (con!=null && new_connection){
            con.close();
        }
        return result;
    }
}
