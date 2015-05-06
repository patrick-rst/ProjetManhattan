package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Composant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public interface ComposantDigital extends Composant {

    public void updateActif();

    public void ajouterNoeudEntree(Noeud noeud);

    public void ajouterNoeudSortie(Noeud noeud);
}
