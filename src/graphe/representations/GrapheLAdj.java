package graphe.representations;

/**
 * Classe représentant un graphe sous forme d'une liste d'adjacence
 * @author MOGNE Youssouf-A., CHEVILY Pierre, BALASSOUPRAMANIEN Madavan, BANOL MORENO Marcell 101
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphe.core.Arc;


/** Type de donnée représentant un graphe sous forme d'une liste d'adjacence*/
public class GrapheLAdj implements graphe.core.IGraphe {
	/**Liste d'adjacence**/
	private Map<String, List<Arc>> ladj; 
	
	/**Constante correspondant à une non valuation*/
	private final int NON_VALUATION = -1;

	/**
	 * Constructeur qui initialise un graphe représenté via une liste d'adjacence
	 */
	public GrapheLAdj() {
		ladj = new HashMap<String, List<Arc>>();
	}

	/**
	 * Constructeur qui initialise un graphe à partir d'une chaîne
	 * @param s : chaîne indiquant les arcs du graphe
	 */
    public GrapheLAdj(String s) {
    	this();
    	peupler(s);
    }

	/**
	 * Renvoie tous les sommets du graphe
	 * @return liste de tous les sommets
	 */
	@Override
	public List<String> getSommets() {
		return new ArrayList<String>(ladj.keySet());
	}

	/**
	 * Renvoie tous les successeurs d'un sommet
	 * @param sommet : sommet dont on veut connaître les successeurs
	 * @return liste des successeurs du sommet
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
	 * Renvoie la valuation entre deux sommets, NON_VALUATION s'il n'existe pas d'arc entre les deux sommets
	 * @pre il faut que le graphe contienne les deux sommets
	 * @param src : sommet source
	 * @param dest : sommet destination
	 * @return la valuation entre les deux sommets
	 */
	@Override
	public int getValuation(String src, String dest) {
		assert(contientSommet(src) && contientSommet(dest));
		for (Arc arc: ladj.get(src)) {
			if (arc.getDestination().equals(dest)){
				return arc.getValuation();
			}
		}
		return NON_VALUATION;
	}

	/**
	 * Renvoie un booléen indiquant si le graphe contient un sommet ou non
	 * @param sommet : sommet que l'on veut vérifier
	 * @return true si le sommet existe dans le graphe, false sinon
	 */
	@Override
	public boolean contientSommet(String sommet) {
		return ladj.containsKey(sommet);
	}

	/**
	 * Renvoie un booléen indiquant si le graphe contient un arc ou non
	 * @param src : sommet source
	 * @param dest : sommet destination
	 * @return true si un arc existe entre les deux sommets, false sinon
	 */
	@Override
	public boolean contientArc(String src, String dest) {
		if (contientSommet(src) && contientSommet(dest)) {
			return getSucc(src).contains(dest);
		}
		return false;
	}

	/**
	 * Ajoute un sommet au graphe si non déja présent
	 * @param noeud : sommet à ajouter
	 */
	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			ladj.put(noeud, new ArrayList<Arc>());
		}
	}

	/**
	 * Ajoute un arc entre deux sommets, avec pour valuation une valeur donnée
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
	 * Permet d'enlever un sommet du graphe si présent dans ce dernier
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
	 * Permet d'enlever un arc entre deux sommets
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
	 * Renvoie une chaîne représentant le graphe
	 * @return chaîne représentant le graphe
	 */
	@Override
	public String toString() {
		return toAString();
	}
}
