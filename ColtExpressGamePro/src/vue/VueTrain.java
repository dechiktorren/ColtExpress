package vue;

import modele.Modele;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VueTrain extends JPanel implements Observer {
    /** On maintient une référence vers le modèle. */
    private Modele modele;
    /** Définition d'une taille (en pixels) pour l'affichage des cellules. */
    private final static int TAILLE = 12;

    /** Constructeur. */
    public VueTrain(Modele modele) {
	this.modele = modele;
	/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
	modele.addObserver(this);
	/**
	 * Définition et application d'une taille fixe pour cette zone de
	 * l'interface, calculée en fonction du nombre de cellules et de la
	 * taille d'affichage.
	 */
	Dimension dim = new Dimension(TAILLE*Modele.LARGEUR,
				      TAILLE*Modele.HAUTEUR);
	this.setPreferredSize(dim);
    }

    /**
     * L'interface [Observer] demande de fournir une méthode [update], qui
     * sera appelée lorsque la vue sera notifiée d'un changement dans le
     * modèle. Ici on se content de réafficher toute la grille avec la méthode
     * prédéfinie [repaint].
     */
    public void update() { repaint(); }

    /**
     * Les éléments graphiques comme [JPanel] possèdent une méthode
     * [paintComponent] qui définit l'action à accomplir pour afficher cet
     * élément. On la redéfinit ici pour lui confier l'affichage des cellules.
     *
     * La classe [Graphics] regroupe les éléments de style sur le dessin,
     * comme la couleur actuelle.
     */
    public void paintComponent(Graphics g) {
		super.repaint();
		/** Pour chaque cellule... */
		for(int i=1; i<=Modele.LARGEUR; i++) {
		    for(int j=1; j<=Modele.HAUTEUR; j++) {
			/**
			 * ... Appeler une fonction d'affichage auxiliaire.
			 * On lui fournit les informations de dessin [g] et les
			 * coordonnées du coin en haut à gauche.
			 */
			paint(g, modele.getCellule(i, j), (i-1)*TAILLE, (j-1)*TAILLE);
		    }
		}
    }
    /**
     * Fonction auxiliaire de dessin d'une cellule.
     * Ici, la classe [Cellule] ne peut être désignée que par l'intermédiaire
     * de la classe [CModele] à laquelle elle est interne, d'où le type
     * [CModele.Cellule].
     * Ceci serait impossible si [Cellule] était déclarée privée dans [CModele].
     */
    private void paint(Graphics g, CModele.Cellule c, int x, int y) {
	if (c.estVivante()) {
	    /** Sélection d'une couleur. */
	    g.setColor(Color.BLACK);
	    /** Coloration d'un rectangle. */
	    g.fillRect(x, y, TAILLE, TAILLE);
	}
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
