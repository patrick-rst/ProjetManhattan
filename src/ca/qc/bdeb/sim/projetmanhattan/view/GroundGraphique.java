/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.Ground;
import ca.qc.bdeb.sim.projetmanhattan.model.GroundAbstrait;

/**
 *
 * @author blood_000
 */
public class GroundGraphique implements GroundAbstrait, Connectable, ComposantGraphique {

    private byte[] cotesConnectes;
    private Ground ground;

    public GroundGraphique() {
        this.ground = new Ground();
        cotesConnectes[0] = 1;
    }

    @Override
    public byte[] getCotesConnectes() {
        return cotesConnectes;
    }

    public void rotater() {
        byte temp = cotesConnectes[4];
        cotesConnectes[3] = cotesConnectes[2];
        cotesConnectes[2] = cotesConnectes[1];
        cotesConnectes[1] = cotesConnectes[0];
        cotesConnectes[0] = temp;
    }

    @Override
    public Composant getEnfant() {
        return this.ground;
    }
}
