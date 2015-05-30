package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Composant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public interface ComposantDigital extends Composant {

    /**
     * Met à jour l'état du composant
     */
    public void updateActif();

    /**
     *
     * @param noeud ajoute un noeud qui envoie du courant à la liste
     */
    public void ajouterNoeudEntree(Noeud noeud);

    /**
     *
     * @param noeud ajoute un noeud qui recoit du courant à la liste
     */
    public void ajouterNoeudSortie(Noeud noeud);

    /**
     * Retourne l'etat du composant: on/off
     */
    public boolean isActif();
}
