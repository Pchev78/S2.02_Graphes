package representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements graphe.IGraphe{
	private Map<String, Map<String, Integer>> hhadj; // Table de hachage
	
	public GrapheHHAdj() {
		hhadj = new HashMap<String, Map<String, Integer>>();
	}

	/**
	 * @brief Getter des sommets
	 * @return tous les sommets de la table de hachage
	 */
	@Override
	public List<String> getSommets() {
		List<String> sommets = new ArrayList<String>();
		for (String sommet : hhadj.keySet()) {
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
		List<String> successeurs = new ArrayList<String>();
		for (String succ : hhadj.get(sommet).keySet()) {
			successeurs.add(succ);
		}
		return successeurs;
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
		if (contientArc(src, dest)) {
			return hhadj.get(src).get(dest);
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
	 * @brief Ajoute un sommet à la table de hachage
	 * @param noeud : sommet à ajouter
	 */
	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			hhadj.put(noeud, new HashMap<String, Integer>());
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
		hhadj.get(source).put(destination, valeur);
	}

	/**
	 * @brief Permet d'enlever un sommet de la table de hachage
	 * @pre le sommet donné en paramètre doit déjà exister dans la table de hachage
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
		hhadj.get(source).remove(destination);
	}

	/**
	 * @return la table de hachage sous forme de String
	 */
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
