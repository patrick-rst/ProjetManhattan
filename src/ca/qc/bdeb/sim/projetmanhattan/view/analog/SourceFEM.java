package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import javafx.scene.image.Image;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class SourceFEM extends Connectable implements Composant {

    private double forceElectroMotrice;
    private double courant;

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public SourceFEM() {
        super(TypeComposant.SOURCE_TENSION);
        imageActive = new Image("file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/source_tension.png");
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = -1;
    }

    /**
     *
     * @return la force electromotrice produite par la source
     */
    public double getForceElectroMotrice() {
        return forceElectroMotrice;
    }

    /**
     *
     * @param forceElectroMotrice attribue la force electromotrice produite par
     * la source
     */
    public void setForceElectroMotrice(double forceElectroMotrice) {
        this.forceElectroMotrice = forceElectroMotrice;
    }

    /**
     *
     * @return le courant qui passe à travers la source
     */
    public double getCourant() {
        return courant;
    }

    /**
     *
     * @param courant attribue le courant qui passe à travers la source
     */
    public void setCourant(double courant) {
        this.courant = courant;
    }

    @Override
    public String toString() {
        return "sourceTension";
    }

}
