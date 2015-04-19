/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;

/**
 *
 * @author blood_000
 */
public class SourceCourantV extends ConnectableV implements ComposantVI {

    private double forceElectroMotrice;
    private double courant;

    public SourceCourantV() {
        super(TypeComposantE.SOURCE_COURANT);

        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
    }

    public double getForceElectroMotrice() {
        return forceElectroMotrice;
    }

    public void setForceElectroMotrice(double forceElectroMotrice) {
        this.forceElectroMotrice = forceElectroMotrice;
    }

    public double getCourant() {
        return courant;
    }

    public void setCourant(double courant) {
        this.courant = courant;
    }

    @Override
    public String toString() {
        return "sourceCourant";
    }

}
