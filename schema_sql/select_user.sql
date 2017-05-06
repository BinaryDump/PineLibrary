-- attention si linux ou mac (à recopier à la main) car il pourrait y avoir des caractères de controle invisibles lors du copier/coller
SELECT * FROM utilisateur WHERE login_user='Igna' AND motDePasse_user = crypt('igna', motDePasse_user);
