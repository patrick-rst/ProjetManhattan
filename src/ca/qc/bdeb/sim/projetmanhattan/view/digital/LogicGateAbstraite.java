/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author blood_000
 */
public abstract class LogicGateAbstraite extends Connectable {

    protected byte entrees;
    protected static String imageFolder = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/";
    protected int imageIndex;
    protected ArrayList<Image> listeImages;
    
    public LogicGateAbstraite(TypeComposant typeComposant) {
        super(typeComposant);
        imageIndex = 0;
        listeImages = new ArrayList();
    }

    public void ajouterEntree() {
        ++entrees;
    }

    public void retirerEntree() {
        --entrees;
    }
    
    public void nextImage() {
        System.out.println((this.imageIndex+1)%listeImages.size());
        this.imageIndex = (this.imageIndex+1)%listeImages.size();
    }    
    
    public Image getImage() {
        return listeImages.get(imageIndex);
    }
    
    public void addImage(String filename) {
        listeImages.add(new Image(imageFolder + filename));
    }     
    

    

}
