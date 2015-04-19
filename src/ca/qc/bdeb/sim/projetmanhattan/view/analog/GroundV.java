/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;

/**
 *
 * @author blood_000
 */
public class GroundV extends ConnectableV implements ComposantVI {

    public GroundV() {
        super(TypeComposantE.GROUND);
        cotesConnectes[0] = 1;
    }

}
