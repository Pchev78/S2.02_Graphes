package representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements graphe.IGraphe{
	private Map<String, Map<String, Integer>> hhadj;
	
	public GrapheHHAdj() {
		hhadj = new HashMap<String, Map<String, Integer>>();
	}
	
	@Override
	public List<String> getSommets() {
		List<String> sommets = new ArrayList<String>();
		for (String sommet : hhadj.keySet()) {
			sommets.add(sommet);
		}
		return sommets;
	}

	@Override
	public List<String> getSucc(String sommet) {
		List<String> successeurs = new ArrayList<String>();
		for (String succ : hhadj.get(sommet).keySet()) {
			successeurs.add(succ);
		}
		return successeurs;
	}

	@Override
	public int getValuation(String src, String dest) {
		assert(contientSommet(src) && contientSommet(dest));
		if (contientArc(src, dest)) {
			return hhadj.get(src).get(dest);
		}
		return -1;
	}

	@Override
	public boolean contientSommet(String sommet) {
		return getSommets().contains(sommet);
	}

	@Override
	public boolean contientArc(String src, String dest) {
		if (contientSommet(src)) {
			return getSucc(src).contains(dest);
		}
		return false;
	}

	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			hhadj.put(noeud, new HashMap<String, Integer>());
		}
	}

	@Override
	public void ajouterArc(String source, String destination, Integer valeur) {
		if (contientArc(source, destination)) {
			throw new IllegalArgumentException("arc deja present");
		}
		if (valeur < 0) {
			throw new IllegalArgumentException("valuation negative");
		}
		ajouterSommet(source);
		ajouterSommet(destination);
		hhadj.get(source).put(destination, valeur);
	}

	@Override
	public void oterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			return;
		}
		hhadj.remove(noeud);
		
		for (String src : getSommets()) {
			if (contientArc(src, noeud)) {
				hhadj.get(src).remove(noeud);
			}
		}
	}

	@Override
	public void oterArc(String source, String destination) {
		if (!contientArc(source, destination)) {
			throw new IllegalArgumentException("arc non present");
		}
		hhadj.get(source).remove(destination);
	}
	
	public String toString() {
		List<String> sommetsTries = new ArrayList<String>(getSommets());
		Collections.sort(sommetsTries);
		
		boolean premier = true;
		String s = "";
		
		for (String sommet: sommetsTries) {
			List <String> destTries = new ArrayList<String>(getSucc(sommet));
			Collections.sort(destTries);
			
			for (String dest : destTries) {
				if (premier) {
					s += sommet + "-" + dest + "(" + getValuation(sommet, dest) + ")";
					premier = false;
				}
				else {
					s += ", " + sommet + "-" + dest + "(" + getValuation(sommet, dest) + ")";
				}
			}
			
			if (destTries.isEmpty()) {
				if (premier) {
					s += sommet + ":";
					premier = false;
				}
				else {
					s += ", " + sommet + ":";
				}
			}
			
		}
		return s;
	}
}
