/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.Ground;
import ca.qc.bdeb.sim.projetmanhattan.model.GroundAbstrait;

/**
 *
 * @author blood_000
 */
public class GroundGraphique extends Connectable implements GroundAbstrait, ComposantGraphique {

    private byte[] cotesConnectes;
    private Ground ground;

    public GroundGraphique() {
        this.ground = new Ground();
        cotesConnectes[0] = 1;
    }

    @Override
    public Composant getEnfant() {
        return this.ground;
    }
}
