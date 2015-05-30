package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class FilT extends FilAbstrait {

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public FilT() {
        super();
        cotesConnectes[0] = 1;
        cotesConnectes[1] = 1;
        cotesConnectes[2] = 1;
        addImage("fil_t.png");
        addImageActif("fil_ton.png");
    }

    @Override
    public String toString() {
        return "FilT";
    }
}
