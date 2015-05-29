package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class NORGate extends LogicGateAbstraite {

    public NORGate() {
        super(TypeComposant.NOR_GATE);
        addImage("nor1.png");
        addImage("nor2.png");
        addImage("nor3.png");

        addImageActif("nor1on.png");
        addImageActif("nor2on.png");
        addImageActif("nor3on.png");
        imageActive = listeImages.get(0);
    }

    @Override
    protected void calculerCourant() {
        actifTemp = actif;
        actif = !((noeudEntreeA != null && noeudEntreeA.isActif()) || (noeudEntreeB != null && noeudEntreeB.isActif()));

        transfererCourant();
    }

}
