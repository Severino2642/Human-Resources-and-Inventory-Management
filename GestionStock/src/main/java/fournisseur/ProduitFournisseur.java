package fournisseur;

import connexion.Base;
import produit.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitFournisseur {
    int id;
    int idFournisseur;
    int idProduit;
    double prix_unitaire;
    Date date_modif;
    Produit produit;

    public ProduitFournisseur() {
    }

    public ProduitFournisseur(int id, int idFournisseur, int idProduit, double prix_unitaire, Date date_modif) {
        this.id = id;
        this.idFournisseur = idFournisseur;
        this.idProduit = idProduit;
        this.prix_unitaire = prix_unitaire;
        this.date_modif = date_modif;
    }

    public ProduitFournisseur(int idFournisseur, int idProduit, double prix_unitaire, Date date_modif) {
        this.idFournisseur = idFournisseur;
        this.idProduit = idProduit;
        this.prix_unitaire = prix_unitaire;
        this.date_modif = date_modif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public Date getDate_modif() {
        return date_modif;
    }

    public void setDate_modif(Date date_modif) {
        this.date_modif = date_modif;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Connection con) throws SQLException {
        boolean newConnection = false;
        if (con == null) {
            con = Base.PsqlConnect();
            newConnection = true;
        }
        this.produit = new Produit().findById(this.getIdProduit(),con);
        if (con != null && newConnection) {
            con.close();
        }
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO produit_fournisseur (idfournisseur,idproduit, prix_unitaire, date_modif) VALUES (?,?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idFournisseur);
            st.setInt(2, idProduit);
            st.setDouble(3, prix_unitaire);
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

    public void deleteByIdProduitAndIdFournisseur(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "DELETE FROM produit_fournisseur WHERE idproduit=? AND idfournisseur=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setInt(2, idFournisseur);

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

    public ProduitFournisseur getProduitFournisseur (int idProduit,int idFournisseur, Connection con){
        ProduitFournisseur result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM produit_fournisseur WHERE idproduit=? AND idfournisseur=? ORDER BY id DESC LIMIT 1";
            st = con.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setInt(2, idFournisseur);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idfournisseur");
                int var3 = res.getInt("idproduit");
                double var4 = res.getDouble("prix_unitaire");
                Date var5 = res.getDate("date_modif");
                result = new ProduitFournisseur(var1, var2, var3, var4, var5);
                result.setProduit(con);
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

    public ProduitFournisseur [] findAllByIdProduitANDIdFournisseur (int idProduit,int idFournisseur, Connection con){
        List<ProduitFournisseur> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM produit_fournisseur WHERE idproduit=? AND idfournisseur=? ORDER BY id ASC";
            st = con.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setInt(2, idFournisseur);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idfournisseur");
                int var3 = res.getInt("idproduit");
                double var4 = res.getDouble("prix_unitaire");
                Date var5 = res.getDate("date_modif");
                ProduitFournisseur produitFournisseur = new ProduitFournisseur(var1, var2, var3, var4, var5);
                produitFournisseur.setProduit(con);
                result.add(produitFournisseur);
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
        return result.toArray(new ProduitFournisseur[]{});
    }

    public ProduitFournisseur [] findAllByIdFournisseur (int idFournisseur, Connection con){
        List<ProduitFournisseur> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM produit_fournisseur WHERE idfournisseur=? ORDER BY id ASC";
            st = con.prepareStatement(sql);
            st.setInt(1, idFournisseur);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idfournisseur");
                int var3 = res.getInt("idproduit");
                double var4 = res.getDouble("prix_unitaire");
                Date var5 = res.getDate("date_modif");
                ProduitFournisseur produitFournisseur = new ProduitFournisseur(var1, var2, var3, var4, var5);
                produitFournisseur.setProduit(con);
                result.add(produitFournisseur);
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
        return result.toArray(new ProduitFournisseur[]{});
    }

    public ProduitFournisseur [] findAllByIdProduit (int idProduit, Connection con){
        List<ProduitFournisseur> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM produit_fournisseur WHERE idproduit=? ORDER BY id ASC";
            st = con.prepareStatement(sql);
            st.setInt(1, idProduit);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idfournisseur");
                int var3 = res.getInt("idproduit");
                double var4 = res.getDouble("prix_unitaire");
                Date var5 = res.getDate("date_modif");
                ProduitFournisseur produitFournisseur = new ProduitFournisseur(var1, var2, var3, var4, var5);
                produitFournisseur.setProduit(con);
                result.add(produitFournisseur);
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
        return result.toArray(new ProduitFournisseur[]{});
    }
}
