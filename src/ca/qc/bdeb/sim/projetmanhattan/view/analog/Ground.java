package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import javafx.scene.image.Image;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class Ground extends Connectable implements Composant {

    public Ground() {
        super(TypeComposant.GROUND);
        cotesConnectes[0] = 1;
        imageActive = new Image("file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/ground.png");
    }

}
