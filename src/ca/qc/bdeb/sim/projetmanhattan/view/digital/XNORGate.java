package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class XNORGate extends LogicGateAbstraite {

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public XNORGate() {
        super(TypeComposant.XNOR_GATE);
        addImage("xnor1.png");
        addImage("xnor2.png");
        addImage("xnor3.png");

        addImageActif("xnor1on.png");
        addImageActif("xnor2on.png");
        addImageActif("xnor3on.png");
        imageActive = listeImages.get(0);
    }

    @Override
    protected void calculerCourant() {
        actif = (!(noeudEntreeA != null && noeudEntreeA.isActif() && (noeudEntreeB == null || !noeudEntreeB.isActif()))
                || (noeudEntreeB != null && noeudEntreeB.isActif() && (noeudEntreeA == null || !noeudEntreeA.isActif())));
    }

}
