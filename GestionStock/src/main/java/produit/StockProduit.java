package produit;

import connexion.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockProduit {
    int id,idProduit;
    boolean isEntrer;
    double quantite;
    double prix_unitaire;
    Date date_modification;

    public StockProduit() {
    }

    public StockProduit(int id, int idProduit, boolean isEntrer, double quantite, double prix_unitaire, Date date_modification) {
        this.id = id;
        this.idProduit = idProduit;
        this.isEntrer = isEntrer;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.date_modification = date_modification;
    }

    public StockProduit(int idProduit, boolean isEntrer, double quantite, double prix_unitaire, Date date_modification) {
        this.idProduit = idProduit;
        this.isEntrer = isEntrer;
        this.quantite = quantite;
        this.prix_unitaire = prix_unitaire;
        this.date_modification = date_modification;
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

    public boolean isEntrer() {
        return isEntrer;
    }

    public void setEntrer(boolean entrer) {
        isEntrer = entrer;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public Date getDate_modification() {
        return date_modification;
    }

    public void setDate_modification(Date date_modification) {
        this.date_modification = date_modification;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO stock_produit (idProduit,isEntrer,quantite,prix_unitaire,date_modification) VALUES (?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setBoolean(2, isEntrer);
            st.setDouble(3, quantite);
            st.setDouble(4, prix_unitaire);
            st.setDate(5, date_modification);

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
            String sql = "UPDATE stock_produit SET quantite=? WHERE id=?";
            st = conn.prepareStatement(sql);
            st.setDouble(1, quantite);
            st.setInt(2, id);

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

    public StockProduit findById (int id, Connection con){
        StockProduit result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM stock_produit WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idProduit");
                boolean var3 = res.getBoolean("isEntrer");
                Double var4 = res.getDouble("quantite");
                Double var5 = res.getDouble("prix_unitaire");
                Date var6 = res.getDate("date_modification");
                result = new StockProduit(var1, var2, var3, var4, var5, var6);
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

    public StockProduit getTotalByidProduit (int idProduit,boolean isEntrer, Connection con){
        StockProduit result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT SUM(quantite) as somme FROM stock_produit WHERE idProduit=? AND isEntrer=?";
            st = con.prepareStatement(sql);
            st.setInt(1, idProduit);
            st.setBoolean(2, isEntrer);
            res = st.executeQuery();
            while(res.next()){
                double var1 = res.getDouble("somme");
                result = new StockProduit(idProduit, isEntrer, var1 , 0 , Date.valueOf("0001-01-01"));
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

    public StockProduit getDernierStock (int idProduit, Connection con) throws SQLException {
        StockProduit result = null;
        boolean newConnection = false;
        if (con == null) {
            con = Base.PsqlConnect();
            newConnection = true;
        }
//        StockProduit stockSortie = this.getTotalByidProduit(idProduit,false,con);
        StockProduit stockEntrer = this.getTotalByidProduit(idProduit,true,con);
//        double totalSortie = 0;
//        if (stockSortie != null){
//            totalSortie = stockSortie.getQuantite();
//        }
        double totalEntrer = 0;
        if (stockEntrer != null){
            totalEntrer = stockEntrer.getQuantite();
        }
//        double reste = totalEntrer - totalSortie;
        result = new StockProduit(idProduit, true, totalEntrer , 0, Date.valueOf("0001-01-01"));
        if (con != null && newConnection) con.close();

        return result;
    }

    public StockProduit [] findByIdProduitAndIsEntrer (int idProduit,boolean isEntrer, Connection con){
        List<StockProduit> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM stock_produit WHERE idproduit=? AND isEntrer=? AND quantite>0 ORDER BY date_modification ASC";
            st = con.prepareStatement(sql);
            st.setInt(1, idProduit);

            st.setBoolean(2, isEntrer);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idProduit");
                boolean var3 = res.getBoolean("isEntrer");
                Double var4 = res.getDouble("quantite");
                Double var5 = res.getDouble("prix_unitaire");
                Date var6 = res.getDate("date_modification");
                result.add(new StockProduit(var1, var2, var3, var4, var5, var6));
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
        return result.toArray(new StockProduit[]{});
    }
}
