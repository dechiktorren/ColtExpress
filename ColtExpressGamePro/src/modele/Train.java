package modele;
import java.util.HashSet;
import java.util.Set;
/*

^
|
|
|
|
|
|	* prison    *           *           *       *
|	*************************           *********
|	*Locomotive **   First  *           * Last	*
|	*************************...........*********
|
|____________Direction(wagon.suivant, Action.avancer) ______________>




 le train est une liste doublement chaine 
 Le modelisation a ete choisi comme ca car c'est plus proche de modele reel + conseil de prof
 Un train : un esemble de wagons connectes l'un et l'autre
 */


public class Train extends Observable
{
	/*
	 * Attributs
	 */
	//LE train est responsable de gerer le nbr de bandits + responsable de les creer
	final int MAX_NB_BANDITS = 3 ;
	private int NB_BANDITS =0 ;
	final int MAX_N_ACTION = 5;
	 private final int NB_WAGONS;
	private Wagon locomotive;
	private Wagon firstWagon;
	
	/*
	 * Constructeur 
	 * @param n : int 
	 * le nombre de wagon dans ce train.
	 * il y aura au moins un locomotive et un wagon
	 */
	
	public Train(int n){
		if(n <1) n =1;
		this.NB_WAGONS = n;
		setLocomotive(new Wagon(this,0));
		firstWagon = new Wagon(this,1);
		getLocomotive().setSuivant(firstWagon);
		firstWagon.precedent = getLocomotive();
		n -= 1;
		Wagon current = firstWagon;
		for(int i =0; i<n ;i++) {
			Wagon add = new Wagon(this,i+2);
			current.setSuivant(add);
			add.precedent = current;
			current = add;
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
		Wagon out = this.getLocomotive();
		while(out.getSuivant()!=(null)) {
			out = out.getSuivant();
		}
		out.getBandits().add(b);
		this.NB_BANDITS++;
		return out;
	}
	
	

	public String toString() {
		String out ="";
		out += this.getLocomotive();
		out += this.firstWagon;
		Wagon curent = this.firstWagon;
		while(curent.getSuivant()!=(null)) {
			curent = curent.getSuivant();
			out += curent;
		}
		return out;
	}
	
	public int getMAX_N_ACTION() {
		return this.MAX_N_ACTION;
	}
	
	
/*
^
|
|
|	            *           *           *     B *
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
		System.out.println("adding bandit");
		System.out.print(t);
		b1.addAction(Action.Recule);
		b1.addAction(Action.Descendre);
		m.addAction(Action.Avance);
		b1.executeAction();
		b1.executeAction();
		m.executeAction();
		
		m.addAction(Action.Tirer);
		m.executeAction();
		
		b1.addAction(Action.Tirer);
		b1.executeAction();
		System.out.println();
		System.out.println("Le train apres les actions");
		System.out.println();
		System.out.print(t);
		
	}
	
	
	
	public int getNB_WAGONS() {
		return NB_WAGONS;
	}



	public Wagon getLocomotive() {
		return locomotive;
	}
	public void setLocomotive(Wagon locomotive) {
		this.locomotive = locomotive;
	}



	public class Wagon extends Possesseur
	{
		private Train train;
		private Wagon suivant;
		private Wagon precedent;
		private Set<Bandit> bandits;
		private Marshall marshall; // vaut null si le marshall n'est pas dans le wagon
		private int ordre; // pour les test unitaire
		
		public Wagon(Train t, int o){
			super(10);
			train = t;
			bandits = new HashSet<Bandit>();
			ordre = o;
			marshall = null;
		}
		
		public boolean isLastWagon() {
			return  this.getSuivant()==(null);
		}
		public boolean isFirstWagon() {
			return this==(train.firstWagon);
		}
		public boolean isLoco() {
			return this==(train.getLocomotive());
		}
		
		// pourquoi retourner un wagon
		public Wagon avanceMarshall() {
			if(!this.isMarshall()) return null; 
			this.suivant.marshall = this.marshall;
			this.marshall = null;
			return this.suivant;
		}
		
		public Wagon reculeMarshall() {
			if(!this.isMarshall()) return null;//si le marshall n'est pas ici 
			this.precedent.marshall = this.marshall;
			this.marshall = null;
			return this.precedent;
		}
		public Wagon avanceBandit(Bandit bandit) {
			this.bandits.remove(bandit);
			this.suivant.bandits.add(bandit);	
			return this.suivant;
		}
		public Wagon reculeBandit(Bandit bandit) {
			this.bandits.remove(bandit);
			this.precedent.bandits.add(bandit);
			return this.precedent;
		}
		
		public String toString() {
			String marsh = (this.isMarshall())?" ":" not ";
			String out = "Wagon contains"+marsh+"marshall"+ordre+":\n"+
					"		Bandits" + getBandits() + "\n";
			return out;
			
		}
		
		public Bandit anotherBanditThan(Personne p) {
			/*if(bandits.size()<=1) {
				System.err.println("Wagon : nobody here");
				return null;
			}*/
			for(Bandit b2 : this.bandits) {
				if(!b2.equals(p)) return b2;
			}
			return null;
		}
		
		
		public Set<Bandit> getBandits() {
			return bandits;
		}
		
		public void setBandits(Set<Bandit> bandits) {
			this.bandits = bandits;
		}

		public Wagon getSuivant() {
			return suivant;
		}
		public void setSuivant(Wagon suivant) {
			this.suivant = suivant;
		}

		// Le marshall est il dans le wagon ?
		public boolean isMarshall() {
			return marshall == null;
		}
		
		
		/* 
		 * Just to rename the abstract class and max = 8
		 */
		class Butins extends ContainerStack{

			Butins() {
				super(8);
			} 
		}


		public void setMarshall(Marshall m) {
			this.marshall = m;
		}
		public Marshall getMarshall() {
			return this.marshall;
		}


		
	
	}
	
}

