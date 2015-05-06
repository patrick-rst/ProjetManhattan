package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public abstract class ImageChangeable extends Connectable {

    protected String imageFolder = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/";
    protected transient ArrayList<Image> listeImages;
    protected transient ArrayList<Image> listeImagesActif;
    protected int imageIndex;
    protected boolean actif;

    public ImageChangeable(TypeComposant typeComposant) {
        super(typeComposant);
        this.actif = false;
        this.listeImages = new ArrayList();
        this.listeImagesActif = new ArrayList();
        this.imageIndex = 0;
    }

    public abstract boolean isActif();

    public void addImage(String filename) {
        this.listeImages.add(new Image(this.imageFolder + filename));
    }

    public void addImageActif(String filename) {
        this.listeImagesActif.add(new Image(this.imageFolder + filename));
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.listeImages.size();
    }

    public Image getImage(boolean actif) {
        if (actif == true) {
            return this.listeImagesActif.get(this.imageIndex);
        } else {
            return this.listeImages.get(this.imageIndex);
        }

    }
}
