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

    public SourceFEM() {
        super(TypeComposant.SOURCE_TENSION);
imageActive = new Image("file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/source_tension.png");
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = -1;
    }

    public double getForceElectroMotrice() {
        return forceElectroMotrice;
    }

    public void setForceElectroMotrice(double forceElectroMotrice) {
        this.forceElectroMotrice = forceElectroMotrice;
    }

    public double getCourant() {
        return courant;
    }

    public void setCourant(double courant) {
        this.courant = courant;
    }

    @Override
    public String toString() {
        return "sourceTension";
    }

}
