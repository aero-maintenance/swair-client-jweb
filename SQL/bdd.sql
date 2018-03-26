DROP table if exists vol;
DROP table if exists aircraft;
DROP table if exists utilisateur;

CREATE TABLE IF NOT EXISTS db_softwair.utilisateur (
	user_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	nom_aeroclub VARCHAR(40) NOT NULL,
	adresse VARCHAR(255) NOT NULL,
	ville VARCHAR(255) NOT NULL,
	code_postale VARCHAR(255) NOT NULL,
	password CHAR(56) NOT NULL,
	email VARCHAR(255) NOT NULL,
	telephone varchar(15),
	PRIMARY KEY (user_id)
)

ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS db_softwair.aircraft (

	ac_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	user_id INT(11) UNSIGNED NOT NULL,
	constructeur VARCHAR(50) NOT NULL,
	modele VARCHAR(50) NOT NULL,
	immatriculation VARCHAR(20) NOT NULL,
	total_FH FLOAT NOT NULL,
	total_FC INT(11) UNSIGNED NOT NULL,
	msn INT(11) UNSIGNED NOT NULL,
	statut VARCHAR(255),
	date_Kardex DATE,
	remarque TEXT,
	PRIMARY KEY (ac_id),
	CONSTRAINT fk_user_id     -- On donne un nom à notre clé
   		FOREIGN KEY (user_id)   -- Colonne sur laquelle on crée la clé
    		REFERENCES utilisateur(user_id)     -- Colonne de référence (celle de la table utilisateur)
)

ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS db_softwair.vol (

	flight_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	ac_id INT(11) UNSIGNED NOT NULL,
	date_heure DATETIME NOT NULL,
	FH FLOAT NOT NULL,
	FC INT(11) UNSIGNED NOT NULL,
	huile INT(11) NOT NULL,
	carburant INT(11) NOT NULL,
	remarque TEXT,
	PRIMARY KEY (flight_id),
	CONSTRAINT fk_ac_id     -- On donne un nom à notre clé
   		FOREIGN KEY (ac_id)   -- Colonne sur laquelle on crée la clé
    		REFERENCES aircraft(ac_id)     -- Colonne de référence (celle de la table aircraft)
   		
)

ENGINE=INNODB;

INSERT INTO utilisateur
VALUES('1','aeroclub_test','B01, parc de la verrerie','Creon','33670','skz6A3m0zrTHv/tmhtzCLKXodrAqokc9CIkNb8ASvY924FNvbQBHLg==','test@test.com','0258653598');