/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceFEMAbstraite;
import java.io.Serializable;

/**
 *
 * @author blood_000
 */
public class SourceFEMGraphique extends Connectable implements SourceFEMAbstraite, Serializable {

    private SourceFEMAbstraite sourceFEM;

    public SourceFEMGraphique() {
        this.sourceFEM = new SourceFEM();
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
        return "SourceFEM";
    }

    public Composant getEnfant() {
        return (Composant) this.sourceFEM;
    }

}
