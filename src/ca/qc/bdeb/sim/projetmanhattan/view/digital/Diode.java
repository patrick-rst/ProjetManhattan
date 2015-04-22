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
public class Diode extends LogicGateAbstraite implements ComposantDigital {

    public Diode() {
        super(TypeComposant.DIODE);
        cotesConnectes[0] = 1;
        cotesConnectes[2] = -1;
    }

    @Override
    public void calculerCourant() {
        actifTemp = actif;
        actif = noeudEntreeA != null && noeudEntreeA.isActif();

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
