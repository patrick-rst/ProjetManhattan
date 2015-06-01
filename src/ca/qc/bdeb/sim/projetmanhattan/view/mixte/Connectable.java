package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

import java.io.Serializable;
import javafx.scene.image.Image;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public abstract class Connectable implements Serializable {

    /**
     * Les cotes qui sont connectables ==1 ou ==-1, selon qu'ils recoivent ou
     * donnent du courant. ==0 si le cote n'est pas connectable.
     */
    protected byte[] cotesConnectes;
    private final TypeComposant typeComposant;
    private double rotation;

    /**
     * L'image visible par l'utilisateur.
     */
    protected transient Image imageActive;

    /**
     * Initialise le connectable et ses variables
     *
     * @param typeComposant Le type de composant qui correspond au composant
     * réel (validation dans l'interface)
     */
    public Connectable(TypeComposant typeComposant) {
        cotesConnectes = new byte[4];
        this.typeComposant = typeComposant;
    }

    /**
     *
     * @return renvoie les cotes Qui sont connectables à d'autrers connectables
     * sur la grille (nord, est, sud, ouest)
     */
    public byte[] getCotesConnectes() {
        return cotesConnectes;
    }

    /* @Override
     private String toString() {
     return cotesConnectes[0] + " " + cotesConnectes[1] + " " + cotesConnectes[2] + " " + cotesConnectes[3] + " ";
     }*/
    /**
     * fait tourner les cotesConnectes dans le sens horaire
     */
    public void rotater() {
        byte temp = cotesConnectes[3];
        cotesConnectes[3] = cotesConnectes[2];
        cotesConnectes[2] = cotesConnectes[1];
        cotesConnectes[1] = cotesConnectes[0];
        cotesConnectes[0] = temp;
    }

    /**
     *
     * @return le type de composant utilisé pour la validation
     */
    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    /**
     *
     * @return l'image visible par l'utilisateur
     */
    public Image getImageActive() {
        return imageActive;
    }

    /**
     * Retourne la rotation du composant lorsqu'il est dans la grille
     * @return 
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Ajoute la rotation de la case dans la grille au composant
     * @param rotation 
     */
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

}
