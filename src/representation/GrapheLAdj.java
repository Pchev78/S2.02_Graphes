package representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Arc.Arc;

public class GrapheLAdj implements graphe.IGraphe {
	private Map<String, List<Arc>> ladj;

	public GrapheLAdj() {
		ladj = new HashMap<String, List<Arc>>();
	}
	
    public GrapheLAdj(String s) {
    	this();
    	graphe.IGraphe.super.peupler(s);
    }
    
	@Override
	public List<String> getSommets() {
		List<String> sommets = new ArrayList<String>();
		for (String sommet : ladj.keySet()){
			sommets.add(sommet);
		}
		return sommets;
	}

	@Override
	public List<String> getSucc(String sommet) {
		List<String> succ = new ArrayList<String>();
		for (Arc arc: ladj.get(sommet) ) {
			succ.add(arc.getDestination());
		}
		return succ;
	}

	@Override
	public int getValuation(String src, String dest) {
		assert(contientSommet(src) && contientSommet(dest));
		for (Arc arc: ladj.get(src)) {
			if (arc.getDestination().equals(dest)){
				return arc.getValuation();
			}
		}
		return -1;
	}

	@Override
	public boolean contientSommet(String sommet) {
		return ladj.containsKey(sommet);
	}

	@Override
	public boolean contientArc(String src, String dest) {
		if (contientSommet(src)) {
			for (Arc arc: ladj.get(src)) {
				if (arc.getDestination().equals(dest)){
					return true;
				}
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
			ladj.put(noeud, new ArrayList<Arc>());
		}
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		if (contientArc(source, destination)) {
			throw new IllegalArgumentException("arc déja present");
		}
		if (valeur < 0) {
			throw new IllegalArgumentException("valuation negative");
		}
		ajouterSommet(source);
		ajouterSommet(destination);
		ladj.get(source).add(new Arc(source, destination, valeur));
	}

	@Override
	public void oterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			return;
		}
		ladj.remove(noeud);
		for (String sommet : ladj.keySet()) {
			for (Arc arc: ladj.get(sommet)) {
				if (arc.getDestination().equals(noeud)) {
					ladj.get(sommet).remove(arc);
				}
			}
		}
	}

	@Override
	public void oterArc(String source, String destination) {
		if (!contientArc(source, destination)) {
			throw new IllegalArgumentException("arc non present");
		}
		for (Arc arc: ladj.get(source)){
			if (arc.getDestination().equals(destination)) {
				ladj.get(source).remove(arc);
			}
		}
	}

	public String toString() {
		List <String> sommetTries= new ArrayList<String>(ladj.keySet());
		Collections.sort(sommetTries);
		
		boolean premier = true;
		String s = "";
		
		for (String sommet: sommetTries) {
			List <Arc> arcTries = new ArrayList<Arc>(ladj.get(sommet));
			Collections.sort(arcTries);
			for (Arc arc: arcTries) {
				if (premier) {
					s += arc.toString();
					premier = false;
				}
				else {
					s += ", " + arc.toString(); 
				}
			}
		}
		return s;
	}
	
}
