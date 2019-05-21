package modele;

import java.util.Hashtable;
import java.util.Set;


public abstract class Personne extends Possesseur {
	protected final Train train;
	protected String name; // nom du bandit
	protected Train.Wagon wagon; // Le wagon dans lequel il se situe
	protected ActionList actions; // L'enemble des actions qui va prendre chaque tour max = 5
	
	
	public Personne(Train t, String name){
		super(5);
		this.train = t;
		this.wagon = positionInitiale(t, this); //method de la classe wagon rdv sa propre description
		actions = new ActionList(t.getMAX_N_ACTION()); //maximum  actions
		this.setName(name);
	}
	
	
	//this method will be used by the contructor and we will be redefined in each sub-class 
	//according to polymorphisme, the method applied in the contructor is the good one
	abstract Train.Wagon positionInitiale(Train t, Personne p); // return the wagon where p should be 
	
	abstract void executeAction(); 	//execute le premiere action s'il en a	
	
	public void addAction(Action a) {
		actions.addAction(a);
	}
	
	
	
	
	
	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}






	protected class ActionList {
		private Hashtable<Integer, Action> actions;
		private int MAX_N;
		private int index;
		ActionList(int n){
			MAX_N = n;
			index=0;
			actions = new Hashtable<Integer, Action>();
		}
		protected void addAction(Action act) {
			if(index>= MAX_N) return;
			this.actions.put(index, act);
			index++;
		}
		private int minSet(Set<Integer> s) {
			int out = this.MAX_N;
			for(int k : s) {
				if(out>k) out = k;
			}
			return out;
		}
		
		
		protected Action actionToExecute() {
			if(actions.size()==0) return null;
			int firstActionIndex = minSet(actions.keySet());
			Action out = actions.get(firstActionIndex);
			actions.remove(firstActionIndex);
			return out;
		}
		
		
		
	}
	
	
}
