/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model;

/**
 *
 * @author blood_000
 */
public class Resistance implements Composant, ResistanceAbstraite {

    private double resistance;
    private double courant;

    @Override
    public double getResistance() {
        System.out.println(resistance);
        return resistance;
    }

    @Override
    public void setResistance(double resistance) {
        this.resistance = resistance;
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
