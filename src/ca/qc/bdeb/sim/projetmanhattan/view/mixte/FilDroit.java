package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class FilDroit extends FilAbstrait {

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public FilDroit() {
        super();
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
        addImage("fil_droit.png");
        addImageActif("fil_droiton.png");
    }

    @Override
    public String toString() {
        return "FilDroit";
    }

}
