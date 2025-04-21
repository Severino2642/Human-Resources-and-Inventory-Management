package facture;

import caisse.Caisse;
import connexion.Base;

import java.sql.*;

public class ProFormat {
    int id;
    int idDemande;
    int idFournisseur;
    double quantite;
    double montant;
    Date date_ajout;

    public ProFormat() {
    }

    public ProFormat(int id, int idDemande, int idFournisseur, double quantite, double montant, Date date_ajout) {
        this.id = id;
        this.idDemande = idDemande;
        this.idFournisseur = idFournisseur;
        this.quantite = quantite;
        this.montant = montant;
        this.date_ajout = date_ajout;
    }

    public ProFormat(int idDemande, int idFournisseur, double quantite, double montant, Date date_ajout) {
        this.idDemande = idDemande;
        this.idFournisseur = idFournisseur;
        this.quantite = quantite;
        this.montant = montant;
        this.date_ajout = date_ajout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
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
            String sql = "INSERT INTO pro_format (iddemande, idfournisseur, quantite, montant, date_ajout) VALUES (?,?,?,?,?) ";
            st = conn.prepareStatement(sql);
            st.setInt(1, idDemande);
            st.setInt(2, idFournisseur);
            st.setDouble(3, quantite);
            st.setDouble(4, montant);
            st.setDate(5, date_ajout);
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

    public void deleteByidDemande(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "DELETE FROM pro_format WHERE iddemande = ? ";
            st = conn.prepareStatement(sql);
            st.setInt(1, idDemande);
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

    public ProFormat findByIdDemande (int idDemande, Connection con){
        ProFormat result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM pro_format WHERE iddemande = ? ";
            st = con.prepareStatement(sql);
            st.setInt(1, idDemande);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("iddemande");
                int var3 = res.getInt("idfournisseur");
                double var4 = res.getDouble("quantite");
                double var5 = res.getDouble("montant");
                Date var6 = res.getDate("date_ajout");
                result = new ProFormat(var1, var2, var3, var4, var5, var6);
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
