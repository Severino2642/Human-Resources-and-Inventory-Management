package facture;

import connexion.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BonDeLivraison {
    int id;
    int idBonDeCommande;
    double quantite;
    double montant;
    Date date_livraison;

    public BonDeLivraison() {
    }

    public BonDeLivraison(int id, int idBonDeCommande, double quantite, double montant, Date date_livraison) {
        this.id = id;
        this.idBonDeCommande = idBonDeCommande;
        this.quantite = quantite;
        this.montant = montant;
        this.date_livraison = date_livraison;
    }

    public BonDeLivraison(int idBonDeCommande, double quantite, double montant, Date date_livraison) {
        this.idBonDeCommande = idBonDeCommande;
        this.quantite = quantite;
        this.montant = montant;
        this.date_livraison = date_livraison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBonDeCommande() {
        return idBonDeCommande;
    }

    public void setIdBonDeCommande(int idBonDeCommande) {
        this.idBonDeCommande = idBonDeCommande;
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

    public Date getDate_livraison() {
        return date_livraison;
    }

    public void setDate_livraison(Date date_livraison) {
        this.date_livraison = date_livraison;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO bon_livraison (idboncommande, quantite, montant, date_livraison) VALUES (?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idBonDeCommande);
            st.setDouble(2, quantite);
            st.setDouble(3, montant);
            st.setDate(4, date_livraison);
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

    public BonDeLivraison findById (int id, Connection con){
        BonDeLivraison result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM bon_livraison WHERE id = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idboncommande");
                double var3 = res.getDouble("quantite");
                double var4 = res.getDouble("montant");
                Date var5 = res.getDate("date_livraison");
                result = new BonDeLivraison(var1,var2,var3,var4,var5);
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

    public BonDeLivraison [] findAllByIdFournisseur (int idFournisseur, Connection con){
        List<BonDeLivraison> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM bon_livraison WHERE idboncommande IN (SELECT id FROM bon_commande WHERE idfournisseur = ? AND isvalide = true)";
            st = con.prepareStatement(sql);
            st.setInt(1, idFournisseur);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idboncommande");
                double var3 = res.getDouble("quantite");
                double var4 = res.getDouble("montant");
                Date var5 = res.getDate("date_livraison");
                result.add(new BonDeLivraison(var1,var2,var3,var4,var5));
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
        return result.toArray(new BonDeLivraison[]{});
    }

    public BonDeLivraison [] findAllNoRecu (Connection con){
        List<BonDeLivraison> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM bon_livraison WHERE id NOT IN (SELECT idbonlivraison FROM bon_reception)";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idboncommande");
                double var3 = res.getDouble("quantite");
                double var4 = res.getDouble("montant");
                Date var5 = res.getDate("date_livraison");
                result.add(new BonDeLivraison(var1,var2,var3,var4,var5));
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
        return result.toArray(new BonDeLivraison[]{});
    }


}
