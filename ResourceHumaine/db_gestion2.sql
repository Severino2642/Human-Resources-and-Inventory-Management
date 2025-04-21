CREATE TABLE admin (
    id serial primary key,
    email VARCHAR(255),
    mdp VARCHAR(255)
);

CREATE TABLE status (
    id serial primary key,
    intitule varchar(255)
);

CREATE TABLE sexe (
    id serial primary key,
    intitule varchar(255)
);

CREATE TABLE departement (
    id serial primary key,
    nom VARCHAR(255),
    date_ajout date
);

CREATE TABLE fournisseur (
    id serial primary key ,
    nom VARCHAR(255),
    date_ajout date
);

CREATE TABLE personne (
    id serial primary key,
    idFournisseur int,
    idSexe int,
    nom VARCHAR(255),
    email VARCHAR(255),
    date_ajout date,
    FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id) ON DELETE CASCADE,
    FOREIGN KEY (idSexe) REFERENCES sexe(id) ON DELETE CASCADE
);

CREATE TABLE talent (
    id serial primary key,
    nom varchar(255),
    date_ajout date
);

CREATE TABLE experience (
    id serial primary key,
    idPersonne int,
    idTalent int,
    duree int,
    date_ajout date,
    FOREIGN KEY (idPersonne) REFERENCES personne(id) ON DELETE CASCADE,
    FOREIGN KEY (idTalent) REFERENCES talent(id) ON DELETE CASCADE
);

CREATE TABLE departement_employer (
    id serial primary key,
    idPersonne int,
    idDepartement int,
    idStatus int,
    date_ajout date,
    FOREIGN KEY (idPersonne) REFERENCES personne(id) ON DELETE CASCADE,
    FOREIGN KEY (idDepartement) REFERENCES departement(id) ON DELETE CASCADE,
    FOREIGN KEY (idStatus) REFERENCES status(id) ON DELETE CASCADE
);

CREATE TABLE demande (
    id serial primary key,
    idDepartement int,
    intitule varchar(255),
    quantite int,
    idSexe int,
    date_demande date,
    FOREIGN KEY (idDepartement) REFERENCES departement(id) ON DELETE CASCADE
);

CREATE TABLE experience_demande (
    id serial primary key,
    idDemande int,
    idTalent int,
    duree int,
    FOREIGN KEY (idDemande) REFERENCES demande(id) ON DELETE CASCADE,
    FOREIGN KEY (idTalent) REFERENCES talent(id) ON DELETE CASCADE
);

CREATE TABLE demande_validation (
    id serial primary key,
    idDemande int,
    idPersonne int,
    date_validation date,
    FOREIGN KEY (idDemande) REFERENCES demande(id) ON DELETE CASCADE,
    FOREIGN KEY (idPersonne) REFERENCES personne(id) ON DELETE CASCADE
);

CREATE TABLE annonce (
    id serial primary key,
    idDemande int,
    idFournisseur int,
    date_ajout date,
    FOREIGN KEY (idDemande) REFERENCES demande(id) ON DELETE CASCADE,
    FOREIGN KEY (idFournisseur) REFERENCES fournisseur(id) ON DELETE CASCADE
);

CREATE TABLE suggestion (
    id serial primary key,
    idAnnonce int,
    idPersonne int,
    date_ajout date,
    FOREIGN KEY (idAnnonce) REFERENCES annonce(id) ON DELETE CASCADE,
    FOREIGN KEY (idPersonne) REFERENCES personne(id) ON DELETE CASCADE
);

CREATE TABLE test (
    id serial primary key,
    idDemande int,
    idPersonne int,
    note decimal(10,2),
    date_ajout date,
    FOREIGN KEY (idDemande) REFERENCES demande(id) ON DELETE CASCADE,
    FOREIGN KEY (idPersonne) REFERENCES personne(id) ON DELETE CASCADE
);

CREATE TABLE question (
    id serial primary key,
    idTalent int,
    intitule varchar(255),
    date_ajout date,
    FOREIGN KEY (idTalent) REFERENCES talent(id) ON DELETE CASCADE
);

CREATE TABLE reponse (
    id serial primary key,
    idQuestion int,
    intitule varchar(255),
    point int,
    FOREIGN KEY (idQuestion) REFERENCES question(id) ON DELETE CASCADE
);

-- CREATE TABLE details_test (
--     id serial primary key,
--     idTest int,
--     idQuestion int,
--     idReponse int,
--     FOREIGN KEY (idTest) REFERENCES test(id) ON DELETE CASCADE,
--     FOREIGN KEY (idQuestion) REFERENCES question(id) ON DELETE CASCADE,
--     FOREIGN KEY (idReponse) REFERENCES reponse(id) ON DELETE CASCADE
-- );


INSERT INTO admin (email, mdp) VALUES ('divaraly@gmail.com','1234');

INSERT INTO status (intitule) VALUES ('Chef de departement');
INSERT INTO status (intitule) VALUES ('Employer');

INSERT INTO sexe (intitule) VALUES ('Homme');
INSERT INTO sexe (intitule) VALUES ('Femme');
