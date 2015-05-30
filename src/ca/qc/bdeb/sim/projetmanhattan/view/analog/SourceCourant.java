package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import javafx.scene.image.Image;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class SourceCourant extends Connectable implements Composant {

    private double forceElectroMotrice;
    private double courant;

    /**
     * Initialise la Source de Courant et ses variables si nécessaire*
     */
    public SourceCourant() {
        super(TypeComposant.SOURCE_COURANT);
        imageActive = new Image("file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/source_courant.png");
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
    }

    /**
     *
     * @return le courant produit par la source
     */
    public double getCourant() {
        return courant;
    }

    /**
     *
     * @param courant attribue le courant produit par la source
     */
    public void setCourant(double courant) {
        this.courant = courant;
    }

    @Override
    public String toString() {
        return "sourceCourant";
    }

}
