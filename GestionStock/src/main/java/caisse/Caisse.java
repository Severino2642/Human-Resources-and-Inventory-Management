package caisse;

import connexion.Base;
import produit.StockProduit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Caisse {
    int id;
    boolean isEntrer;
    double montant;
    Date date_modif;

    public Caisse() {
    }

    public Caisse(int id, boolean isEntrer, double montant, Date date_modif) {
        this.id = id;
        this.isEntrer = isEntrer;
        this.montant = montant;
        this.date_modif = date_modif;
    }

    public Caisse(boolean isEntrer, double montant, Date date_modif) {
        this.isEntrer = isEntrer;
        this.montant = montant;
        this.date_modif = date_modif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEntrer() {
        return isEntrer;
    }

    public void setEntrer(boolean entrer) {
        isEntrer = entrer;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
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
            String sql = "INSERT INTO caisse (isentrer, montant, date_modif) VALUES (?,?,?) ";
            st = conn.prepareStatement(sql);
            st.setBoolean(1, isEntrer);
            st.setDouble(2, montant);
            st.setDate(3, date_modif);

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

    public Caisse [] getEtatDeCaisse (boolean isEntrer, Connection con){
        List<Caisse> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM caisse WHERE isentrer = ? ";
            st = con.prepareStatement(sql);
            st.setBoolean(1, isEntrer);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                boolean var2 = res.getBoolean("isentrer");
                int var3 = res.getInt("montant");
                Date var4 = res.getDate("date_modif");
                result.add(new Caisse(var1,var2,var3,var4));

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
        return result.toArray(new Caisse[]{});
    }

    public Caisse getTotal (boolean isEntrer, Connection con){
        Caisse result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT SUM(montant) as somme FROM caisse WHERE isentrer = ? ";
            st = con.prepareStatement(sql);
            st.setBoolean(1, isEntrer);
            res = st.executeQuery();
            while(res.next()){
                double var1 = res.getDouble("somme");
                result = new Caisse( isEntrer, var1 , Date.valueOf("0001-01-01"));
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

    public Caisse getDernierCaisse (Connection con) throws SQLException {
        Caisse result = null;
        boolean newConnection = false;
        if (con == null) {
            con = Base.PsqlConnect();
            newConnection = true;
        }
        Caisse caisseSortie = this.getTotal(false,con);
        Caisse caisseEntrer = this.getTotal(true,con);
        double totalSortie = 0;
        if (caisseSortie != null){
            totalSortie = caisseSortie.getMontant();
        }
        double totalEntrer = 0;
        if (caisseEntrer != null){
            totalEntrer = caisseEntrer.getMontant();
        }
        double reste = totalEntrer - totalSortie;
        result = new Caisse( true, reste, Date.valueOf("0001-01-01"));
        if (con != null && newConnection) con.close();

        return result;
    }


}
