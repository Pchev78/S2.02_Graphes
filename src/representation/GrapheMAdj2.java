package representation;

import java.util.*;

public class GrapheMAdj2 implements graphe.IGraphe{
    private int[][] matrice;
    private Map<String, Integer> indices;
    private final int NON_VALUATION = -1; // Valeur pour laquelle une valuation ne sera pas considérée

    public GrapheMAdj2() {
        indices = new HashMap<String, Integer>();
    }

    /**
     * @brief 2ᵉ constructeur de la classe GrapheMAdj2, en supposant qu'on nous fournisse des arcs
     * @param s : chaîne contenant les arcs fournis
     */
    public GrapheMAdj2(String s) {
        this();
        peupler(s);
    }

    /**
     * @brief Getter des sommets
     * @return tous les sommets de la matrice
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
     * @brief Permet d'avoir tous les successeurs d'un sommet
     * @param sommet : sommet dont on veut connaître les successeurs
     * @return les successeurs du sommet
     */
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

    /**
     * @brief Permet d'avoir l'indice d'un sommet dans la matrice
     * @pre On vérifie que le sommet existe dans la matrice
     * @param sommet : sommet dont on veut l'indice
     * @return l'indice du sommet
     */
    private int indice(String sommet){
        assert(contientSommet(sommet));
        return indices.get(sommet);
    }

    /**
     * @brief Permet de donner la valuation entre 2 sommets
     * @param src : sommet source
     * @param dest : sommet destination
     * @return la valuation entre les 2 sommets
     */
    @Override
    public int getValuation(String src, String dest) {
        return matrice[indice(src)][indice(dest)];
    }

    /**
     * @param sommet : sommet que l'on veut vérifier
     * @return true si le sommet existe dans la matrice, false sinon
     */
    @Override
    public boolean contientSommet(String sommet) {
        return indices.containsKey(sommet);
    }

    /**
     * @param src : sommet source
     * @param dest : sommet destination
     * @return true si un arc existe entre les 2 sommets, false sinon
     */
    @Override
    public boolean contientArc(String src, String dest) {
        if (contientSommet(src) && contientSommet(dest)){
            return getValuation(src,dest) != NON_VALUATION;
        }
        return false;
    }

    /**
     * @return le nombre de sommets de la matrice
     */
    private int nbSommets (){
        return getSommets().size();
    }

    /**
     * @brief Permet de copier une matrice dans une nouvelle
     * @see agrandirMatrice pour son utilisation
     * @param nouvelleMatrice : matrice dans laquelle on veut copier l'ancienne matrice
     */
    private void copierMatrice(int[][] nouvelleMatrice) {
        for (String src : getSommets()) {
            for (String dest : getSommets()) {
                nouvelleMatrice[indice(src)][indice(dest)] = matrice[indice(src)][indice(dest)];
            }
        }
    }

    /**
     * @brief Agrandit une matrice de la taille nbSommets à la taille nbSommets + 1
     * @return la matrice agrandie
     */
    private int[][] agrandirMatrice(){
        int[][] nouvelleMatrice = new int[nbSommets() + 1][nbSommets() + 1];
        copierMatrice(nouvelleMatrice);
        return nouvelleMatrice;
    }

    /**
     * @brief Permet de mettre les cases relatives à un nouveau sommet à NON_VALUATION
     * @see ajouterSommet pour son utilisation
     * @param indMax : l'indice du nouveau sommet
     */
    private void majMatrice(int indMax){
        for (int i = 0; i < indMax; i++){
            matrice[i][indMax] = NON_VALUATION;
            matrice[indMax][i] = NON_VALUATION;
        }
        matrice[indMax][indMax] = NON_VALUATION;
    }

    /**
     * @brief Ajoute un sommet à la matrice
     * @param noeud : sommet à ajouter
     */
    @Override
    public void ajouterSommet(String noeud) {
        if (!contientSommet(noeud)){
            matrice = agrandirMatrice();
            majMatrice(nbSommets());
            indices.put(noeud, nbSommets());
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
        matrice[indice(source)][indice(destination)] = valeur;
    }

    /**
     * @brief Permet de créer une nouvelle Map avec de nouveaux indices,
     * du fait de la suppression d'un sommet de la matrice
     * @param noeudSuppr : sommet supprimé de la matrice
     * @return la nouvelle Map contenant les nouveaux indices
     */
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

    /**
     * @brief Permet de rétrécir la taille d'une matrice,
     * passant de nbSommets à nbSommets - 1
     * @param noeudSuppr : sommet supprimé de la matrice
     * @return la nouvelle matrice
     */
    private int[][] retrecirMatrice(String noeudSuppr){
        int[][] nouvelleMatrice = new int[nbSommets() - 1][nbSommets() - 1];
        Map<String, Integer> nvIndices = nouveauxIndices(noeudSuppr);

        for (String src : getSommets()) {
            for (String dest : getSommets()) {
                if (src != noeudSuppr && dest != noeudSuppr) {
                    nouvelleMatrice[nvIndices.get(src)][nvIndices.get(dest)] = getValuation(src, dest);
                }
            }
        }
        return nouvelleMatrice;
    }

    /**
     * @brief Permet d'enlever un sommet d'une matrice
     * @pre le sommet doit déjà exister dans la matrice
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
        matrice[indice(source)][indice(destination)] = NON_VALUATION;
    }

    /**
     * @return la matrice sous forme de String
     */
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