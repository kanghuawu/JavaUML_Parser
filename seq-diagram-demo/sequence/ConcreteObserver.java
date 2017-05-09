package sequence;
// package java_uml_seq_parser;

public class ConcreteObserver implements Observer {
 
	protected String observerState;
	protected ConcreteSubject subject;

    public ConcreteObserver( ConcreteSubject theSubject )
    {
        this.subject = theSubject ;
    }
    
	public void update() {
	    // do nothing
	}

    public void showState()
    {
        System.out.println( "Observer: " + this.getClass().getName() + " = " + observerState );
    }
	 
}
 
