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
public interface Connectable {

    public byte[] getCotesConnectes();

    @Override
    public String toString();

    public void rotater();

}
