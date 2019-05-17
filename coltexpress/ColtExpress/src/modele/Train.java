package modele;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
/*

^
|
|
|
|
|
|	* prison    *           *           *     B *
|	*************************           *********
|	*Locomotive **   First  *           * Last	*
|	*************************...........*********
|
|____________Direction(wagon.suivant, Action.avancer) ______________>




 le train c'est une liste doublement chaine 
 Le modelisation a ete choisi comme ca car c'est plus proche de modele reel + conseil de prof
 Un train : un esemble de wagons connectes l'un et l'autre
 */


public class Train
{
	/*
	 * Attributs
	 */
	//LE train est responsable de gerer le nbr de bandits + responsable de les creer
	private final int MAX_NB_BANDITS = 3 ;
	private int NB_BANDITS =0 ;
	private final int MAX_N_ACTION = 5;
	private final int MAX_N_BUTIN = 5;
	private final int NB_WAGONS_MAX = 5;
	private final double NERVOISITE_MARSHALL = 0.3;
	private Wagon locomotive;
	private Wagon firstWagon;
	
	/*
	 * Constructeur 
	 * @param n : int 
	 * le nombre de wagon dans ce train il y aura au moins un locomotive et un wagon
	 */
	public Train(int n){
		if(n <1) n =1;
		if(n>this.NB_WAGONS_MAX ) n = this.NB_WAGONS_MAX;
		locomotive = new Wagon(this,0);
		firstWagon = new Wagon(this,1);
		locomotive.suivant = firstWagon;
		firstWagon.precedent = locomotive;
		n -= 1;
		Wagon current = firstWagon;
		this.addButins(current);
		for(int i =2; i<n ;i++) {
			Wagon addedWagon = new Wagon(this,i);
			current.suivant = addedWagon;
			addedWagon.precedent = current;
			current = addedWagon;
			this.addButins(current);
		}
	}
	public Train() {
		this(4);
	}

	/*
	 * Cette fonction permet a un bandit de sauter sur le dernier wagon de train
	 */
	public Wagon banditLastWagon(Bandit b) {
		if(this.NB_BANDITS >= this.MAX_NB_BANDITS) return null;
		Wagon out = this.locomotive;
		while(out.suivant!=(null)) {
			out = out.suivant;
		}
		out.bandits.add(b);
		this.NB_BANDITS++;
		return out;
	}
	
	/*
	 * Cette fonction permet a un Marshall de monter sur le locomotive de train
	 */
	public Wagon marshaLocomotive(Marshall b) {
		this.locomotive.marshall = true;
		return this.locomotive;
	}
	public String toString() {
		String out ="";
		out += this.locomotive;
		out += this.firstWagon;
		Wagon curent = this.firstWagon;
		while(curent.suivant!=(null)) {
			curent = curent.suivant;
			out += curent;
		}
		return out;
	}
	
	public int getMAX_N_ACTION() {
		return this.MAX_N_ACTION;
	}
	public int getMAX_N_BUTIN() {
		return this.MAX_N_BUTIN;
	}
	
	
	/*
	 * Gestion butin
	 */
	
	/*
	 * Ajouter des butins au hasard entre 1 et 4
	 */
	public static void print(String s) {
		System.out.println(s);
	}
	private void addButins(Wagon w) {
		Random rnd = new Random();
		int butinNbr = rnd.nextInt(MAX_N_BUTIN );
		Butin b = new Bourse(w);
		w.addButin(b);
		for(int i = 0; i< butinNbr; i++) {
			int butinType = rnd.nextInt(2);
			b = (butinType ==0 )?  new Bijou(w) : new Bourse(w);
			w.addButin(b);
		}
		 
	}
	
/*
^
|
|
|	* prison    *           *           *     B *
|	*************************           *********
|	*Locomotive **   First  *           * Last	*
|	*************************...........*********
|
|____________Direction(wagon.suivant, Action.avancer) ______________>

 */
	
	public static void main(String args[]) {
		Train t = new Train(2);
		System.out.print(t);
		Bandit b1 = new Bandit(t,"Jean");
		Marshall m = new Marshall(t, "Marshall");
		System.out.println("adding a bandit");
		System.out.print(t);
		b1.addAction(Action.Descendre);
		b1.addAction(Action.Braquer);
		m.addAction(Action.Avance);
		b1.executeAction();
		b1.executeAction();
		m.executeAction();
		System.err.println("Le train apres les actions");
		System.out.println();
		System.out.print(t);	
		m.addAction(Action.Tirer);
		m.executeAction();
		
		b1.addAction(Action.Tirer);
		b1.executeAction();
		System.err.println("Le train apres les actions 2");
		System.out.println();
		System.out.print(t);	
		System.out.println();
			
	}
	
	
	
	protected class Wagon extends Possesseur
	{
		private Train train;
		private Wagon suivant;
		private Wagon precedent;
		private Set<Bandit> bandits;
		private boolean marshall;
		private int ordre; //utile pour les test unitaire
		public Wagon(Train t, int o){
			super(t.getMAX_N_BUTIN());
			train =t;
			bandits = new HashSet<Bandit>(0);
			ordre = o;
		}
		
		public boolean isLastWagon() {
			return  this.suivant==(null);
		}
		public boolean isFirstWagon() {
			return this==(train.firstWagon);
		}
		public boolean isLoco() {
			return this==(train.locomotive);
		}
		public Wagon avanceMarshall() {
			if(!this.marshall) return null;
			this.marshall = false;
			this.suivant.marshall = true;	
			return this.suivant;
		}
		public Wagon reculeMarshall() {
			if(!this.marshall) return null;//si le marshall n'est pas ici 
			this.marshall = false;
			this.precedent.marshall = true;	
			return this.precedent;
		}
		public Wagon avanceBandit(Bandit bandit) {
			bandits.remove(bandit);
			this.suivant.bandits.add(bandit);	
			return this.suivant;
		}
		public Wagon reculeBandit(Bandit bandit) {
			bandits.remove(bandit);
			this.precedent.bandits.add(bandit);
			return this.precedent;
		}
		
		public String toString() {
			String marsh = (this.marshall)?" contains marshall":" ";
			String out = "Wagon '"+ordre+"'"+ marsh +":\n"+
					"		Bandits" + bandits + ".\n";
			out += "		"+super.toString() + "\n";
			return out;
			
		}
		
		
		public Butin stoleButin() {
			return super.popButin();
		}
		public Bandit anotherBanditThan(Personne p) {
			/*if(bandits.size()<=1) {
				System.err.println("Wagon : nobody here");
				return null;
			}*/
			for(Bandit b2 : bandits) {
				if(!b2.equals(p)) return b2;
			}
			return null;
		}
		


		
	
	}
	
}

