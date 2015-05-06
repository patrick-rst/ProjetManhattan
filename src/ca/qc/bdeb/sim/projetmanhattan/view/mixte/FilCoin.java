package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class FilCoin extends FilAbstrait {

    public FilCoin() {
        super();
        cotesConnectes[0] = 1;
        cotesConnectes[1] = 1;
        addImage("fil_coin.png");
        addImageActif("fil_coinon.png");
    }

    @Override
    public String toString() {
        return "FilCoin";
    }
}
