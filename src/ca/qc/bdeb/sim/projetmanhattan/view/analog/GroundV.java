/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.GroundM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.GroundAbstrait;
import java.io.Serializable;

/**
 *
 * @author blood_000
 */
public class GroundV extends ConnectableV implements GroundAbstrait, ComposantVI {

    private GroundM ground;

    public GroundV() {
        super(TypeComposantE.GROUND);
        this.ground = new GroundM();
        cotesConnectes[0] = 1;
    }

    @Override
    public ComposantI getEnfant() {
        return this.ground;
    }
}
