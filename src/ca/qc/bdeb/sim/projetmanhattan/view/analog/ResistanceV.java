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
public class ResistanceV extends ConnectableV implements ComposantVI {

    private double resistance;
    private double courant;

    public ResistanceV() {
        super(TypeComposantE.RESISTANCE);

        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public double getCourant() {
        return courant;
    }

    public void setCourant(double courant) {
        this.courant = courant;
    }

    @Override
    public String toString() {
        return "resistance";
    }

}
