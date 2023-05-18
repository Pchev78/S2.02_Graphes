package graphe.Dijkstra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import graphe.core.IGrapheConst;

public class Dijkstra{
	
	private final static int INFINI = Integer.MAX_VALUE-1;
	
    private static void initDijkstra(IGrapheConst g, String source, Map<String, Integer> dist, Map<String, String> prev) {
        for (String s : g.getSommets()) {
            dist.put(s, INFINI);
            prev.put(s, null);
        }
        dist.put(source, 0);
    }

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
