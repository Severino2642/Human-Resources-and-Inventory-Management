CREATE TABLE admin (
    id serial primary key,
    email VARCHAR(255),
    mdp VARCHAR(255)
);

CREATE TABLE status (
    id serial primary key,
    intitule varchar(255)
);

CREATE TABLE employer (
    id serial primary key,
    nom VARCHAR(255),
    email VARCHAR(255),
    date_embauche date
);

CREATE TABLE departement (
    id serial primary key,
    nom VARCHAR(255),
    date_ajout date
);

CREATE TABLE departement_employer (
    id serial primary key,
    idEmployer int,
    idDepartement int,
    idStatus int,
    date_ajout date,
    FOREIGN KEY (idEmployer) REFERENCES employer(id) ON DELETE CASCADE,
    FOREIGN KEY (idDepartement) REFERENCES departement(id) ON DELETE CASCADE,
    FOREIGN KEY (idStatus) REFERENCES status(id) ON DELETE CASCADE
);

CREATE TABLE gestion_stock(
    id serial primary key,
    nom VARCHAR(255)
);

CREATE TABLE produit (
    id serial primary key,
    idGestionStock int,
    nom VARCHAR(255),
    FOREIGN KEY (idGestionStock) REFERENCES gestion_stock(id) ON DELETE CASCADE
);

CREATE TABLE demande (
    id serial primary key,
    idDepartement int,
    idProduit int,
    intitule VARCHAR(255),
    quantite decimal(10,2),
    date_demande date,
    date_besoin date,
    FOREIGN KEY (idDepartement) REFERENCES departement(id) ON DELETE CASCADE,
    FOREIGN KEY (idProduit) REFERENCES produit(id) ON DELETE CASCADE
);

CREATE TABLE departement_validation (
    id serial primary key,
    idDemande int,
    idDepartement int,
    valeur decimal(10,2),
    isValide boolean,
    date_validation date,
    FOREIGN KEY (idDemande) REFERENCES demande(id) ON DELETE CASCADE,
    FOREIGN KEY (idDepartement) REFERENCES departement(id) ON DELETE CASCADE
);

CREATE TABLE demande_validation (
    id serial primary key,
    idDemande int,
    quantite decimal(10,2),
    isValide boolean,
    date_validation date,
    FOREIGN KEY (idDemande) REFERENCES demande(id) ON DELETE CASCADE
);

CREATE TABLE fournisseur (
    id serial primary key ,
    nom VARCHAR(255),
    date_ajout date
);

CREATE TABLE incident (
    id serial primary key,
    idFournisseur int,
    remarque VARCHAR(255),
    date_incident date,
    FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id) ON DELETE CASCADE
);



CREATE TABLE prix_produit(
    id serial primary key,
    idProduit int,
    isVente boolean,
    valeur decimal(10,2),
    date_modif date,
    FOREIGN KEY (idProduit) REFERENCES produit(id) ON DELETE CASCADE
);

CREATE TABLE produit_fournisseur(
    id serial primary key,
    idFournisseur int,
    idProduit int,
    prix_unitaire decimal(10,2),
    date_modif date,
    FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id) ON DELETE CASCADE,
    FOREIGN KEY (idProduit) REFERENCES produit(id) ON DELETE CASCADE
);

CREATE TABLE caisse (
    id serial primary key,
    isEntrer boolean,
    montant decimal(10,2),
    date_modif date
);

CREATE TABLE pro_format (
    id serial primary key,
    idDemande int,
    idFournisseur int,
    quantite decimal(10,2),
    montant decimal(10,2),
    date_ajout date,
    FOREIGN KEY (idDemande) REFERENCES demande(id) ON DELETE CASCADE,
    FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id) ON DELETE CASCADE
);

CREATE TABLE bon_commande (
    id serial primary key,
    idProduit int,
    idFournisseur int,
    quantite decimal(10,2),
    montant decimal(10,2),
    isValide boolean,
    date_commande date,
    date_besoin date,
    FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id) ON DELETE CASCADE,
    FOREIGN KEY (idProduit) REFERENCES produit(id) ON DELETE CASCADE
);

CREATE TABLE bon_livraison (
    id serial primary key,
    idBonCommande int,
    quantite decimal(10,2),
    montant decimal(10,2),
    date_livraison date,
    FOREIGN KEY (idBonCommande) REFERENCES bon_commande(id) ON DELETE CASCADE
);

CREATE TABLE bon_reception (
    id serial primary key,
    idBonLivraison int,
    quantite decimal(10,2),
    montant decimal(10,2),
    date_recu date,
    FOREIGN KEY (idBonLivraison) REFERENCES bon_livraison(id) ON DELETE CASCADE
);

INSERT INTO admin (email, mdp) VALUES ('divaraly@gmail.com','1234');

INSERT INTO status (intitule) VALUES ('Chef de departement');
INSERT INTO status (intitule) VALUES ('Employer');

INSERT INTO gestion_stock (nom) VALUES ('CMUP');
INSERT INTO gestion_stock (nom) VALUES ('FIFO');
INSERT INTO gestion_stock (nom) VALUES ('LIFO');


SELECT d.id FROM demande d
JOIN demande_validation dv on d.id = dv.idDemande
WHERE d.quantite = dv.quantite;