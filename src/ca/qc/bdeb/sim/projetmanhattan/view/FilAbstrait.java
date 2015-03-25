/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

/**
 *
 * @author blood_000
 */
public abstract class FilAbstrait implements Connectable {

    protected byte[] cotesConnectes;

    public FilAbstrait() {
        cotesConnectes = new byte[4];
    }

    public byte[] getCotesConnectes() {
        return cotesConnectes;
    }
    
    public void rotater(){
        byte temp = cotesConnectes[4];
        cotesConnectes[4] = cotesConnectes[3];
        cotesConnectes[3] = cotesConnectes[2];
        cotesConnectes[2] = cotesConnectes[1];
        cotesConnectes[1] = temp;
    }

}
