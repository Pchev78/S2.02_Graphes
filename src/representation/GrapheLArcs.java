package representation;

import java.util.ArrayList;
import java.util.List;
import Arc.Arc;

public class GrapheLArcs implements graphe.IGraphe {

    // Représenter sous forme de liste d'arcs
    private List<Arc> arcs;

    public GrapheLArcs(String s) {
        peupler(s);
    }

    @Override
    public List<String> getSommets() {
        List<String> sommets = new ArrayList<String>();
        for (Arc arc: arcs) {
            if (!sommets.contains(arc.getSource())) {
                sommets.add(arc.getSource());
            }
            if (!sommets.contains(arc.getDestination())) {
                sommets.add(arc.getDestination());
            }
        }
        return sommets;
    }

    @Override
    public List<String> getSucc(String sommet) {
        assert(contientSommet(sommet));
        List<String> succ = new ArrayList<String>();
        for(Arc arc: arcs) {
            if (arc.getSource().equals(sommet) && !succ.contains(arc.getDestination())){
                succ.add(arc.getDestination());
            }
        }
        return succ;
    }

    @Override
    public int getValuation(String src, String dest) {
        assert(contientArc(src, dest));
        for (Arc arc: arcs) {
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest)) {
                return arc.getValuation();
            }
        }
        return 0;
    }

    @Override
    public boolean contientSommet(String sommet) {
        return getSommets().contains(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        for (Arc arc: arcs) {
            if (arc.getSource().equals(src) && arc.getDestination().equals(dest)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (contientSommet(noeud)) {
            return;
        }
        else {
            arcs.add(new Arc(noeud));
        }
    }

    private boolean Destination(String sommet) {
        assert(contientSommet(sommet));
        for (Arc arc: arcs) {
            if (arc.getSource().equals(sommet) && arc.getDestination() != "") {
                return true;
            }
        }
        return false;
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        if (contientArc(source, destination)) {
            throw new IllegalArgumentException("arc déja present");
        }
        if (valeur < 0) {
            throw new IllegalArgumentException("valuation negative");
        }
        if (!Destination(source)) {
            oterSommet(source);
        }
        if (!Destination(destination)) {
            oterSommet(source);
        }
        arcs.add(new Arc(source, destination, valeur));
    }

    @Override
    public void oterSommet(String noeud) {
        if (!contientSommet(noeud)) {
            return;
        }
        for (Arc arc: arcs) {
            if (arc.getSource().equals(noeud) || arc.getDestination().equals(noeud)) {
                arcs.remove(arc);
            }
        }
    }

    @Override
    public void oterArc(String source, String destination) {
        if (contientArc(source, destination)) {
            throw new IllegalArgumentException("arc non present");
        }
        for (Arc arc: arcs) {
            if (arc.getSource().equals(source) && arc.getDestination().equals(destination)) {
                arcs.remove(arc);
            }
        }
    }

    public String toString() {
        //Collections.sort(arcs);
        String s = "";
        boolean premier = true;
        for (Arc arc: arcs) {
            if (premier) {
                s += arc.toString();
                premier = false;
            }
            else {
                s += ", " + arc.toString();
            }
        }
        return s;
    }

}