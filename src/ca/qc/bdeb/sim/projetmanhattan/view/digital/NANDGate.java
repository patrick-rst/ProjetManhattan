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
public class NANDGate extends LogicGateAbstraite {

    public NANDGate() {
        super(TypeComposant.NAND_GATE);
        addImage("nand1.png");
        addImage("nand2.png");
        addImage("nand3.png");
        
        addImageActif("nand1on.png");
        addImageActif("nand2on.png");
        addImageActif("nand3on.png");  
        imageActive = listeImages.get(0);
    }

    @Override
    public void calculerCourant() {
        actifTemp = actif;
        actif = !(noeudEntreeA != null && noeudEntreeA.isActif() && noeudEntreeB != null && noeudEntreeB.isActif());

        transfererCourant();
    }

}
