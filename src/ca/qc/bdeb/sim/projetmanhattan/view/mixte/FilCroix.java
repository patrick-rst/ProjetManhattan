package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class FilCroix extends FilAbstrait {

    public FilCroix() {
        super();
        cotesConnectes[0] = 1;
        cotesConnectes[1] = 1;
        cotesConnectes[2] = 1;
        cotesConnectes[3] = 1;
        addImage("fil_croix.png");
        addImageActif("fil_croixon.png");
    }

    @Override
    public String toString() {
        return "FilCroix";
    }

}
