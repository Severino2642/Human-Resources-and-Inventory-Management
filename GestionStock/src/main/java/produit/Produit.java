package produit;

import annexe.GestionStock;
import connexion.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Produit {
    int id;
    int idGestionStock;
    String nom;
    GestionStock gestionStock;
    PrixProduit prixAchat;
    PrixProduit prixVente;
    StockProduit stock;

    public Produit() {
    }

    public Produit(int id, int idGestionStock, String nom) {
        this.id = id;
        this.idGestionStock = idGestionStock;
        this.nom = nom;
        this.gestionStock = new GestionStock().findById(idGestionStock,null);
        this.setPrixAchat();
        this.setPrixVente();
    }

    public Produit(int idGestionStock, String nom) {
        this.idGestionStock = idGestionStock;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGestionStock() {
        return idGestionStock;
    }

    public void setIdGestionStock(int idGestionStock) {
        this.idGestionStock = idGestionStock;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public GestionStock getGestionStock() {
        return gestionStock;
    }

    public void setGestionStock(GestionStock gestionStock) {
        this.gestionStock = gestionStock;
    }

    public PrixProduit getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat() {
        PrixProduit prixAchat = new PrixProduit().getPrixByidProduit(this.getId(),false,null);
        if (prixAchat != null) {
            this.prixAchat = prixAchat;
        }
        else {
            this.prixAchat = new PrixProduit(this.getId(),false,0.0,Date.valueOf("0001-01-01"));
        }
    }

    public PrixProduit getPrixVente() {
        return prixVente;
    }

    public void setPrixVente() {
        PrixProduit prixVente = new PrixProduit().getPrixByidProduit(this.getId(),true,null);
        if (prixVente != null) {
            this.prixVente = prixVente;
        }
        else {
            this.prixVente = new PrixProduit(this.getId(),true,0.0,Date.valueOf("0001-01-01"));
        }
    }

    public StockProduit getStock() {
        return stock;
    }

    public void setStock() throws SQLException {
        this.stock = new StockProduit().getDernierStock(this.getId(),null);
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO produit (idgestionstock, nom) VALUES (?,?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idGestionStock);
            st.setString(2, nom);
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
            String sql = "DELETE FROM produit WHERE id = ?";
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

    public Produit findById (int id, Connection con){
        Produit result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM produit WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idgestionstock");
                String var3 = res.getString("nom");
                result = new Produit(var1, var2, var3);
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

    public Produit [] findAll ( Connection con){
        List<Produit> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM produit";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idgestionstock");
                String var3 = res.getString("nom");
                result.add(new Produit(var1, var2, var3));
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
        return result.toArray(new Produit[]{});
    }

    public double getPrixMoyenne (StockProduit [] stockProduits){
        double result = 0;
        double montant_total = 0;
        double quantite_total = 0;
        for (StockProduit sp: stockProduits){
            montant_total += (sp.getQuantite()* sp.getPrix_unitaire());
            quantite_total += sp.getQuantite();
        }
        result = montant_total / quantite_total;
        return result;
    }

    public Double [] getValeur (StockProduit [] stockEntrer,StockProduit [] stockSortie){
        List<Double> result = new ArrayList<>();
        double montant_total = 0;
        double totalEntrer = 0;
        double totalSortie = 0;
        for (StockProduit sp: stockEntrer){
            totalEntrer += sp.getQuantite();
        }
        for (StockProduit sp: stockSortie){
            totalSortie += sp.getQuantite();
        }

        if (this.getGestionStock().getNom().compareToIgnoreCase("CMUP")==0){
            montant_total = this.getPrixMoyenne(stockEntrer);
        }
        if (this.getGestionStock().getNom().compareToIgnoreCase("FIFO")==0){
            if (stockEntrer.length > 0){
                montant_total = stockEntrer[0].getPrix_unitaire();
            }
        }
        if (this.getGestionStock().getNom().compareToIgnoreCase("LIFO")==0){
            if (stockEntrer.length > 0){
                montant_total = stockEntrer[stockEntrer.length-1].getPrix_unitaire();
            }
        }
        result.add(montant_total);
        result.add(totalEntrer);
        result.add(totalSortie);
        return result.toArray(new Double[]{});
    }

    public void methodCMUP(Date date,double quantite,StockProduit [] stockEntrer,Connection con){
        for (int i=0; i<stockEntrer.length; i++){
            double reste = stockEntrer[i].getQuantite()-quantite;
            double prix_unitaire = this.getPrixMoyenne(stockEntrer);
            if (quantite > 0){
                if (reste >= 0){
                    stockEntrer[i].setQuantite(reste);
                    stockEntrer[i].update(con);
                    new StockProduit(this.getId(),false,quantite,prix_unitaire,date).insert(con);
                    quantite = 0;
                }
                else {
                    new StockProduit(this.getId(),false,stockEntrer[i].getQuantite(),prix_unitaire,date).insert(con);
                    stockEntrer[i].setQuantite(0);
                    stockEntrer[i].update(con);
                    quantite = reste*-1;
                }
            }
        }
    }

    public void methodLIFO(Date date,double quantite,StockProduit [] stockEntrer,Connection con){
        for (int i=stockEntrer.length-1; i>=0; i--){
            double reste = stockEntrer[i].getQuantite()-quantite;
            double prix_unitaire = stockEntrer[i].getPrix_unitaire();
            if (quantite > 0){
                if (reste >= 0){
                    stockEntrer[i].setQuantite(reste);
                    stockEntrer[i].update(con);
                    new StockProduit(this.getId(),false,quantite,prix_unitaire,date).insert(con);
                    quantite = 0;
                }
                else {
                    new StockProduit(this.getId(),false,stockEntrer[i].getQuantite(),prix_unitaire,date).insert(con);
                    stockEntrer[i].setQuantite(0);
                    stockEntrer[i].update(con);
                    quantite = reste*-1;
                }
            }
        }
    }

    public void methodFIFO(Date date,double quantite,StockProduit [] stockEntrer,Connection con){
        for (int i=0; i<stockEntrer.length; i++){
            double reste = stockEntrer[i].getQuantite()-quantite;
            double prix_unitaire = stockEntrer[i].getPrix_unitaire();
            if (quantite > 0){
                if (reste >= 0){
                    stockEntrer[i].setQuantite(reste);
                    stockEntrer[i].update(con);
                    new StockProduit(this.getId(),false,quantite,prix_unitaire,date).insert(con);
                    quantite = 0;
                }
                else {
                    new StockProduit(this.getId(),false,stockEntrer[i].getQuantite(),prix_unitaire,date).insert(con);
                    stockEntrer[i].setQuantite(0);
                    stockEntrer[i].update(con);
                    quantite = reste*-1;
                }
            }
        }
    }

    public void sortieStock(Date date,double quantite,Connection con) throws SQLException {
        boolean newConnection = false;
        if (con == null) {
            con = Base.PsqlConnect();
            newConnection = true;
        }
        StockProduit [] stockEntrer = new StockProduit().findByIdProduitAndIsEntrer(this.getId(),true,con);

        if (this.getGestionStock().getNom().compareToIgnoreCase("CMUP")==0){
            this.methodCMUP(date,quantite,stockEntrer,con);
        }
        if (this.getGestionStock().getNom().compareToIgnoreCase("FIFO")==0){
            this.methodFIFO(date,quantite,stockEntrer,con);
        }
        if (this.getGestionStock().getNom().compareToIgnoreCase("LIFO")==0){
            this.methodLIFO(date,quantite,stockEntrer,con);
        }
        if (con!=null && newConnection){
            con.close();
        }
    }
}
