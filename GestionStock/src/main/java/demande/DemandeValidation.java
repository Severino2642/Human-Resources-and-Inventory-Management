package demande;

import connexion.Base;

import java.sql.*;

public class DemandeValidation {
    int id;
    int idDemande;
    double quantite;
    boolean isValid;
    Date date_validation;

    public DemandeValidation() {
    }

    public DemandeValidation(int idDemande, double quantite, boolean isValid, Date date_validation) {
        this.idDemande = idDemande;
        this.quantite = quantite;
        this.isValid = isValid;
        this.date_validation = date_validation;
    }

    public DemandeValidation(int id, int idDemande, double quantite, boolean isValid, Date date_validation) {
        this.id = id;
        this.idDemande = idDemande;
        this.quantite = quantite;
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

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
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
            String sql = "INSERT INTO demande_validation ( iddemande, quantite, isvalide, date_validation) VALUES ( ?,?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idDemande);
            st.setDouble(2, quantite);
            st.setBoolean(3, isValid);
            st.setDate(4, date_validation);

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

    public DemandeValidation findByIdDemande (int idDemande, Connection con){
        DemandeValidation result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM demande_validation WHERE iddemande=?";
            st = con.prepareStatement(sql);
            st.setInt(1, idDemande);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("iddemande");
                double var3 = res.getDouble("quantite");
                boolean var4 = res.getBoolean("isvalide");
                Date var5 = res.getDate("date_validation");
                result = new DemandeValidation(var1, var2, var3, var4, var5);
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
