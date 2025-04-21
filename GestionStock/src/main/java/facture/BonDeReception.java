package facture;

import connexion.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BonDeReception {
    int id;
    int idBonDeLivraison;
    double quantite;
    double montant;
    Date date_recu;

    public BonDeReception() {
    }

    public BonDeReception(int id, int idBonDeLivraison, double quantite, double montant, Date date_recu) {
        this.id = id;
        this.idBonDeLivraison = idBonDeLivraison;
        this.quantite = quantite;
        this.montant = montant;
        this.date_recu = date_recu;
    }

    public BonDeReception(int idBonDeLivraison, double quantite, double montant, Date date_recu) {
        this.idBonDeLivraison = idBonDeLivraison;
        this.quantite = quantite;
        this.montant = montant;
        this.date_recu = date_recu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBonDeLivraison() {
        return idBonDeLivraison;
    }

    public void setIdBonDeLivraison(int idBonDeLivraison) {
        this.idBonDeLivraison = idBonDeLivraison;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDate_recu() {
        return date_recu;
    }

    public void setDate_recu(Date date_recu) {
        this.date_recu = date_recu;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO bon_reception (idbonlivraison, quantite, montant, date_recu) VALUES (?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idBonDeLivraison);
            st.setDouble(2, quantite);
            st.setDouble(3, montant);
            st.setDate(4, date_recu);
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

    public BonDeReception [] findAll (Connection con){
        List<BonDeReception> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM bon_reception ORDER BY id DESC";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idbonlivraison");
                double var3 = res.getDouble("quantite");
                double var4 = res.getDouble("montant");
                Date var5 = res.getDate("date_recu");
                result.add(new BonDeReception(var1, var2, var3, var4, var5));
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
        return result.toArray(new BonDeReception[]{});
    }
}
