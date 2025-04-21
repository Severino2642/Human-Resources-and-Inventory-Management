package user;

import connexion.Base;

import java.sql.*;

public class Admin {
    int id;
    String email;
    String mdp;

    public Admin() {
    }

    public Admin(int id, String email, String mdp) {
        this.id = id;
        this.email = email;
        this.mdp = mdp;
    }

    public Admin(String email, String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Admin findByEmailAndPassword (String email,String mdp, Connection con){
        Admin result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM admin WHERE email=? AND mdp=?";
            st = con.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, mdp);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                String var2 = res.getString("email");
                String var3 = res.getString("mdp");
                result = new Admin(var1, var2, var3);
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
