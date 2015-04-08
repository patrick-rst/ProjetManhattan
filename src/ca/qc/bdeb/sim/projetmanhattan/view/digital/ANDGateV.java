/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.ANDGateI;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.ANDGateM;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;

/**
 *
 * @author blood_000
 */
public class ANDGateV extends ConnectableV implements ComposantVI, ANDGateI {

    private ANDGateM andGate;

    public ANDGateV() {
        super(TypeComposantE.ANDGATE);
    }

    public ComposantI getEnfant() {
        return (ComposantI) andGate;
    }
}
