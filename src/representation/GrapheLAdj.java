package representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Arc.Arc;

public class GrapheLAdj implements graphe.IGraphe {
	private Map<String, List<Arc>> ladj; // Liste d'adjacence

	public GrapheLAdj() {
		ladj = new HashMap<String, List<Arc>>();
	}

	/**
	 * @brief 2ᵉ constructeur de la classe GrapheMAdj2, en supposant qu'on nous fournisse des arcs
	 * @param s : chaîne contenant les arcs fournis
	 */
    public GrapheLAdj(String s) {
    	this();
    	peupler(s);
    }

	/**
	 * @brief Getter des sommets
	 * @return tous les sommets de la liste d'adjacence des arcs
	 */
	@Override
	public List<String> getSommets() {
		List<String> sommets = new ArrayList<String>();
		for (String sommet : ladj.keySet()){
			sommets.add(sommet);
		}
		return sommets;
	}

	/**
	 * @brief Permet d'avoir tous les successeurs d'un sommet
	 * @param sommet : sommet dont on veut connaître les successeurs
	 * @return les successeurs du sommet
	 */
	@Override
	public List<String> getSucc(String sommet) {
		List<String> succ = new ArrayList<String>();
		for (Arc arc: ladj.get(sommet) ) {
			succ.add(arc.getDestination());
		}
		return succ;
	}

	/**
	 * @brief Permet de donner la valuation entre 2 sommets
	 * @param src : sommet source
	 * @param dest : sommet destination
	 * @return la valuation entre les 2 sommets
	 */
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

	/**
	 * @param sommet : sommet que l'on veut vérifier
	 * @return true si le sommet existe dans la liste, false sinon
	 */
	@Override
	public boolean contientSommet(String sommet) {
		return getSommets().contains(sommet);
	}

	/**
	 * @param src : sommet source
	 * @param dest : sommet destination
	 * @return true si un arc existe entre les 2 sommets, false sinon
	 */
	@Override
	public boolean contientArc(String src, String dest) {
		if (contientSommet(src)) {
			return getSucc(src).contains(dest);
		}
		return false;
	}

	/**
	 * @brief Ajoute un sommet à la liste d'adjacence des arcs
	 * @param noeud : sommet à ajouter
	 */
	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			ladj.put(noeud, new ArrayList<Arc>());
		}
	}

	/**
	 * @brief Ajoute un arc entre 2 sommets, avec pour valuation une valeur donnée
	 * @pre il ne faut pas que l'arc soit déjà présent
	 * @pre il ne faut pas que la valeur donnée en paramètre soit négative
	 * @param source : sommet duquel part l'arc
	 * @param destination : sommet vers lequel arrive l'arc
	 * @param valeur : valuation de l'arc
	 */
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
		ladj.get(source).add(new Arc(source, destination, valeur));
	}

	/**
	 * @brief Permet d'enlever un sommet de la liste d'adjacence des arcs
	 * @pre le sommet donné en paramètre doit déjà exister dans la liste d'adjacence des arcs
	 * @param noeud : sommet à supprimer
	 */
	@Override
	public void oterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			return;
		}
		ladj.remove(noeud);
		for (String sommet : getSommets()) {
			for (Arc arc: ladj.get(sommet)) {
				if (arc.getDestination().equals(noeud)) {
					ladj.get(sommet).remove(arc);
				}
			}
		}
	}

	/**
	 * @brief Permet d'enlever un arc entre 2 sommets
	 * @pre il faut que l'arc existe déjà
	 * @param source : sommet duquel part l'arc
	 * @param destination : sommet vers lequel va l'arc
	 */
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

	/**
	 * @return la liste d'adjacence des arcs sous forme de String
	 */
	public String toString() {
		List <String> sommetsTries= new ArrayList<String>(ladj.keySet());
		Collections.sort(sommetsTries);
		
		boolean premier = true;
		String s = "";
		
		for (String sommet: sommetsTries) {
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
			
			if(arcTries.isEmpty()) {
				if (premier) {
					s += "sommet" + ":";
					premier = false;
				}
				else{
					s += ", " + sommet + ":";
				}
			}
			
		}
		return s;
	}
	
}
