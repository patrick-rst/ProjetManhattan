/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.ORGateI;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.ORGateM;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;

/**
 *
 * @author blood_000
 */
public class ORGateV extends ConnectableV implements ComposantVI, ORGateI {

    private ORGateM orGate;

    public ORGateV() {
        super(TypeComposantE.ORGATE);
    }

    public ComposantI getEnfant() {
        return (ComposantI) orGate;
    }
}
