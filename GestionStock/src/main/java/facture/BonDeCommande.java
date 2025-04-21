package facture;

import connexion.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BonDeCommande {
    int id;
    int idProduit;
    int idFournisseur;
    double quantite;
    double montant;
    boolean isValide;
    Date date_commande;
    Date date_besoin;

    public BonDeCommande() {
    }

    public BonDeCommande(int id, int idProduit, int idFournisseur, double quantite, double montant, boolean isValide, Date date_commande, Date date_besoin) {
        this.id = id;
        this.idProduit = idProduit;
        this.idFournisseur = idFournisseur;
        this.quantite = quantite;
        this.montant = montant;
        this.isValide = isValide;
        this.date_commande = date_commande;
        this.date_besoin = date_besoin;
    }

    public BonDeCommande(int idProduit, int idFournisseur, double quantite, double montant, boolean isValide, Date date_commande, Date date_besoin) {
        this.idProduit = idProduit;
        this.idFournisseur = idFournisseur;
        this.quantite = quantite;
        this.montant = montant;
        this.isValide = isValide;
        this.date_commande = date_commande;
        this.date_besoin = date_besoin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
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

    public boolean isValide() {
        return isValide;
    }

    public void setValide(boolean valide) {
        isValide = valide;
    }

    public Date getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(Date date_commande) {
        this.date_commande = date_commande;
    }

    public Date getDate_besoin() {
        return date_besoin;
    }

    public void setDate_besoin(Date date_besoin) {
        this.date_besoin = date_besoin;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO bon_commande ( idproduit, idfournisseur, quantite, montant, isvalide, date_commande, date_besoin) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setInt(2, idFournisseur);
            st.setDouble(3, quantite);
            st.setDouble(4, montant);
            st.setBoolean(5, isValide);
            st.setDate(6, date_commande);
            st.setDate(7, date_besoin);
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

    public void update(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "UPDATE bon_commande SET isvalide = ?,date_commande=?,date_besoin=? WHERE id=?";
            st = conn.prepareStatement(sql);
            st.setBoolean(1, isValide);
            st.setDate(2, date_commande);
            st.setDate(3, date_besoin);
            st.setInt(4, id);
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

    public BonDeCommande findById (int id, Connection con){
        BonDeCommande result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM bon_commande WHERE id = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idproduit");
                int var3 = res.getInt("idfournisseur");
                double var4 = res.getDouble("quantite");
                double var5 = res.getDouble("montant");
                boolean var6 = res.getBoolean("isvalide");
                Date var7 = res.getDate("date_commande");
                Date var8 = res.getDate("date_besoin");
                result = new BonDeCommande(var1,var2,var3,var4,var5,var6,var7,var8);
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

    public BonDeCommande [] findAllByIsValide (boolean isValide, Connection con){
        List<BonDeCommande> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM bon_commande WHERE isvalide = ?";
            st = con.prepareStatement(sql);
            st.setBoolean(1, isValide);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idproduit");
                int var3 = res.getInt("idfournisseur");
                double var4 = res.getDouble("quantite");
                double var5 = res.getDouble("montant");
                boolean var6 = res.getBoolean("isvalide");
                Date var7 = res.getDate("date_commande");
                Date var8 = res.getDate("date_besoin");
                result.add(new BonDeCommande(var1,var2,var3,var4,var5,var6,var7,var8));
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
        return result.toArray(new BonDeCommande[]{});
    }

    public BonDeCommande [] findAllByIsValideAndIdFournisseur (int idFournisseur,boolean isValide, Connection con){
        List<BonDeCommande> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM bon_commande WHERE isvalide = ? AND idfournisseur = ?";
            st = con.prepareStatement(sql);
            st.setBoolean(1, isValide);
            st.setInt(2, idFournisseur);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idproduit");
                int var3 = res.getInt("idfournisseur");
                double var4 = res.getDouble("quantite");
                double var5 = res.getDouble("montant");
                boolean var6 = res.getBoolean("isvalide");
                Date var7 = res.getDate("date_commande");
                Date var8 = res.getDate("date_besoin");
                result.add(new BonDeCommande(var1,var2,var3,var4,var5,var6,var7,var8));
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
        return result.toArray(new BonDeCommande[]{});
    }

    public BonDeCommande [] getBonDeCommandeNonLivrer (int idFournisseur,boolean isValide, Connection con){
        List<BonDeCommande> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM bon_commande WHERE isvalide = ? AND idfournisseur = ? AND id NOT IN (SELECT idboncommande FROM bon_livraison)";
            st = con.prepareStatement(sql);
            st.setBoolean(1, isValide);
            st.setInt(2, idFournisseur);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idproduit");
                int var3 = res.getInt("idfournisseur");
                double var4 = res.getDouble("quantite");
                double var5 = res.getDouble("montant");
                boolean var6 = res.getBoolean("isvalide");
                Date var7 = res.getDate("date_commande");
                Date var8 = res.getDate("date_besoin");
                result.add(new BonDeCommande(var1,var2,var3,var4,var5,var6,var7,var8));
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
        return result.toArray(new BonDeCommande[]{});
    }
}
