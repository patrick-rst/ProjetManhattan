/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import java.io.Serializable;

/**
 *
 * @author blood_000
 */
public class Connectable implements Serializable {

    protected byte[] cotesConnectes;

    public Connectable() {
        cotesConnectes = new byte[4];
    }

    public byte[] getCotesConnectes() {
        return cotesConnectes;
    }

    @Override
    public String toString() {
        return null;
    }

    public void rotater() {
        byte temp = cotesConnectes[3];
        cotesConnectes[3] = cotesConnectes[2];
        cotesConnectes[2] = cotesConnectes[1];
        cotesConnectes[1] = cotesConnectes[0];
        cotesConnectes[0] = temp;
    }

}
