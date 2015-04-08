/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourantM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourantI;

/**
 *
 * @author blood_000
 */
public class SourceCourantV extends ConnectableV implements SourceCourantI {

    private SourceCourantI sourceCourant;

    public SourceCourantV() {
        super(TypeComposantE.SOURCE_COURANT);
        this.sourceCourant = new SourceCourantM();

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

    public ComposantI getEnfant() {
        return (ComposantI) this.sourceCourant;
    }
    
    @Override
    public String toString() {
        return "sourceCourant";
    }    

}
