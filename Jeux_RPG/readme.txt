to do:
    creer un jeu de type rpg avec au moins

    -Personnage : Nom / PV / degats / defense (au moins 3 perso)
    -Objet : Nom
    -Donjon : Nom / liste Salle / Loot final(objet)
    -Salle : Nom / Personnage / Loot(objet)

    phase de combat :
    while true:
        for each hero in herolist:
            ->un hero fait une action (runattackcommand)
            ->check si boss toujours vie, si non sortie du for each et du while true
            ->le boss fait une action sur un hero (au hasard)
        ->check si un des hero toujours en vie, si non l'enlever de la liste des hero (deplacer dans liste hero mort ?)
        ->si tous mort game over
        ->regen la mana de tout les hero

note:
    donjon plan
    //----20- ----21- ----22-
	//----10- ----11- ----12-
	//----00- ----01- ----02-
	//----xx- -start- ----xx-  	