package fournisseur;

import connexion.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Incident {
    int id;
    int idFournisseur;
    String remarque;
    Date date_incident;

    public Incident() {
    }

    public Incident(int idFournisseur, String remarque, Date date_incident) {
        this.idFournisseur = idFournisseur;
        this.remarque = remarque;
        this.date_incident = date_incident;
    }

    public Incident(int id, int idFournisseur, String remarque, Date date_incident) {
        this.id = id;
        this.idFournisseur = idFournisseur;
        this.remarque = remarque;
        this.date_incident = date_incident;
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

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Date getDate_incident() {
        return date_incident;
    }

    public void setDate_incident(Date date_incident) {
        this.date_incident = date_incident;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO incident (idfournisseur, remarque, date_incident) VALUES ( ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idFournisseur);
            st.setString(2, remarque);
            st.setDate(3, date_incident);
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

    public Incident [] findAll (Connection con){
        List<Incident> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM incident";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idfournisseur");
                String var3 = res.getString("remarque");
                Date var4 = res.getDate("date_incident");
                result.add(new Incident(var1, var2, var3, var4));
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
        return result.toArray(new Incident[]{});
    }

    public Incident [] findByIdFournisseur (int idFournisseur,Connection con){
        List<Incident> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM incident WHERE idfournisseur = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, idFournisseur);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idfournisseur");
                String var3 = res.getString("remarque");
                Date var4 = res.getDate("date_incident");
                result.add(new Incident(var1, var2, var3, var4));
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
        return result.toArray(new Incident[]{});
    }
}
