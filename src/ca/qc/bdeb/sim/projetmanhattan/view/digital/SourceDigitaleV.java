/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.SourceDigitaleI;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.SourceDigitaleM;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposantE;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class SourceDigitaleV extends ConnectableV implements SourceDigitaleI, ComposantVI {

    private SourceDigitaleI sourceDigitale;

    public SourceDigitaleV() {
        super(TypeComposantE.SOURCE_DIGITALE);
        cotesConnectes[0] = 1;
        sourceDigitale = new SourceDigitaleM();
    }
    
    public ComposantI getEnfant(){
        return (ComposantI)sourceDigitale;
    }

    @Override
    public boolean lireOutput() {
        return sourceDigitale.lireOutput();
    }

    @Override
    public void setListeOutput(ArrayList<Boolean> list) {
        sourceDigitale.setListeOutput(list);
    }

    @Override
    public void setOutput(String string) {
        sourceDigitale.setOutput(string);
    }

}
