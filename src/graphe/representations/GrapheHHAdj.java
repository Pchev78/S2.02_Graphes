package graphe.representations;

/**
 * Classe représentant un graphe sous forme d'une table de hachage
 * @author MOGNE Youssouf-A., CHEVILY Pierre, BALASSOUPRAMANIEN Madavan, BANOL MORENO Marcell 101
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Type de donnée représentant un graphe sous forme d'une table de hachage*/
public class GrapheHHAdj implements graphe.core.IGraphe{
	/**Table d'hachage**/
	private Map<String, Map<String, Integer>> hhadj;
	
	/**Constante correspondant à une non valuation*/
	private final int NON_VALUATION = -1;
	
	/**
	 * Constructeur qui initialise un graphe représenté via une table de hachage
	 */
	public GrapheHHAdj() {
		hhadj = new HashMap<String, Map<String, Integer>>();
	}
	
	/**
	 * Constructeur qui initialise un graphe à partir d'une chaîne
	 * @param s : chaîne indiquant les arcs du graphe
	 */
	public GrapheHHAdj(String s) {
		this();
		peupler(s);
	}

	/**
	 * Renvoie tous les sommets du graphe
	 * @return liste de tous les sommets
	 */
	@Override
	public List<String> getSommets() {
		return new ArrayList<String>(hhadj.keySet());
	}

	/**
	 * Renvoie tous les successeurs d'un sommet
	 * @param sommet : sommet dont on veut connaître les successeurs
	 * @return liste des successeurs du sommet
	 */
	@Override
	public List<String> getSucc(String sommet) {
		return new ArrayList<String>(hhadj.get(sommet).keySet());
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
		if (contientArc(src, dest)) {
			return hhadj.get(src).get(dest);
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
		return hhadj.containsKey(sommet);
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
			return hhadj.get(src).containsKey(dest);
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
			hhadj.put(noeud, new HashMap<String, Integer>());
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
		hhadj.get(source).put(destination, valeur);
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
		hhadj.remove(noeud);
		
		for (String src : getSommets()) {
			if (contientArc(src, noeud)) {
				hhadj.get(src).remove(noeud);
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
		hhadj.get(source).remove(destination);
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
