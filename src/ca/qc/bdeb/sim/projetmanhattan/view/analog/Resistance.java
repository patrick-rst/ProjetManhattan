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

    /**
     * Initialise la résistance et ses variables si nécessaire*
     */
    public Resistance() {
        super(TypeComposant.RESISTANCE);
        imageActive = new Image("file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/resistance.png");
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
    }

    /**
     *
     * @return la résistance du composant en Ohms
     */
    public double getResistance() {
        return resistance;
    }

    /**
     *
     * @param resistance Attribue la résistance du composant
     */
    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    /**
     *
     * @return le courant qui passe à travers la résistance
     */
    public double getCourant() {
        return courant;
    }

    /**
     *
     * @param courant attribue le courant qui passe à travers la résistance
     */
    public void setCourant(double courant) {
        this.courant = courant;
    }

    @Override
    public String toString() {
        return "resistance";
    }

    /**
     *
     * @return le chemin d'acces à l'image de la résistance
     */
    public String getImgPath() {
        return imgPath;
    }

}
