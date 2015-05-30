package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class XORGate extends LogicGateAbstraite {

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public XORGate() {
        super(TypeComposant.XOR_GATE);
        addImage("xor1.png");
        addImage("xor2.png");
        addImage("xor3.png");

        addImageActif("xor1on.png");
        addImageActif("xor2on.png");
        addImageActif("xor3on.png");
        imageActive = listeImages.get(0);
    }

    @Override
    protected void calculerCourant() {
        actif = (noeudEntreeA != null && noeudEntreeA.isActif() && (noeudEntreeB == null || !noeudEntreeB.isActif()))
                || (noeudEntreeB != null && noeudEntreeB.isActif() && (noeudEntreeA == null || !noeudEntreeA.isActif()));
    }

}
