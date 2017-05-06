-- DROP TABLE utilisateurs;

CREATE TABLE utilisateurs
(
  login_user character varying,
  motDePasse_user character varying,
  nom_user character varying,
  prenom_user character varying,
  CONSTRAINT "loginUnique" UNIQUE (login_user)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE utilisateurs
  OWNER TO postgres;