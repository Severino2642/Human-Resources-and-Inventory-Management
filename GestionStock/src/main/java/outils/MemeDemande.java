package outils;

import connexion.Base;
import demande.Demande;
import departement.DepartementValidation;
import produit.Produit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemeDemande {
    Produit produit;
    List<Demande> demandes = new ArrayList<>();
    double totalQuantite;

    public MemeDemande() {
    }

    public MemeDemande(Produit produit, List<Demande> demandes) {
        this.produit = produit;
        this.demandes = demandes;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public void addDemande(Demande demande) {
        this.demandes.add(demande);
    }

    public double getTotalQuantite() {
        return totalQuantite;
    }

    public void setTotalQuantite(double totalQuantite) {
        this.totalQuantite = totalQuantite;
    }

    public MemeDemande findByIdProduit(int idProduit, Connection con) throws SQLException {
        MemeDemande memeDemande = null;
        boolean newConnex = false;
        if (con == null) {
            con = Base.PsqlConnect();
        }
        Demande [] demandes = new Demande().getDemandeNotValidationByIdProduit(idProduit,con);
        if (demandes.length > 0) {
            memeDemande = new MemeDemande();
            memeDemande.setProduit(new Produit().findById(idProduit,con));
            double totalQuantite = 0;
            for (Demande demande : demandes) {
                memeDemande.addDemande(demande);
                totalQuantite += demande.getQuantite();
            }
            memeDemande.setTotalQuantite(totalQuantite);
        }

        if (con != null && newConnex) {
            con.close();
        }
        return memeDemande;
    }

    public MemeDemande findByIdProduitAvecDepartementValidation(int idProduit,int idDepartement, Connection con) throws SQLException {
        MemeDemande memeDemande = null;
        boolean newConnex = false;
        if (con == null) {
            con = Base.PsqlConnect();
        }
        Demande [] demandes = new Demande().getDemandeNotValidationByIdProduit(idProduit,con);
        if (demandes.length > 0) {
            List<Demande> list = new ArrayList<>();
            double totalQuantite = 0;
            for (Demande demande : demandes) {
                DepartementValidation departementValidation = new DepartementValidation().findByIdDemandeAndIdDepartementAndIsValide(demande.getId(),idDepartement,false,con);
                if (departementValidation != null){
                    list.add(demande);
                    totalQuantite += demande.getQuantite();
                }
            }
            if (list.size() > 0) {
                memeDemande = new MemeDemande();
                memeDemande.setProduit(new Produit().findById(idProduit,con));
                memeDemande.setTotalQuantite(totalQuantite);
                memeDemande.setDemandes(list);
            }
        }

        if (con != null && newConnex) {
            con.close();
        }
        return memeDemande;
    }

    public MemeDemande [] findAllMemeDemande(Connection con) throws SQLException {
        List<MemeDemande> result = new ArrayList<>();
        boolean newConnex = false;
        if (con == null) {
            con = Base.PsqlConnect();
        }
        Produit [] produits = new Produit().findAll(con);
        for (Produit produit : produits) {
            MemeDemande memeDemande = this.findByIdProduit(produit.getId(), con);
            if (memeDemande != null) {
                result.add(memeDemande);
            }
        }
        if (con != null && newConnex) {
            con.close();
        }
        return result.toArray(new MemeDemande[]{});
    }

    public MemeDemande [] findAllMemeDemandeByDepartement(int idDepartement,Connection con) throws SQLException {
        List<MemeDemande> result = new ArrayList<>();
        boolean newConnex = false;
        if (con == null) {
            con = Base.PsqlConnect();
        }
        Produit [] produits = new Produit().findAll(con);
        for (Produit produit : produits) {
            MemeDemande memeDemande = this.findByIdProduitAvecDepartementValidation(produit.getId(),idDepartement, con);
            if (memeDemande != null) {
                result.add(memeDemande);
            }
        }
        if (con != null && newConnex) {
            con.close();
        }
        return result.toArray(new MemeDemande[]{});
    }

    public void sendRequestByIdDepartement (int idDepartement, Connection con) throws Exception {
        boolean newConnex = false;
        if (con == null) {
            con = Base.PsqlConnect();
        }
        for (Demande demande : this.getDemandes()) {
            DepartementValidation departementValidation = new DepartementValidation(demande.getId(),idDepartement,demande.getQuantite(),false, Base.getDate());
            departementValidation.insert(con);
        }

        if (con != null && newConnex) {
            con.close();
        }
    }
}
