package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class ORGate extends LogicGateAbstraite {

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public ORGate() {
        super(TypeComposant.ORGATE);
        addImage("or1.png");
        addImage("or2.png");
        addImage("or3.png");

        addImageActif("or1on.png");
        addImageActif("or2on.png");
        addImageActif("or3on.png");
        imageActive = listeImages.get(0);
    }

    @Override
    protected void calculerCourant() {
        actif = (noeudEntreeA != null && noeudEntreeA.isActif()) || (noeudEntreeB != null && noeudEntreeB.isActif());
    }

}
