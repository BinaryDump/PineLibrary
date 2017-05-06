-- DROP TABLE emprunts;

create table emprunts (
	id SERIAL PRIMARY KEY NOT NULL,
	login_emprunteur character varying,
	login_preteur character varying,
	isbn_livre_emprunte character varying,
	date_emprunt character varying,
	FOREIGN KEY(login_emprunteur) REFERENCES utilisateurs(login_user),
	FOREIGN KEY(login_preteur) REFERENCES utilisateurs(login_user)
);
