/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author blood_000
 */
public class XNORGate extends LogicGateAbstraite {

    public XNORGate() {
        super(TypeComposant.XNOR_GATE);
        addImage("xnor1.png");
        addImage("xnor2.png");
        addImage("xnor3.png");        
    }

    @Override
    public void calculerCourant() {
        actifTemp = actif;
        actif = (!(noeudEntreeA != null && noeudEntreeA.isActif() && (noeudEntreeB == null || !noeudEntreeB.isActif()))
                || (noeudEntreeB != null && noeudEntreeB.isActif() && (noeudEntreeA == null || !noeudEntreeA.isActif())));
        transfererCourant();
    }

}
