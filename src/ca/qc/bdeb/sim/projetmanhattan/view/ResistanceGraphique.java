/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.model.ResistanceAbstraite;

/**
 *
 * @author blood_000
 */
public class ResistanceGraphique implements ResistanceAbstraite, Connectable {

    private ResistanceAbstraite resistance;
    private byte[] cotesConnectes;

    public ResistanceGraphique() {
        this.resistance = new Resistance();
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
    }

    @Override
    public byte[] getCotesConnectes() {
        return cotesConnectes;
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
        return "Resistance";
    }

    public void rotater() {
        byte temp = cotesConnectes[4];
        cotesConnectes[3] = cotesConnectes[2];
        cotesConnectes[2] = cotesConnectes[1];
        cotesConnectes[1] = cotesConnectes[0];
        cotesConnectes[0] = temp;
    }

    public Composant getEnfant() {
        return (Composant) this.resistance;
    }

}
