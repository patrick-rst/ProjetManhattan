/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ResistanceM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ResistanceI;

/**
 *
 * @author blood_000
 */
public class ResistanceV extends ConnectableV implements ResistanceI {

    private ResistanceI resistance;

    public ResistanceV() {
        super(TypeComposantE.RESISTANCE);
        this.resistance = new ResistanceM();
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
    }

    @Override
    public double getResistance() {
        return resistance.getResistance();
    }

    @Override
    public void setResistance(double resistance) {
        this.resistance.setResistance(resistance);
    }

    @Override
    public double getCourant() {
        return resistance.getCourant();
    }

    @Override
    public void setCourant(double courant) {
        this.resistance.setCourant(courant);
    }

    @Override
    public String toString() {
        return "resistance";
    }

    public ComposantI getEnfant() {
        return (ComposantI) this.resistance;
    }

}
