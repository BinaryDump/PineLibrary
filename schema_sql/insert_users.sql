DELETE FROM utilisateur;
INSERT INTO utilisateur(login_user, motDePasse_user, nom_user, prenom_user) VALUES ('Caly', crypt('quentin', gen_salt('bf',8)), 'Quentin', 'Hussin');
INSERT INTO utilisateur(login_user, motDePasse_user, nom_user, prenom_user) VALUES ('Igna', crypt('igna', gen_salt('bf',8)), 'Igna', 'Igna');
INSERT INTO utilisateur(login_user, motDePasse_user, nom_user, prenom_user) VALUES ('Lalwende', crypt('sarah', gen_salt('bf',8)), 'Sarah', 'Bordin');

