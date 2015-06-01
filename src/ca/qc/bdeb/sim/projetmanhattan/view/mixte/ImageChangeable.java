package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public abstract class ImageChangeable extends Connectable {

    /**
     * Le chemin d'acces qui contient les images
     */
    protected String imageFolder = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/";

    /**
     * Liste d'images qui n'ont pas de contour vert
     */
    protected transient ArrayList<Image> listeImages;

    /**
     * Liste d'images avec un contour vert
     */
    protected transient ArrayList<Image> listeImagesActif;

    /**
     * L'index de l'image en cours dans la liste
     */
    protected int imageIndex;

    /**
     * L'image en cours doit être dans listeImages ou ListeImagesActif
     */
    protected boolean actif;

    public ImageChangeable(TypeComposant typeComposant) {
        super(typeComposant);
        this.actif = false;
        this.listeImages = new ArrayList();
        this.listeImagesActif = new ArrayList();
        this.imageIndex = 0;
    }

    /**
     *
     * @return l'état de la variable 'actif' (on/off)
     */
    public abstract boolean isActif();

    /**
     * Ajouter une image non-active (pas de contour vert) à la liste
     * @param filename le nom de l'image
     */
    protected void addImage(String filename) {
        this.listeImages.add(new Image(this.imageFolder + filename));
    }

    /**
     * Ajouter une image active (contour vert) à la liste
     * @param filename le nom de l'image
     */
    protected void addImageActif(String filename) {
        this.listeImagesActif.add(new Image(this.imageFolder + filename));
    }

    /**
     * Augmente l'index de 1 et retourne à 0 lorsque plus grand que le nombre d'image
     */
    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.listeImages.size();
    }

    /**
     * Retourne l'image qui doit être utilisé par l'interface
     * @param actif true pour une image avec contour vert, false pour l'image sans contour
     * @return l'image en question
     */
    public Image getImage(boolean actif) {
        if (actif == true) {
            return this.listeImagesActif.get(this.imageIndex);
        } else {
            return this.listeImages.get(this.imageIndex);
        }

    }

    /**
     * Permet de dire si le composant est actif
     * @param actif true/false
     */
    public void setActif(boolean actif) {
        this.actif = actif;
    }
    
    
}
