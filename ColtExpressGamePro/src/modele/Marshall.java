package modele;

import java.util.Random;
import java.util.Set;

import modele.Train.Wagon;

public class Marshall extends Personne {

	public Marshall(Train t, String name) {
		super(t, name);
		//this.wagon.toString(); // inutile
	}

	
	@Override
	Wagon positionInitiale(Train t, Personne p) {
		this.train.getLocomotive().setMarshall(this);
		return this.train.getLocomotive();
	}
	
	
	@Override
	protected void executeAction() {
		//si cette action est nulle rien va etre executer
		Action actionExcute = actions.actionToExecute();
		
		if(actionExcute.equals(Action.Tirer)) {
			this.tirer();
		}
		
		if(!wagon.isLastWagon() && actionExcute.equals(Action.Avance)) {
			Train.Wagon newWagon = wagon.avanceMarshall();
			if(newWagon == null) System.err.println("OUUPS CHECK1 executeAction  Marshall");
			System.out.println(getName()+" avance vers la fin de train");
			wagon =  newWagon;
			System.out.println(wagon);
			return;
		}
		if(!wagon.isFirstWagon() && actionExcute.equals(Action.Recule)) {
			Train.Wagon newWagon =wagon.reculeMarshall();
			if(newWagon == null) System.err.println("OUUPS CHECK1 executeAction  Marshall");
			System.out.println(getName()+" recule vers le debut de train");
			wagon =  newWagon;
			System.out.println(wagon);
			return;
		}
		System.out.println(getName()+ " will do nothing now!");
	}
	
	public void tirer() {
		
		Set<Bandit> bandits = null;
		//System.out.println(this.wagon.toString());
		
		//this.train.hashCode();
		//wagon.getBandits();
		
		if(bandits.isEmpty()) {
			System.out.println(this.getName() + " has shot no body");
		}
		else {
			// On enlève un butin au hasard
			int size = bandits.size();
			int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
			int i = 0;
			Bandit touche = null;
			for(Bandit b : bandits) {
			    if (i == item) {
			    	touche = b;
			        bandits.remove(touche);
			    i++;
			    }
			}
			System.out.println(this.getName() + " has shot " + touche.getName());
			if(!touche.isEmpty())
				wagon.addButin(touche.popButin());
		}
	}


}
