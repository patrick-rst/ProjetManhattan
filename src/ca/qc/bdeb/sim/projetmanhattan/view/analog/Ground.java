/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Composant;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author blood_000
 */
public class Ground extends Connectable implements Composant {

    public Ground() {
        super(TypeComposant.GROUND);
        cotesConnectes[0] = 1;
    }

}
