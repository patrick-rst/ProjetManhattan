package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import javafx.scene.image.Image;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class Resistance extends Connectable implements Composant {

    private final String imgPath = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/resistance.png";

    private double resistance;
    private double courant;

    public Resistance() {
        super(TypeComposant.RESISTANCE);
        imageActive = new Image("file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/resistance.png");
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getCourant() {
        return courant;
    }

    public void setCourant(double courant) {
        this.courant = courant;
    }

    @Override
    public String toString() {
        return "resistance";
    }

    public String getImgPath() {
        return imgPath;
    }

}
