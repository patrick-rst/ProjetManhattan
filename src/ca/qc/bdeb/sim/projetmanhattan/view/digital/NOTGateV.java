/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;

/**
 *
 * @author blood_000
 */
public class NOTGateV extends ConnectableV implements ComposantVI {

    public NOTGateV() {
        super(TypeComposantE.NOTGATE);
    }

}
