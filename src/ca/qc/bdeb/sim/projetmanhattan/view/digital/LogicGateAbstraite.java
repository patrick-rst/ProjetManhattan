/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author blood_000
 */
public abstract class LogicGateAbstraite extends Connectable implements ComposantDigital {


    protected byte entrees;
    protected static String imageFolder = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/";
    protected int imageIndex;
    protected transient ArrayList<Image> listeImages;
    

    protected Noeud noeudEntreeA;
    protected Noeud noeudEntreeB;
    protected Noeud noeudSortie;
    protected boolean actif;
    protected boolean actifTemp;


    public LogicGateAbstraite(TypeComposant typeComposant) {
        super(typeComposant);
        imageIndex = 0;
        listeImages = new ArrayList();
    }

    @Override
    public void ajouterNoeudEntree(Noeud noeud) {
        if (noeudEntreeA == null) {
            noeudEntreeA = noeud;
        } else if (noeudEntreeB == null) {
            noeudEntreeB = noeud;
        } else {
            System.out.println("Erreur: Logic Gate mal connectée");
        }
    }

    public abstract void calculerCourant();

    protected void transfererCourant() {
        if (actifTemp != actif) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            if (actif) {
                noeudSortie.augmenterTensionDigital();
            } else {
                noeudSortie.diminuerTensionDigital();
            }
        }
    }

    public Noeud getNoeudSortie() {
        return noeudSortie;
    }

    public Noeud getNoeudEntreeA() {
        return noeudEntreeA;
    }

    public Noeud getNoeudEntreeB() {
        return noeudEntreeB;
    }

    @Override
    public void updateActif() {
        calculerCourant();
    }

    @Override
    public void ajouterNoeudSortie(Noeud noeud) {
        this.noeudSortie = noeud;
    }

    public void reset() {
        this.imageIndex = 0;
        this.listeImages = new ArrayList();
    }
    
    public void nextImage() {
        System.out.println((this.imageIndex+1)%listeImages.size());
        this.imageIndex = (this.imageIndex+1)%listeImages.size();
    }    
    
    public Image getImage() {
        System.out.println("imageIndex:"+imageIndex);
        System.out.println("size:"+listeImages.size());
        return listeImages.get(imageIndex);
    }
    
    public void addImage(String filename) {
        listeImages.add(new Image(imageFolder + filename));
    }     
    

    

}
