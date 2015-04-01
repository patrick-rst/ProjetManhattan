/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceCourantAbstraite;

/**
 *
 * @author blood_000
 */
public class SourceCourantGraphique extends Connectable implements SourceCourantAbstraite {

    private SourceCourantAbstraite sourceCourant;
    private byte[] cotesConnectes;

    public SourceCourantGraphique(Circuit c) {
        this.sourceCourant = new SourceCourant();
        c.ajouterSourceCourant((SourceCourant) sourceCourant);
        this.cotesConnectes = new byte[4];
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;
    }

    @Override
    public double getCourant() {
        return sourceCourant.getCourant();
    }

    @Override
    public void setCourant(double courant) {
        this.sourceCourant.setCourant(courant);
    }

    public Composant getEnfant() {
        return (Composant) this.sourceCourant;
    }

}
