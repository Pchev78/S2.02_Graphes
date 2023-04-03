package Arc;


public class Arc implements Comparable<Arc>{
    private String source;
    private String destination;
    private int valuation;
    
    
    public Arc(String noeud) {
    	source = noeud;
    	destination = "";
    	valuation = 0;
    }
    
    public Arc(String source, String destination, int valuation) {
    	this.source = source;
    	this.destination = destination;
    	this.valuation = valuation;
    }
    
    public String getSource() {
    	return source;
    }
    
    public String getDestination() {
    	return destination;
    }
    
    public int getValuation() {
    	return valuation;
    }
    
    public boolean equals(Arc ArcCompare) {
    	return source.equals(ArcCompare.source) && destination.equals(ArcCompare.destination);
    }
    
    public String toString() {
    	if (destination == "") {
    		String s = source + ":";
    		return s;
    	}
    	String s = source + "-" + destination + "(" + valuation + ")";
    	return s;
    }

	@Override
	public int compareTo(Arc o) {
		return source.compareTo(o.getSource());
	}
    
   
}
