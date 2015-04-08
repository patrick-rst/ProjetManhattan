/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.Ground;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.GroundAbstrait;
import java.io.Serializable;

/**
 *
 * @author blood_000
 */
public class GroundGraphique extends Connectable implements GroundAbstrait, ComposantGraphique {

    private Ground ground;

    public GroundGraphique() {
        super(TypeComposant.GROUND);
        this.ground = new Ground();
        cotesConnectes[0] = 1;
    }

    @Override
    public Composant getEnfant() {
        return this.ground;
    }
}
