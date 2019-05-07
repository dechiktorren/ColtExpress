package modele;

import java.util.Stack;

public abstract class ContainerStack {
	private Stack<Butin> butins;
	private final int  MAX_CAPACITY;
	private int n ;
	ContainerStack(int maxN){
		setButins(new Stack<Butin>());
		n= 0;
		MAX_CAPACITY = maxN;
	}
	public void pushButin(Butin b ) {
		if(n>= this.MAX_CAPACITY) return;
		getButins().push(b);
		n++;
	}
	public Butin popButin() {
		if(getButins().empty()) return null ;
		n--;
		return getButins().pop();
		
	}
	public Butin peekButin() {
		return getButins().peek();
	}
	public boolean isEmpty() {
		return getButins().empty();
	}
	public Stack<Butin> getButins() {
		return butins;
	}
	public void setButins(Stack<Butin> butins) {
		this.butins = butins;
	}


}

