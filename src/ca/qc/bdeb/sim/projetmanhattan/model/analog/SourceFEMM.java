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
public class SourceFEMM implements ComposantI, SourceFEMI {

    private double forceElectroMotrice;
    private double courant;

    @Override
    public double getForceElectroMotrice() {
        return forceElectroMotrice;
    }

    @Override
    public void setForceElectroMotrice(double forceElectroMotrice) {
        this.forceElectroMotrice = forceElectroMotrice;
    }

    @Override
    public double getCourant() {
        return courant;
    }

    @Override
    public void setCourant(double courant) {
        this.courant = courant;
    }
}
