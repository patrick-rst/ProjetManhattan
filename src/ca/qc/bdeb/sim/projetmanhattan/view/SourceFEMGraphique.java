/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceFEMAbstraite;

/**
 *
 * @author blood_000
 */
public class SourceFEMGraphique implements SourceFEMAbstraite, Connectable {

    private int orientation;
    private SourceFEMAbstraite sourceFEM;

    private byte[] cotesConnectes;

    @Override
    public byte[] getCotesConnectes() {
        return cotesConnectes;
    }

    @Override
    public double getForceElectroMotrice() {
        return sourceFEM.getForceElectroMotrice();
    }

    @Override
    public void setForceElectroMotrice(double forceElectroMotrice) {
        this.sourceFEM.setForceElectroMotrice(forceElectroMotrice);
    }

    @Override
    public double getCourant() {
        return sourceFEM.getCourant();
    }

    @Override
    public void setCourant(double courant) {
        this.sourceFEM.setCourant(courant);
    }

}
