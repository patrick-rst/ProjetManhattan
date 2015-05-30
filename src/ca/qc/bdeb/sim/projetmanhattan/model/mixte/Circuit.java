package ca.qc.bdeb.sim.projetmanhattan.model.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public interface Circuit {

    /**
     * Remet la grille de composants à zéro en prévision de la prochaine analyse
     */
    public void wipe();

    /**
     * Analyse le circuit selon son type (statique ou dynamique)
     */
    public void analyserCircuit();

    /**
     * ajouter un noeud à la liste de noeuds
     *
     * @param noeud le noeud à ajouter
     */
    public void ajouterNoeud(Noeud noeud);
}
