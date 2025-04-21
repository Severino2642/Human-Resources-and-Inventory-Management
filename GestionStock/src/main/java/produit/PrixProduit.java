package produit;

import connexion.Base;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrixProduit{
    int id;
    int idProduit;
    boolean isVente;
    double valeur;
    Date date_modif;

    public PrixProduit() {
    }

    public PrixProduit(int id, int idProduit, boolean isVente, double valeur, Date date_modif) {
        this.id = id;
        this.idProduit = idProduit;
        this.isVente = isVente;
        this.valeur = valeur;
        this.date_modif = date_modif;
    }

    public PrixProduit(int idProduit, boolean isVente, double valeur, Date date_modif) {
        this.idProduit = idProduit;
        this.isVente = isVente;
        this.valeur = valeur;
        this.date_modif = date_modif;
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

    public boolean isVente() {
        return isVente;
    }

    public void setVente(boolean vente) {
        isVente = vente;
    }

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }

    public Date getDate_modif() {
        return date_modif;
    }

    public void setDate_modif(Date date_modif) {
        this.date_modif = date_modif;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO prix_produit (idProduit,isVente,valeur,date_modif) VALUES (?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setBoolean(2, isVente);
            st.setDouble(3, valeur);
            st.setDate(4, date_modif);
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

    public PrixProduit getPrixByidProduit (int idProduit, boolean isVente, Connection con){
        PrixProduit result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM prix_produit WHERE idProduit=? AND isVente=? ORDER BY id DESC LIMIT 1";
            st = con.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setBoolean(2, isVente);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idProduit");
                boolean var3 = res.getBoolean("isVente");
                double var4 = res.getDouble("valeur");
                Date var5 = res.getDate("date_modif");
                result = new PrixProduit(var1,var2,var3,var4,var5);
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

    public PrixProduit [] getAllPrixByidProduit (int idProduit, boolean isVente, Connection con){
        List<PrixProduit> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM prix_produit WHERE idProduit=? AND isVente=? ORDER BY id ASC";
            st = con.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setBoolean(2, isVente);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idProduit");
                boolean var3 = res.getBoolean("isVente");
                double var4 = res.getDouble("valeur");
                Date var5 = res.getDate("date_modif");
                result.add(new PrixProduit(var1,var2,var3,var4,var5));
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
        return result.toArray(new PrixProduit[]{});
    }
}

