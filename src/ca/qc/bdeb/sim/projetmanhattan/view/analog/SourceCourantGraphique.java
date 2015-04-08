/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourantAbstraite;

/**
 *
 * @author blood_000
 */
public class SourceCourantGraphique extends Connectable implements SourceCourantAbstraite {

    private SourceCourantAbstraite sourceCourant;

    public SourceCourantGraphique() {
        super(TypeComposant.SOURCE_COURANT);
        this.sourceCourant = new SourceCourant();

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
    
    @Override
    public String toString() {
        return "sourceCourant";
    }    

}
