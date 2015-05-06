/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * or open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author blood_000
 */
public class ORGate extends LogicGateAbstraite  {


    
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
    public void calculerCourant() {
        actifTemp = actif;
        actif = (noeudEntreeA != null && noeudEntreeA.isActif()) || (noeudEntreeB != null && noeudEntreeB.isActif());

        transfererCourant();
    }

}
