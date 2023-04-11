package Arc;

/**
 * Classe représentant un arc
 * @author MOGNE Youssouf-A., CHEVILY Pierre, BALASSOUPRAMANIEN Madavan, BANOL MORENO Marcell 101
 */

/** Type de donnée représentant un arc*/
public class Arc implements Comparable<Arc>{
    private String source;
    private String destination;
    private int valuation;
    
    /**
	 * Constructeur qui initialise un arc factice à partir d'un sommet
	 */
    public Arc(String noeud) {
    	source = noeud;
    	destination = "";
    	valuation = 0;
    }
    
    /**
     * Constructeur qui initialise un arc entre deux sommets avec une valuation
     * @param source : chaîne représentant le sommet source
     * @param destination : chaîne représentant le sommet destination
     * @param valuation : valeur de l'arc
     */
    public Arc(String source, String destination, int valuation) {
    	this.source = source;
    	this.destination = destination;
    	this.valuation = valuation;
    }
    
    /**
     * Renvoie le sommet source d'un arc
     * @return la source de l'arc
     */
    public String getSource() {
    	return source;
    }
    
    /**
     * Renvoie le sommet destination d'un arc
     * @return la destination d'un arc
     */
    public String getDestination() {
    	return destination;
    }
    
    /**
     * Renvoie la valuation d'un arc
     * @return la valuation
     */
    public int getValuation() {
    	return valuation;
    }
    
    /**
     * Renvoie un booléen indiquant si deux arcs sont égaux ou non
     * @param ArcCompare : arc comparé à notre arc
     * @return true si les deux arcs sont égaux, false sinon
     */
    public boolean equals(Arc ArcCompare) {
    	return source.equals(ArcCompare.source) && destination.equals(ArcCompare.destination);
    }
    
    /**
     * Renvoie une chaîne représentant un arc
     * @return chaîne représentant l'arc
     */
    public String toString() {
    	if (destination == "") {
    		String s = source + ":";
    		return s;
    	}
    	String s = source + "-" + destination + "(" + valuation + ")";
    	return s;
    }
    
    /**
     * Compare un arc à un autre
     * @param o : arc auquel est comparé notre arc
     * @return une valeur positive si notre arc est devant dans l'ordre alphabétique,
     * 0 si les deux sont égaux, négative sinon
     */
	@Override
	public int compareTo(Arc o) {
		if (source.equals(o.getSource())) { //si les sommets sources sont égaux, on compare les destinations
			return destination.compareTo(o.destination);
		}
		return source.compareTo(o.getSource());
	}
}
