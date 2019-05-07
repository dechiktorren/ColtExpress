package modele;

import modele.Train.Wagon;

public class Marshall extends Personne{

	public Marshall(Train t, String name) {
		super(t, name);
	}

	@Override
	Wagon personneWagon(Train t, Personne p) {
		return t.marshaLocomotive(this);

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
	



}
