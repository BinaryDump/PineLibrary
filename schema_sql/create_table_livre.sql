-- DROP TABLE livres;

create table livres (
	id SERIAL PRIMARY KEY NOT NULL,
	titre character varying,
	auteur character varying,
	editeur character varying,
	isbn character varying,
	login_user character varying NOT NULL,
	FOREIGN KEY(login_user) REFERENCES utilisateurs(login_user)
);
