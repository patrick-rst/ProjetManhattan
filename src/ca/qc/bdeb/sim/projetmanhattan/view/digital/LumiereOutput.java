package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ImageChangeable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class LumiereOutput extends ImageChangeable implements ComposantDigital {

    protected Noeud noeudEntree;

    public LumiereOutput() {
        super(TypeComposant.LUMIERE_OUTPUT);
        cotesConnectes[3] = 1;

        actif = false;
        addImageActif("file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/lightOn.png");
        addImage("file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/lightOff.png");
        imageActive = listeImages.get(0);
    }

    @Override
    public void updateActif() {
        if (noeudEntree.isActif()) {
            actif = true;
        } else {
            actif = false;
        }
    }

    @Override
    public void ajouterNoeudEntree(Noeud noeud) {
        this.noeudEntree = noeud;
    }

    @Override
    public void ajouterNoeudSortie(Noeud noeud) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void switchOn() {
        actif = true;
    }

    public void switchOff() {
        actif = false;
    }

    @Override
    public boolean isActif() {
        return actif;
    }

}
