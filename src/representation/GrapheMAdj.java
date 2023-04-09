package representation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheMAdj implements graphe.IGraphe{
	private int[][] matrice;
	private Map<String, Integer> indices;
	
	private final int NON_VALUATION = -1;
	
	public GrapheMAdj() {
		indices = new HashMap<String, Integer>();
	}
	
	public GrapheMAdj(String s) {
		this();
		peupler(s);
	}
	
	@Override
	public List<String> getSommets() {
		List<String> sommets = new ArrayList<String>();
		for (String sommet : indices.keySet()) {
			sommets.add(sommet);
		}
		return sommets;
	}
	
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
	
	private int indice(String sommet) {
		assert(contientSommet(sommet));
		return indices.get(sommet);
	}
	
	@Override
	public int getValuation(String src, String dest) {
		assert(contientSommet(src) && contientSommet(dest));
		return matrice[indice(src)][indice(dest)];
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
	
	private int nbSommets() {
		return getSommets().size();
	}
	
	
	private int[][] agrandirMatrice() {
		int [][] nvMat= new int[nbSommets()+1][nbSommets()+1] ;
		
		for (String src : getSommets()) {
			for (String dest: getSommets()) {
				nvMat[indice(src)][indice(dest)] = getValuation(src, dest);
			}
		}
		return nvMat;
	}
	
	private void majMatrice(int indMax) {
		for (int i = 0; i < indMax; ++i) {
			matrice[i][indMax] = NON_VALUATION;
			matrice[indMax][i] = NON_VALUATION;
		}
		
		matrice[indMax][indMax] = NON_VALUATION;
	}
	
	@Override
	public void ajouterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			matrice = agrandirMatrice();
			majMatrice(nbSommets());
			indices.put(noeud, nbSommets());
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
		matrice[indice(source)][indice(destination)] = valeur;
	}
	
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

	
	
	@Override
	public void oterSommet(String noeud) {
		if (!contientSommet(noeud)) {
			return;
		}
		matrice = retrecirMatrice(noeud);
		indices = nouveauxIndices(noeud);
	}
	
	@Override
	public void oterArc(String source, String destination) {
		if (!contientArc(source, destination)) {
			throw new IllegalArgumentException("arc non present");
		}
		matrice[indice(source)][indice(destination)] = NON_VALUATION;
	}
	
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