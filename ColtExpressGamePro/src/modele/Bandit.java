package modele;


import java.util.Random;
import java.util.Set;

import modele.Train.Wagon;

/*
 * Le class qui represente le bandit
 * 
 */
public class Bandit extends Personne
{
	
	private boolean interieur; //pour savoir s'il est sur le toit ou dans le wagon
	protected SacButin sac;
	
	/*
	 * Creer le bandit sur le toit de dernier wagon
	 */
	public Bandit(Train t, String name){
		super(t,name);
		interieur = false; // au debut il est sur le toit
		sac = new SacButin();
	}
	
	@Override
	protected Wagon positionInitiale(Train t, Personne p) {
		return t.banditLastWagon(this);
	}
	
	// getter pour les autres classes
	public boolean getInterieur( ) { 
		return this.interieur;
	}

	public void tirer() {
		Bandit touche = wagon.anotherBanditThan(this);
		//Set<Bandit> bandits = wagon.getBandits();
		if(touche == null) {
			System.out.println(this.getName() + " has shot no body");
		}
		else {
			System.out.println(this.getName() + " has shot " + touche.getName());
			if(!touche.isEmpty())
				wagon.addButin(touche.popButin());
		}
	}

	
	@Override
	protected void executeAction() {
		//si cette action est nulle rien va etre executer
		Action actionExcute = actions.actionToExecute();
		
		if(actionExcute.equals(Action.Tirer)) {
			this.tirer();
		}
		if(actionExcute.equals(Action.Braquer)) {
			this.braquer();
		}
		if(interieur && actionExcute.equals(Action.Monter)) {
			interieur = false;
			System.out.println(getName()+" monte sur le toit");
			System.out.println(wagon);
			return;
		}
		if(!interieur && actionExcute.equals(Action.Descendre)) {
			interieur = true;
			System.out.println(getName()+" descend a l'interieur");
			System.out.println(wagon);
			return;
		}
		if(!wagon.isLastWagon() && actionExcute.equals(Action.Avance)) {
			Train.Wagon newWagon = wagon.avanceBandit(this);
			System.out.println(getName()+" avance vers la fin de train");
			wagon =  newWagon;
			System.out.println(wagon);
			return;
		}
		if(!wagon.isFirstWagon() && actionExcute.equals(Action.Recule)) {
			Train.Wagon newWagon =wagon.reculeBandit(this);
			System.out.println(getName()+" recule vers le debut de train");
			wagon =  newWagon;
			System.out.println(wagon);
			return;
		}
		System.out.println(getName()+ " has nothing to do!");
	}
	
	
	
	
	public String toString() {
		String pos = (this.interieur)? ("a l'interieur"):("sur le toit") ;
		return this.getName() + " est " + pos;
	}
	
	
	
	public void braquer() {
		if(wagon.getButins().isEmpty()) return;
		this.sac.pushButin(wagon.popButin());
	}
	
	
	/*
	 * Juste pour changer le nom de class and max = 4 
	 */
	class SacButin extends ContainerStack{ 
		SacButin() {
			super(4);
		}
		
	}









	
		
		

	
}

