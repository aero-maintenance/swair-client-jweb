CREATE TABLE IF NOT EXISTS db_softwair.utilisateur (

	user_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	nom_aeroclub VARCHAR(40) NOT NULL,
	adresse VARCHAR(255) NOT NULL,
	ville VARCHAR(255) NOT NULL,
	code_postale VARCHAR(255) NOT NULL,
	password CHAR(64) NOT NULL,
	email VARCHAR(255) NOT NULL,
	
	PRIMARY KEY (user_id)

)

ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS db_softwair.aircraft (

	ac_id INT UNSIGNED NOT NULL AUTO_INCREMENT,	
	id_user INT UNSIGNED NOT NULL,
	modele VARCHAR(40) NOT NULL,
	immatriculation VARCHAR(255) NOT NULL,
	total_FH FLOAT NOT NULL,
	total_FC INT UNSIGNED NOT NULL,
	msn INT NOT NULL,
	statut VARCHAR(255),
	date_Kardex DATE,
	remarque TEXT,
	PRIMARY KEY (ac_id),
	CONSTRAINT fk_user_id     -- On donne un nom à notre clé
   		FOREIGN KEY (id_user)   -- Colonne sur laquelle on crée la clé
    		REFERENCES utilisateur(user_id)     -- Colonne de référence (celle de la table Client)
		
)

ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS db_softwair.vol (

	id_vol INT UNSIGNED NOT NULL AUTO_INCREMENT,	
	id_ac INT UNSIGNED NOT NULL,
	date_heure DATETIME NOT NULL,
	FH FLOAT NOT NULL,
	FC INT UNSIGNED NOT NULL,
	remarque TEXT,
	PRIMARY KEY (id_vol),
	CONSTRAINT fk_ac_id     -- On donne un nom à notre clé
   		FOREIGN KEY (id_ac)   -- Colonne sur laquelle on crée la clé
    		REFERENCES aircraft(ac_id)     -- Colonne de référence (celle de la table Client)
   		
)

ENGINE=INNODB;

INSERT INTO utilisateur
VALUES(2,'aeroclub1','B01, parc de la verrerie','Creon','33670','967520ae23e8ee14888bae72809031b98398ae4a636773e18fff917d77679334','yanou3345@hotmail.fr');