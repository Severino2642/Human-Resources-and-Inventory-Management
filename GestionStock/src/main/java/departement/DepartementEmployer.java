package departement;

import connexion.Base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartementEmployer {
    int id;
    int idEmployer;
    int idDepartement;
    int idStatus;
    Date date_ajout;

    public DepartementEmployer() {
    }

    public DepartementEmployer(int idEmployer, int idDepartement, int idStatus, Date date_ajout) {
        this.idEmployer = idEmployer;
        this.idDepartement = idDepartement;
        this.idStatus = idStatus;
        this.date_ajout = date_ajout;
    }

    public DepartementEmployer(int id, int idEmployer, int idDepartement, int idStatus, Date date_ajout) {
        this.id = id;
        this.idEmployer = idEmployer;
        this.idDepartement = idDepartement;
        this.idStatus = idStatus;
        this.date_ajout = date_ajout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmployer() {
        return idEmployer;
    }

    public void setIdEmployer(int idEmployer) {
        this.idEmployer = idEmployer;
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public Date getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Date date_ajout) {
        this.date_ajout = date_ajout;
    }

    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO departement_employer (idemployer, iddepartement, idstatus, date_ajout) VALUES ( ?, ?, ?, ?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idEmployer);
            st.setInt(2, idDepartement);
            st.setInt(3, idStatus);
            st.setDate(4, date_ajout);
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
            String sql = "UPDATE departement_employer SET idstatus=? WHERE id=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, idStatus);
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

    public void delete(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "DELETE FROM departement_employer WHERE id=?";
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

    public DepartementEmployer [] findByIdEmployer (int idEmployer, Connection con){
        List<DepartementEmployer> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement_employer WHERE idEmployer = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, idEmployer);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idemployer");
                int var3 = res.getInt("iddepartement");
                int var4 = res.getInt("idstatus");
                Date var5 = res.getDate("date_ajout");
                result.add(new DepartementEmployer(var1, var2, var3, var4, var5));
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
        return result.toArray(new DepartementEmployer[]{});
    }

    public DepartementEmployer [] findByIdDepartement (int idDepartement, Connection con){
        List<DepartementEmployer> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement_employer WHERE idDepartement = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, idDepartement);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idemployer");
                int var3 = res.getInt("iddepartement");
                int var4 = res.getInt("idstatus");
                Date var5 = res.getDate("date_ajout");
                result.add(new DepartementEmployer(var1, var2, var3, var4, var5));
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
        return result.toArray(new DepartementEmployer[]{});
    }

    public DepartementEmployer findByIdEmployerAndIdDepartement (int idEmployer,int idDepartement, Connection con){
        DepartementEmployer result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM departement_employer WHERE idEmployer = ? AND idDepartement = ?";
            st = con.prepareStatement(sql);
            st.setInt(1, idEmployer);
            st.setInt(2, idDepartement);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("idemployer");
                int var3 = res.getInt("iddepartement");
                int var4 = res.getInt("idstatus");
                Date var5 = res.getDate("date_ajout");
                result = new DepartementEmployer(var1, var2, var3, var4, var5);
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
