package annexe;

import connexion.Base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Status {
    int id;
    String intitule;

    public Status() {
    }

    public Status(int id, String intitule) {
        this.id = id;
        this.intitule = intitule;
    }

    public Status(String intitule) {
        this.intitule = intitule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Status findById (int id, Connection con){
        Status result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM status WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                String var1 = res.getString("intitule");
                result = new Status(id,var1);
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

    public Status [] findAll (Connection con){
        List<Status> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM status";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("intitule");
                result.add(new Status(var1,var2));
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
        return result.toArray(new Status[]{});
    }
}
