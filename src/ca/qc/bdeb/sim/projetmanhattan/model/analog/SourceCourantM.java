/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.analog;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;

/**
 *
 * @author blood_000
 */
public class SourceCourantM implements ComposantI, SourceCourantI {

    private double courant;

    @Override
    public double getCourant() {
        return courant;
    }

    @Override
    public void setCourant(double courant) {
        this.courant = courant;
    }
}