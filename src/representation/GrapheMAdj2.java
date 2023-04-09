package representation;

import java.util.*;

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
        for (String succ : getSommets()) {
            if(getValuation(sommet, succ) != NON_VALUATION) {
                successeurs.add(succ);
            }
        }
        return successeurs;
    }

    private int indice(String sommet){
        assert(contientSommet(sommet));
        return indices.get(sommet);
    }

    @Override
    public int getValuation(String src, String dest) {
        return matrice[indice(src)][indice(dest)];
    }

    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    @Override
    public boolean contientArc(String src, String dest) {
        if (contientSommet(src) && contientSommet(dest)){
            return getValuation(src,dest) != NON_VALUATION;
        }
        return false;
    }

    private int nbSommets (){
        return getSommets().size();
    }

    private void copierMatrice(int[][] nouvelleMatrice) {
        for (String src : getSommets()) {
            for (String dest : getSommets()) {
                nouvelleMatrice[indice(src)][indice(dest)] = matrice[indice(src)][indice(dest)];
            }
        }
    }

    private int[][] agrandirMatrice(){
        int[][] nouvelleMatrice = new int[nbSommets() + 1][nbSommets() + 1];
        copierMatrice(nouvelleMatrice);
        return nouvelleMatrice;
    }

    private void majMatrice(int indMax){
        for (int i = 0; i < indMax; i++){
            matrice[i][indMax] = NON_VALUATION;
            matrice[indMax][i] = NON_VALUATION;
        }
        matrice[indMax][indMax] = NON_VALUATION;
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)){
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

    private Map<String, Integer> nouveauxIndices(String noeudSuppr){
        Map<String, Integer> nouveauxIndices = new HashMap<String, Integer>();
        for (String sommet : getSommets()){
            if (sommet != noeudSuppr) {
                if(indice(sommet) > indice(noeudSuppr))
                    nouveauxIndices.put(sommet, indice(sommet) - 1);
                else
                    nouveauxIndices.put(sommet, indice(sommet));
            }
        }
        return nouveauxIndices;
    }

    private int[][] retrecirMatrice(String noeaudSuppr){
        int[][] nouvelleMatrice = new int[nbSommets() - 1][nbSommets() - 1];
        Map<String, Integer> nvIndices = nouveauxIndices(noeaudSuppr);

        for (String src : getSommets()) {
            for (String dest : getSommets()) {
                if (src != noeaudSuppr && dest != noeaudSuppr) {
                    nouvelleMatrice[nvIndices.get(src)][nvIndices.get(dest)] = getValuation(src, dest);
                }
            }
        }
        return nouvelleMatrice;
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

    public String toString(){
        String s = "";
        List<String> sommetsTries = new ArrayList<String>(getSommets());
        Collections.sort(sommetsTries);

        boolean premier = true;

        for (String src : sommetsTries) {
            List<String> succTries = new ArrayList<String>(getSucc(src));
            Collections.sort(succTries);

            if (succTries.isEmpty()) {
                if (!premier){
                    s += ", ";
                }
                s += src + ":";
                premier = false;
            }

            for (String dest : succTries) {
                if (!premier){
                    s += ", ";
                }
                s += src + "-" + dest + "(" + getValuation(src,dest) + ")";
                premier = false;
            }
        }
        return s;
    }
}