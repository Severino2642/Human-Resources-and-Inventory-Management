package demande;

import connexion.Base;
import departement.Departement;
import departement.DepartementValidation;
import outils.MemeDemande;
import produit.Produit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Demande {
    int id;
    int idDepartement;
    int idProduit;
    String intitule;
    double quantite;
    Date date_demande;
    Date date_besoin;
    DemandeValidation demandeValidation;
    Produit produit;
    Departement departement;
    public Demande() {
    }

    public Demande(int id, int idDepartement, int idProduit, String intitule, double quantite, Date date_demande, Date date_besoin) {
        this.id = id;
        this.idDepartement = idDepartement;
        this.idProduit = idProduit;
        this.intitule = intitule;
        this.quantite = quantite;
        this.date_demande = date_demande;
        this.date_besoin = date_besoin;
        try {
            Connection con = Base.PsqlConnect();
            this.demandeValidation = new DemandeValidation().findByIdDemande(id,con);
            this.produit = new Produit().findById(idProduit,con);
            this.departement = new Departement().findById(idDepartement,con);
            con.close();
        }catch (Exception e) {}
    }

    public Demande(int idDepartement, int idProduit, String intitule, double quantite, Date date_demande, Date date_besoin) {
        this.idDepartement = idDepartement;
        this.idProduit = idProduit;
        this.intitule = intitule;
        this.quantite = quantite;
        this.date_demande = date_demande;
        this.date_besoin = date_besoin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDepartement() {
        return idDepartement;
    }

    public void setIdDepartement(int idDepartement) {
        this.idDepartement = idDepartement;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public Date getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public Date getDate_besoin() {
        return date_besoin;
    }

    public void setDate_besoin(Date date_besoin) {
        this.date_besoin = date_besoin;
    }

    public DemandeValidation getDemandeValidation() {
        return demandeValidation;
    }

    public void setDemandeValidation(DemandeValidation demandeValidation) {
        this.demandeValidation = demandeValidation;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public String getTermeValidation (){
        String result = "en attente";
        if (this.getDemandeValidation() != null){
            if (this.getDemandeValidation().isValid()){
                result = "accepter";
            }
            else {
                result = "rejeter";
            }
        }
        return result;
    }
    public void insert(Connection conn) {
        PreparedStatement st = null;
        boolean newConnection = false;
        try{
            if (conn == null) {
                conn = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "INSERT INTO demande ( iddepartement, idproduit, intitule, quantite, date_demande, date_besoin) VALUES (?,?,?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, idDepartement);
            st.setInt(2, idProduit);
            st.setString(3, intitule);
            st.setDouble(4, quantite);
            st.setDate(5, date_demande);
            st.setDate(6, date_besoin);
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

    public Demande findById (int id, Connection con){
        Demande result = null;
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM demande WHERE id=?";
            st = con.prepareStatement(sql);
            st.setInt(1, id);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("iddepartement");
                int var3 = res.getInt("idproduit");
                String var4 = res.getString("intitule");
                double var5 = res.getDouble("quantite");
                Date var6 = res.getDate("date_demande");
                Date var7 = res.getDate("date_besoin");
                result = new Demande(var1, var2, var3, var4, var5, var6, var7);
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

    public Demande [] findByIdDepartement (int idDepartement, Connection con){
        List<Demande> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM demande WHERE iddepartement=?";
            st = con.prepareStatement(sql);
            st.setInt(1, idDepartement);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("iddepartement");
                int var3 = res.getInt("idproduit");
                String var4 = res.getString("intitule");
                double var5 = res.getDouble("quantite");
                Date var6 = res.getDate("date_demande");
                Date var7 = res.getDate("date_besoin");
                result.add(new Demande(var1, var2, var3, var4, var5, var6, var7));
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
        return result.toArray(new Demande[]{});
    }

    public Demande [] getDemandeNotValidation (Connection con){
        List<Demande> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM demande WHERE id NOT IN (SELECT d.iddemande FROM demande_validation d) OR id IN (SELECT d.id FROM demande d\n" +
                    "JOIN demande_validation dv on d.id = dv.idDemande\n" +
                    "WHERE d.quantite > dv.quantite AND dv.quantite > 0) ORDER BY id DESC";
            st = con.prepareStatement(sql);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("iddepartement");
                int var3 = res.getInt("idproduit");
                String var4 = res.getString("intitule");
                double var5 = res.getDouble("quantite");
                Date var6 = res.getDate("date_demande");
                Date var7 = res.getDate("date_besoin");
                result.add(new Demande(var1, var2, var3, var4, var5, var6, var7));
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
        return result.toArray(new Demande[]{});
    }

    public Demande [] getDemandeNotValidationByIdProduit (int idProduit,Connection con){
        List<Demande> result = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet res = null;
        boolean newConnection = false;
        try{
            if (con == null) {
                con = Base.PsqlConnect();
                newConnection = true;
            }
            String sql = "SELECT * FROM demande WHERE (id NOT IN (SELECT d.iddemande FROM demande_validation d) OR id IN (SELECT d.id FROM demande d\n" +
                    "JOIN demande_validation dv on d.id = dv.idDemande\n" +
                    "WHERE d.quantite > dv.quantite AND dv.quantite > 0)) AND idproduit=? ORDER BY id DESC";
            st = con.prepareStatement(sql);
            st.setInt(1, idProduit);
            res = st.executeQuery();
            while(res.next()){
                int var1 = res.getInt("id");
                int var2 = res.getInt("iddepartement");
                int var3 = res.getInt("idproduit");
                String var4 = res.getString("intitule");
                double var5 = res.getDouble("quantite");
                Date var6 = res.getDate("date_demande");
                Date var7 = res.getDate("date_besoin");
                result.add(new Demande(var1, var2, var3, var4, var5, var6, var7));
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
        return result.toArray(new Demande[]{});
    }

    public double getTotalQuantite (){
        Demande [] demandes = this.getDemandeNotValidationByIdProduit (this.getIdProduit(), null);
        double total = 0;
        for (Demande demande: demandes){
            total += demande.getQuantite();
        }
        return total;
    }

    public int getNextAction (int idDepartementStock,int idDepartementFinance,int idDepartementAchat,Connection con) throws SQLException {
        int result = 0;
        boolean newConnex = false;
        if (con == null) {
            con = Base.PsqlConnect();
        }
        DepartementValidation dpvStock = new DepartementValidation().findByIdDemandeAndIdDepartement(this.getId(),idDepartementStock,con);
        if (dpvStock != null) {
            if (!dpvStock.isValid()){
                result = -1;
            }
            else {
                result++;
                DepartementValidation dpvAchat = new DepartementValidation().findByIdDemandeAndIdDepartement(this.getId(),idDepartementAchat,con);
                if (dpvAchat != null) {
                    if (!dpvAchat.isValid()){
                        result = -2;
                    }
                    else {
                        result++;
                        DepartementValidation dpvFinance = new DepartementValidation().findByIdDemandeAndIdDepartement(this.getId(),idDepartementFinance,con);
                        if (dpvFinance != null) {
                            if (!dpvFinance.isValid()){
                                result = -1;
                            }
                            else {
                                result++;
                            }
                        }
                    }
                }
            }

        }

        if (con != null && newConnex) {
            con.close();
        }
        return result;
    }
}
