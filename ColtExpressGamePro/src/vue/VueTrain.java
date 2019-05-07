package vue;

import modele.Train;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VueTrain extends JPanel implements Observer {
    /** On maintient une référence vers le modèle. */
    private Train train;
    
    // dimention d'une position en nombre de pixels 
    private final static int largeurPosition = 150;
    private final static int hauteurPosition = 100;
    

    /** Constructeur. */
    public VueTrain(Train train) {
		this.train = train;
		/** On enregistre la vue [this] en tant qu'observateur de [modele]. */
		train.addObserver(this);
		/**
		 * Définition et application d'une taille fixe pour cette zone de
		 * l'interface, calculée en fonction du nombre de cellules et de la
		 * taille d'affichage.
		 */
		Dimension dim = new Dimension(this.largeurPosition * train.getNB_WAGONS(),
					      this.hauteurPosition * 2);
		this.setPreferredSize(dim);
    }

    /**
     * L'interface [Observer] demande de fournir une méthode [update], qui
     * sera appelée lorsque la vue sera notifiée d'un changement dans le
     * modèle. Ici on se content de réafficher toute la grille avec la méthode
     * prédéfinie [repaint].
     */
    public void update() { 
    	repaint(); 
    }

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
		for(int i=1; i<=train.getNB_WAGONS(); i++) {
		    for(int j=1; j<=1; j++) {
			/**
			 * ... Appeler une fonction d'affichage auxiliaire.
			 * On lui fournit les informations de dessin [g] et les
			 * coordonnées du coin en haut à gauche.
			 */
		    	paint(g, train.getPos(i, j), j*largeur, i*hauteur);
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
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
