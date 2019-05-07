package modele;

import java.util.Random;

public abstract class Butin {
	protected int valeur;
	private String nom;
	protected ContainerStack cs;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}

class Bourse extends Butin {
	public Bourse(ContainerStack pos){
		this.setNom("Bourse");
		this.cs = pos;
    	Random rnd = new Random();
    	this.valeur = rnd.nextInt(501);
	}
}

class Bijou extends Butin {
	public Bijou(ContainerStack pos){
		this.setNom("Bijou");
		this.valeur = 500;
		this.cs = pos;
	}
}

class Magot extends Butin {
	public Magot(ContainerStack pos){
		this.setNom("Magot");
		this.valeur = 1000;
		this.cs = pos;
	}
}