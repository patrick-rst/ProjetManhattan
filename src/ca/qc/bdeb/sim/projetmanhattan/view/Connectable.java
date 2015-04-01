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
public class Connectable {

    protected byte[] cotesConnectes;

    public Connectable() {
        cotesConnectes = new byte[4];
    }

    public byte[] getCotesConnectes() {
        for(int i = 0; i < 4; ++i){
            System.out.print(cotesConnectes[i]);
        }
        System.out.println("");
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
