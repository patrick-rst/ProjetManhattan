/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author blood_000
 */
public class NOTGate extends LogicGateAbstraite implements ComposantDigital {

    public NOTGate() {
        super(TypeComposant.NOTGATE);
        addImage("not1.png");
    }

    @Override
    public void calculerCourant() {
        actifTemp = actif;
        if (noeudEntreeA == null) {
            actif = true;
        } else {
            actif = noeudEntreeA.isActif();
        }
        transfererCourant();
    }

    @Override
    public void ajouterNoeudEntree(Noeud noeud) {
        if (noeudEntreeA == null) {
            noeudEntreeA = noeud;
        } else {
            System.out.println("Erreur: Logic Gate mal connectée");
        }
    }
}
