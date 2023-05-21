package graphe.Dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graphe.core.IGrapheConst;

/**
 * Classe implémentant l'algorithme de Dijkstra
 * @author MOGNE Youssouf-A., CHEVILY Pierre, BALASSOUPRAMANIEN Madavan, BANOL MORENO Marcell 101
 *
 */
public class Dijkstra{
	
	/**Constante représentant une valeur infinie*/
	private final static int INFINI = Integer.MAX_VALUE-1;
	
	/**
	 * Initialise l'algorithme de Dijkstra, notamment les deux dictionnaires prev et dist.
	 * @param g graphe cible de Dijkstra
	 * @param source sommet de départ
	 * @param dist dictionnaire répertoriant pour chaque sommet, la plus courte distance entre ce dernier et la source
	 * @param prev dictionnaire répertoriant pour chaque sommet son prédécesseur dans le chemin avec la plus courte distance 
	 * entre la source et ce sommet
	 */
    private static void initDijkstra(IGrapheConst g, String source, Map<String, Integer> dist, Map<String, String> prev) {
        for (String s : g.getSommets()) {
            prev.put(s, null);
            dist.put(s, INFINI);
        }
        dist.put(source, 0);
    }

    /**
     * Retourne le sommet ayant jusqu'ici la plus courte distance dans le dictionnaire dist 
     * parmi les sommets qu'il reste à visiter
     * @param sAVisiter sommets restants à visiter 
     * @param dist dictionnaire répertoriant pour chaque sommet, la plus courte distance entre ce dernier et la source
     * @return sommet avec la distance minimum
     */
    private static String trvDistMin(List<String> sAVisiter, Map<String, Integer> dist){
        int distMin = Integer.MAX_VALUE;
        String sMin = null;

        for (String sommet : sAVisiter){
            if (dist.get(sommet) < distMin){
                distMin = dist.get(sommet);
                sMin = sommet;
            }
        }
        return sMin;
    }

    /**
     * Implémente l'algorithme de Dijkstra permettant de retrouver les plus courts chemin permettant d'aller 
     * d'un sommet source à un autre dans un graphe cible
     * @param g graphe cible de Dijkstra
	 * @param source sommet de départ
	 * @param dist dictionnaire répertoriant pour chaque sommet, la plus courte distance entre ce dernier et la source
	 * @param prev dictionnaire répertoriant pour chaque sommet son prédécesseur dans le chemin avec la plus courte distance 
	 * entre la source et ce sommet
     */
    public static void dijkstra(IGrapheConst g, String source, Map<String, Integer> dist, Map<String, String> prev) {

        initDijkstra(g, source, dist, prev);
        List <String> sAVisiter = new ArrayList<String>(dist.keySet());
        
        while (!sAVisiter.isEmpty()){
        	
            String sATraiter = trvDistMin(sAVisiter, dist);
            sAVisiter.remove(sATraiter);
            
            if (dist.get(sATraiter) != INFINI) {
	            for (String succ : g.getSucc(sATraiter)){
	                int nvDist = dist.get(sATraiter) + g.getValuation(sATraiter, succ);
	                if (nvDist < dist.get(succ)){
	                    dist.put(succ, nvDist);
	                    prev.put(succ, sATraiter);
	                }
	            }
            }
            else {
            	dist.remove(sATraiter);
            }
        }
    }
}
