package representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Arc.Arc;

public class GrapheLArcs implements graphe.IGraphe {
    // Représenter sous forme de liste d'arcs
    private List<Arc> arcs;
    
    public GrapheLArcs() {
    	arcs = new ArrayList<Arc>();
    }
    
    public GrapheLArcs(String s) {
    	this();
    	peupler(s);
    }
    
	@Override
	public List<String> getSommets() {
		List<String> sommets = new ArrayList<String>();
		for (Arc arc: arcs) {
			if (!sommets.contains(arc.getSource())) {
				sommets.add(arc.getSource());
			}
		}
		return sommets;
	}

	@Override
	public List<String> getSucc(String sommet) {
		List<String> succ = new ArrayList<String>();
		for(Arc arc: arcs) {
			if (arc.getSource().equals(sommet) && !succ.contains(arc.getDestination())){
				succ.add(arc.getDestination());
			}
		}
		return succ;
	}

	@Override
	public int getValuation(String src, String dest) {
		assert(contientSommet(src) && contientSommet(dest));
		for (Arc arc: arcs) {
			if (arc.getSource().equals(src) && arc.getDestination().equals(dest)) {
				return arc.getValuation();
			}
		}
		return -1;
	}

	@Override
	public boolean contientSommet(String sommet) {
		return getSommets().contains(sommet);
	}

	@Override
	public boolean contientArc(String src, String dest) {
		for (Arc arc: arcs) {
			if (arc.getSource().equals(src) && arc.getDestination().equals(dest)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void ajouterSommet(String noeud) {
		if (contientSommet(noeud)) {
			return;
		}
		else {
			arcs.add(new Arc(noeud));
		}
	}

	private boolean noDestination(String sommet) {
		assert(contientSommet(sommet));
		for (Arc arc: arcs) {
			if (arc.getSource().equals(sommet) && arc.getDestination() == "") {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		if (contientArc(source, destination)) {
			throw new IllegalArgumentException("arc déja present");
		}
		if (valeur < 0) {
			throw new IllegalArgumentException("valuation negative");
		}
		if(contientSommet(source) && noDestination(source)) {
			oterArc(source, "");
		}
		arcs.add(new Arc(source, destination, valeur));
	}
	
	private void supprimer(List<Arc> supprArc) {
		for (Arc arc: supprArc) {
			arcs.remove(arc);
		}
	}

	@Override
	public void oterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			return;
		}
		List<Arc> supprArc = new ArrayList<Arc>();
		for (Arc arc: arcs) {
			if (arc.getSource().equals(noeud) || arc.getDestination().equals(noeud)) {
				supprArc.add(arc);
			}
		}
		supprimer(supprArc);
	}
	
	private void supprimer(Arc arc) {
		arcs.remove(arc);
	}

	@Override
	public void oterArc(String source, String destination) {
		if (!contientArc(source, destination)) {
			throw new IllegalArgumentException("arc non present");
		}
		Arc supprArc = null;
		for (Arc arc: arcs) {
			if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
				supprArc = arc;
				break;
			}
		}
		supprimer(supprArc);
	}
	
	public String toString() {
		Collections.sort(arcs);
		String s = "";
		boolean premier = true;
		for (Arc arc: arcs) {
			if (premier) {
				s += arc.toString();
				premier = false;
			}
			else {
				s += ", " + arc.toString();
			}
		}
		return s;
	}
	
}
