package fournisseur;

import connexion.Base;
import produit.Produit;
import user.Employer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fournisseur {
    int id;
    String nom;
    Date date_ajout;
    List<ProduitFournisseur> produitFournisseurs;

    public Fournisseur() {
    }

    public Fournisseur(int id, String nom, Date date_ajout) {
        this.id = id;
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public Fournisseur(String nom, Date date_ajout) {
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public List<ProduitFournisseur> getProduitFournisseurs() {
        return produitFournisseurs;
    }

    public void setProduitFournisseurs(Connection con) throws SQLException {
        boolean newConnection = false;
        if (con == null) {
            con = Base.PsqlConnect();
            newConnection = true;
        }
        ProduitFournisseur [] produitFournisseurs = new ProduitFournisseur().findAllByIdFournisseur(this.getId(),con);
        this.produitFournisseurs = List.of(produitFournisseurs);
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
            String sql = "INSERT INTO fournisseur (nom, date_ajout) VALUES (?, ?) ";
            st = conn.prepareStatement(sql);
            st.setString(1, nom);
            st.setDate(2, date_ajout);
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
    public void delete(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "DELETE FROM fournisseur WHERE id=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, id);
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

    public Fournisseur findById (int id, Connection con){
        Fournisseur result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM fournisseur WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                Date var3 = res.getDate("date_ajout");
                result = new Fournisseur(var1,var2,var3);
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

    public Fournisseur [] findAll (Connection con){
        List<Fournisseur> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM fournisseur";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                Date var3 = res.getDate("date_ajout");
                result.add(new Fournisseur(var1,var2,var3));
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
        return result.toArray(new Fournisseur[]{});
    }
}
