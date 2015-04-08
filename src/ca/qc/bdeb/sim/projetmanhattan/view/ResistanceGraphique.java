/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.model.ResistanceAbstraite;
import java.io.Serializable;

/**
 *
 * @author blood_000
 */
public class ResistanceGraphique extends Connectable implements ResistanceAbstraite {

    private ResistanceAbstraite resistance;

    public ResistanceGraphique() {
        this.resistance = new Resistance();
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

    public Composant getEnfant() {
        return (Composant) this.resistance;
    }

}
