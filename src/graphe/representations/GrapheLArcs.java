package graphe.representations;

/**
 * Classe représentant un graphe sous forme d'une liste d'arcs
 * @author MOGNE Youssouf-A., CHEVILY Pierre, BALASSOUPRAMANIEN Madavan, BANOL MORENO Marcell 101
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import graphe.core.Arc;

/** Type de donnée représentant un graphe sous forme d'une liste d'arcs*/
public class GrapheLArcs implements graphe.core.IGraphe {
	/**Liste d'arcs**/
    private List<Arc> arcs;
    
    /**Constante correspondant à une non valuation*/
    private final int NON_VALUATION = -1; 
    
    /**
	 * Constructeur qui initialise un graphe représenté via une liste d'arcs
	 */
    public GrapheLArcs() {
    	arcs = new ArrayList<Arc>();
    }
    
    
    /**
	 * Constructeur qui initialise un graphe à partir d'une chaîne
	 * @param s : chaîne indiquant les arcs du graphe
	 */
    public GrapheLArcs(String s) {
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
		for (Arc arc: arcs) {
			if (!sommets.contains(arc.getSource())) {
				sommets.add(arc.getSource());
			}
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
		List<String> succ = new ArrayList<String>();
		for(Arc arc: arcs) {
			if (arc.getSource().equals(sommet) && !succ.contains(arc.getDestination()) && !arc.getDestination().equals("")){
				succ.add(arc.getDestination());
			}
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
		for (Arc arc: arcs) {
			if (arc.getSource().equals(src) && arc.getDestination().equals(dest)) {
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
	 * Ajoute un sommet au graphe si non déja présent
	 * @param noeud : sommet à ajouter
	 */
	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			arcs.add(new Arc(noeud));
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
		ajouterSommet(destination);
		arcs.add(new Arc(source, destination, valeur));
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
		
		List<Arc> supprArc = new ArrayList<Arc>();
		for (Arc arc: arcs) {
			if (arc.getSource().equals(noeud) || arc.getDestination().equals(noeud)) {
				supprArc.add(arc);
			}
		}
		arcs.removeAll(supprArc);
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
		Arc supprArc = null;
		for (Arc arc: arcs) {
			if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
				supprArc = arc;
				break;
			}
		}
		arcs.remove(supprArc);
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
