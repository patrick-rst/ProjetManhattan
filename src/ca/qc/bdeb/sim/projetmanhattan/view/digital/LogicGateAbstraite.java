/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author blood_000
 */
public abstract class LogicGateAbstraite extends Connectable {

    protected byte entrees;

    public LogicGateAbstraite(TypeComposant typeComposant) {
        super(typeComposant);
    }

    public void ajouterEntree() {
        ++entrees;
    }

    public void retirerEntree() {
        --entrees;
    }
    

}
