/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceFEMM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceFEMI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;

/**
 *
 * @author blood_000
 */
public class SourceFEMV extends ConnectableV implements SourceFEMI, ComposantVI {

    private SourceFEMI sourceFEM;

    public SourceFEMV() {
        super(TypeComposantE.SOURCE_TENSION);
        this.sourceFEM = new SourceFEMM();
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = -1;
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

    @Override
    public String toString() {
        return "sourceTension";
    }

    public ComposantI getEnfant() {
        return (ComposantI) this.sourceFEM;
    }

}
