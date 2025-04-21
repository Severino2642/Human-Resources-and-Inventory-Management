package user;

import annexe.Status;
import connexion.Base;
import departement.Departement;
import departement.DepartementEmployer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Employer {
    int id;
    String nom;
    String email;
    Date date_embauche;
    Status status;
    DepartementEmployer departement_employer;

    public Employer() {
    }

    public Employer(int id, String nom, String email, Date date_embauche) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.date_embauche = date_embauche;
    }

    public Employer(String nom, String email, Date date_embauche) {
        this.nom = nom;
        this.email = email;
        this.date_embauche = date_embauche;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate_embauche() {
        return date_embauche;
    }

    public void setDate_embauche(Date date_embauche) {
        this.date_embauche = date_embauche;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public DepartementEmployer getDepartement_employer() {
        return departement_employer;
    }

    public void setDepartement_employer(DepartementEmployer departement_employer) {
        this.departement_employer = departement_employer;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO employer (nom, email, date_embauche) VALUES (?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setString(1, nom);
            st.setString(2, email);
            st.setDate(3, date_embauche);
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

    public Employer findById (int id, Connection con){
        Employer result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM employer WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                String var3 = res.getString("email");
                Date var4 = res.getDate("date_embauche");
                result = new Employer(var1, var2, var3, var4);
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

    public Employer [] findAll (Connection con){
        List<Employer> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM employer";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                String var3 = res.getString("email");
                Date var4 = res.getDate("date_embauche");
                result.add(new Employer(var1, var2, var3, var4));
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
        return result.toArray(new Employer[]{});
    }

    public Employer [] getEmployerNoRecruter (int idDepartement,Connection con){
        List<Employer> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM employer WHERE id NOT IN (SELECT de.idemployer FROM departement_employer de WHERE iddepartement=?)";
            st = con.prepareStatement(sql);
            st.setInt(1, idDepartement);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                String var3 = res.getString("email");
                Date var4 = res.getDate("date_embauche");
                result.add(new Employer(var1, var2, var3, var4));
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
        return result.toArray(new Employer[]{});
    }

    public Employer findByEmail (String email, Connection con){
        Employer result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM employer WHERE email=?";
            st = con.prepareStatement(sql);
            st.setString(1, email);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                String var3 = res.getString("email");
                Date var4 = res.getDate("date_embauche");
                result = new Employer(var1, var2, var3, var4);
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
}
