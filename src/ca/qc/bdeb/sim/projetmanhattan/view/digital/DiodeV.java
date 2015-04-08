/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.DiodeI;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.DiodeM;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;

/**
 *
 * @author blood_000
 */
public class DiodeV  extends ConnectableV implements  ComposantVI, DiodeI {

    private DiodeM diode;

    public DiodeV() {
        super(TypeComposantE.DIODE);
        cotesConnectes[0] = 1;
        cotesConnectes[2] = -1;
    }
    
    public ComposantI getEnfant(){
        return (ComposantI)diode;
    }
    
    

}
