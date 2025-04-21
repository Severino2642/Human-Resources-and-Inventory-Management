package departement;

import annexe.Status;
import connexion.Base;
import produit.Produit;
import user.Employer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Departement {
    int id;
    String nom;
    Date date_ajout;
    List<Employer> employers = new ArrayList<>();

    public Departement() {
    }

    public Departement(String nom, Date date_ajout) {
        this.nom = nom;
        this.date_ajout = date_ajout;
    }

    public Departement(int id, String nom, Date date_ajout) {
        this.id = id;
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

    public List<Employer> getEmployers() {
        return employers;
    }

    public void setEmployers (Connection con) throws SQLException {
        boolean newConnex = false;
        if (con == null){
            newConnex = true;
            con = Base.PsqlConnect();
        }
        this.employers = new ArrayList<>();
        DepartementEmployer [] departementEmployer = new DepartementEmployer().findByIdDepartement(this.getId(),con);
        for (DepartementEmployer de : departementEmployer){
            Status status = new Status().findById(de.getIdStatus(),con);
            Employer emp = new Employer().findById(de.getIdEmployer(),con);
            emp.setStatus(status);
            emp.setDepartement_employer(de);
            this.employers.add(emp);
        }
        if (newConnex && con != null){
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
            String sql = "INSERT INTO departement ( nom, date_ajout) VALUES ( ?, ?)";
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
            String sql = "Delete from departement where id = ?";
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

    public Departement findById (int id, Connection con){
        Departement result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                Date var3 = res.getDate("date_ajout");
                result = new Departement(var1, var2, var3);
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

    public Departement findByNom (String nom, Connection con){
        Departement result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement WHERE nom LIKE ?";
            st = con.prepareStatement(sql);
            st.setString(1, nom);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                Date var3 = res.getDate("date_ajout");
                result = new Departement(var1, var2, var3);
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

    public Departement [] findAll ( Connection con){
        List<Departement> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("nom");
                Date var3 = res.getDate("date_ajout");
                result.add(new Departement(var1, var2, var3));
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
        return result.toArray(new Departement[]{});
    }


}
