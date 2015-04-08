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
public abstract class FilAbstrait extends Connectable implements Serializable {

    protected double tension;

    public FilAbstrait() {
        this.cotesConnectes = new byte[4];
    }

    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
    }

}
