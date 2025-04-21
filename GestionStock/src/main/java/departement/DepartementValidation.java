package departement;

import connexion.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementValidation {
    int id;
    int idDemande;
    int idDepartement;
    double valeur;
    boolean isValid;
    Date date_validation;

    public DepartementValidation() {
    }

    public DepartementValidation(int idDemande, int idDepartement, double valeur, boolean isValid, Date date_validation) {
        this.idDemande = idDemande;
        this.idDepartement = idDepartement;
        this.valeur = valeur;
        this.isValid = isValid;
        this.date_validation = date_validation;
    }

    public DepartementValidation(int id, int idDemande, int idDepartement, double valeur, boolean isValid, Date date_validation) {
        this.id = id;
        this.idDemande = idDemande;
        this.idDepartement = idDepartement;
        this.valeur = valeur;
        this.isValid = isValid;
        this.date_validation = date_validation;
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

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Date getDate_validation() {
        return date_validation;
    }

    public void setDate_validation(Date date_validation) {
        this.date_validation = date_validation;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO departement_validation ( iddemande, iddepartement, valeur, isvalide, date_validation) VALUES ( ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idDemande);
            st.setInt(2, idDepartement);
            st.setDouble(3, valeur);
            st.setBoolean(4, isValid);
            st.setDate(5, date_validation);
            st.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (conn != null && newConnection) conn.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
    }

    public void deleteByidDemande(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "DELETE FROM departement_validation WHERE iddemande = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, idDemande);
            st.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (conn != null && newConnection) conn.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
    }

    public void updateByIdDemandeAndIdDepartement(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "UPDATE departement_validation SET isvalide=? WHERE iddemande=? AND iddepartement=?";
            st = conn.prepareStatement(sql);
            st.setBoolean(1, isValid);
            st.setInt(2, idDemande);
            st.setInt(3, idDepartement);
            st.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (conn != null && newConnection) conn.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
    }

    public DepartementValidation [] findByIdDepartement (int idDepartement, boolean isValid, Connection con){
        List<DepartementValidation> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement_validation WHERE iddepartement=? AND isvalide=?";
            st = con.prepareStatement(sql);
            st.setInt(1, idDepartement);
            st.setBoolean(2, isValid);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idDemande");
                int var3 = res.getInt("iddepartement");
                double var4 = res.getDouble("valeur");
                boolean var5 = res.getBoolean("isvalide");
                Date var6 = res.getDate("date_validation");
                result.add(new DepartementValidation(var1, var2, var3, var4, var5,var6));
            }
        }catch (SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (con != null && newConnection) con.close();
                if (res != null) res.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        return result.toArray(new DepartementValidation[]{});
    }

    public DepartementValidation [] findByIdDemande (int idDemande, boolean isValid, Connection con){
        List<DepartementValidation> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement_validation WHERE iddemande=? AND isvalide=?";
            st = con.prepareStatement(sql);
            st.setInt(1, idDemande);
            st.setBoolean(2, isValid);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idDemande");
                int var3 = res.getInt("iddepartement");
                double var4 = res.getDouble("valeur");
                boolean var5 = res.getBoolean("isvalide");
                Date var6 = res.getDate("date_validation");
                result.add(new DepartementValidation(var1, var2, var3, var4, var5,var6));
            }
        }catch (SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (con != null && newConnection) con.close();
                if (res != null) res.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        return result.toArray(new DepartementValidation[]{});
    }

    public DepartementValidation findByIdDemandeAndIdDepartementAndIsValide (int idDemande,int idDepartement, boolean isValid, Connection con){
        DepartementValidation result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement_validation WHERE iddemande=? AND iddepartement=? AND isvalide=?";
            st = con.prepareStatement(sql);
            st.setInt(1, idDemande);
            st.setInt(2, idDepartement);
            st.setBoolean(3, isValid);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idDemande");
                int var3 = res.getInt("iddepartement");
                double var4 = res.getDouble("valeur");
                boolean var5 = res.getBoolean("isvalide");
                Date var6 = res.getDate("date_validation");
                result = new DepartementValidation(var1, var2, var3, var4, var5,var6);
            }
        }catch (SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (con != null && newConnection) con.close();
                if (res != null) res.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        return result;
    }

    public DepartementValidation findByIdDemandeAndIdDepartement (int idDemande,int idDepartement, Connection con){
        DepartementValidation result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement_validation WHERE iddemande=? AND iddepartement=?";
            st = con.prepareStatement(sql);
            st.setInt(1, idDemande);
            st.setInt(2, idDepartement);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idDemande");
                int var3 = res.getInt("iddepartement");
                double var4 = res.getDouble("valeur");
                boolean var5 = res.getBoolean("isvalide");
                Date var6 = res.getDate("date_validation");
                result = new DepartementValidation(var1, var2, var3, var4, var5,var6);
            }
        }catch (SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
        finally {
            try {
                if (st != null) st.close();
                if (con != null && newConnection) con.close();
                if (res != null) res.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        return result;
    }
}
