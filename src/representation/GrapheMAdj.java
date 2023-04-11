package representation;

/**
 * Classe représentant un graphe sous forme d'une matrice d'adjacence
 * @author MOGNE Youssouf-A., CHEVILY Pierre, BALASSOUPRAMANIEN Madavan, BANOL MORENO Marcell 101
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Type de donnée représentant un graphe sous forme d'une matrice d'adjacence*/
public class GrapheMAdj implements graphe.IGraphe{
	private int[][] matrice; 
	private Map<String, Integer> indices;
	
	private final int NON_VALUATION = -1; 
	
	/**
	 * Constructeur qui initialise un graphe représenté via une matrice d'adjacence
	 */
	public GrapheMAdj() {
		indices = new HashMap<String, Integer>();
	}

	/**
	 * Constructeur qui initialise un graphe à partir d'une chaîne
	 * @param s : chaîne indiquant les arcs du graphe
	 */
	public GrapheMAdj(String s) {
		this();
		peupler(s);
	}

	/**
	 * Renvoie tous les sommets du graphe
	 * @return liste de tous les sommets
	 */
	@Override
	public List<String> getSommets() {
		List<String> sommets = new ArrayList<String>();
		for (String sommet : indices.keySet()) {
			sommets.add(sommet);
		}
		return sommets;
	}

	/**
	 * Renvoie tous les successeurs d'un sommet
	 * @param sommet : sommet dont on veut connaître les successeurs
	 * @return liste des successeurs du sommet
	 */
	@Override
	public List<String> getSucc(String sommet) {
		List<String> successeurs = new ArrayList<String>();
		for (String dst : getSommets()) {
			if (getValuation(sommet, dst) != NON_VALUATION) {
				successeurs.add(dst);
			}
		}
		return successeurs;
	}

	/**
	 * Renvoie l'indice d'un sommet dans la matrice
	 * @pre On vérifie que le sommet existe dans la matrice
	 * @param sommet : sommet dont on veut l'indice
	 * @return l'indice du sommet
	 */
	private int indice(String sommet) {
		assert(contientSommet(sommet));
		return indices.get(sommet);
	}

	/**
	 * Renvoie la valuation entre deux sommets, -1 s'il n'existe pas d'arc entre les deux sommets
	 * @param src : sommet source
	 * @param dest : sommet destination
	 * @return la valuation entre les deux sommets
	 */
	@Override
	public int getValuation(String src, String dest) {
		assert(contientSommet(src) && contientSommet(dest));
		return matrice[indice(src)][indice(dest)];
	}

	/**
	 * Renvoie un booléen indiquant si le graphe contient un sommet ou non
	 * @param sommet : sommet que l'on veut vérifier
	 * @return true si le sommet existe dans le graphe, false sinon
	 */
	@Override
	public boolean contientSommet(String sommet) {
		return getSommets().contains(sommet);
	}

	/**
	 * Renvoie un booléen indiquant si le graphe contient un arc ou non
	 * @param src : sommet source
	 * @param dest : sommet destination
	 * @return true si un arc existe entre les deux sommets, false sinon
	 */
	@Override
	public boolean contientArc(String src, String dest) {
		if (contientSommet(src)) {
			return getSucc(src).contains(dest);
		}
		return false;
	}

	/**
	 * Renvoie le nombre de nombre de sommets du graphe
	 * @return le nombre de sommets
	 */
	private int nbSommets() {
		return getSommets().size();
	}

	/**
	 * Renvoie une nouvelle matrice copiant et agrandissant de 1 la précédente
	 * @return la nouvelle matrice
	 */
	private int[][] agrandirMatrice() {
		int [][] nvMat= new int[nbSommets()+1][nbSommets()+1] ;
		
		for (String src : getSommets()) {
			for (String dest: getSommets()) {
				nvMat[indice(src)][indice(dest)] = getValuation(src, dest);
			}
		}
		return nvMat;
	}

	/**
	 * Initialise les cases relatives à un nouveau sommet à NON_VALUATION dans la matrice
	 * @see ajouterSommet pour son utilisation
	 * @param indMax : l'indice du nouveau sommet
	 */
	private void majMatrice(int indMax) {
		for (int i = 0; i < indMax; ++i) {
			matrice[i][indMax] = NON_VALUATION;
			matrice[indMax][i] = NON_VALUATION;
		}
		
		matrice[indMax][indMax] = NON_VALUATION;
	}

	/**
	 * Ajoute un sommet au graphe si non déja présent
	 * @param noeud : sommet à ajouter
	 */
	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			matrice = agrandirMatrice();
			majMatrice(nbSommets());
			indices.put(noeud, nbSommets());
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
		matrice[indice(source)][indice(destination)] = valeur;
	}

	/**
	 * Renvoie une nouvelle Map avec les nouveaux indices pour chaque sommet,
	 * du fait de la suppression d'un sommet
	 * @param sommetSuppr : sommet supprimé 
	 * @return la nouvelle Map contenant les nouveaux indices
	 */
	private Map<String, Integer> nouveauxIndices(String sommetSuppr) {
		Map<String, Integer> nvIndices = new HashMap<String, Integer>();
		for (String sommet : getSommets()) {
			if (!sommet.equals(sommetSuppr)) {
				if (indice(sommet) > indice(sommetSuppr)) {
					nvIndices.put(sommet, indice(sommet)-1);
				}
				else {
					nvIndices.put(sommet, indice(sommet));
				}
			}
		}
		return nvIndices;
	}

	/**
	 * Renvoie une nouvelle matrice rétrécissant la précédente de 1
	 * @param noeudSuppr : sommet supprimé de la matrice
	 * @return la nouvelle matrice
	 */
	private int[][] retrecirMatrice(String noeudSuppr) {
		int [][] nvMat= new int[nbSommets()-1][nbSommets()-1];
		Map<String, Integer> nvIndices = nouveauxIndices(noeudSuppr);
		
		for (String src : getSommets()) {
			if (!src.equals(noeudSuppr)) {
				for (String dest : getSommets()) {
					if (!dest.equals(noeudSuppr)) {
						nvMat[nvIndices.get(src)][nvIndices.get(dest)] = getValuation(src, dest);
					}
				}
			}
		}
		return nvMat;
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
		matrice = retrecirMatrice(noeud);
		indices = nouveauxIndices(noeud);
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
		matrice[indice(source)][indice(destination)] = NON_VALUATION;
	}

	/**
	 * Renvoie une chaîne représentant le graphe
	 * @return chaîne représentant le graphe
	 */
	public String toString() {
		String s = "";
		boolean premier = true;
		
		List<String> sommetsTries = new ArrayList<String>(getSommets());
		Collections.sort(sommetsTries);
		
		for(String src : sommetsTries) {
			List<String> succTries = new ArrayList<String>(getSucc(src));
			Collections.sort(succTries);
			
			for (String dst : succTries) {
				if (premier) {
					s += src + "-" + dst + "(" + getValuation(src, dst) + ")";
					premier = false;
				}
				else {
					s += ", " + src + "-" + dst + "(" + getValuation(src, dst) + ")";
				}
			}
			if (succTries.isEmpty()) {
				if (premier) {
					s += src + ":";
					premier = false;
				}
				else {
					s += ", " + src + ":";
				}
			}
		}
		return s;
	}
}
