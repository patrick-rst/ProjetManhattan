package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class ANDGate extends LogicGateAbstraite {

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public ANDGate() {
        super(TypeComposant.ANDGATE);

        addImage("and1.png");
        addImage("and2.png");
        addImage("and3.png");

        addImageActif("and1on.png");
        addImageActif("and2on.png");
        addImageActif("and3on.png");
        imageActive = listeImages.get(0);
    }

    @Override
    protected void calculerCourant() {
        actifTemp = actif;
        actif = noeudEntreeA != null && noeudEntreeA.isActif() && noeudEntreeB != null && noeudEntreeB.isActif();

        transfererCourant();
    }

}
