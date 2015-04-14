/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class SourceDigitaleM implements SourceDigitaleI, ComposantI {

    private ArrayList<Boolean> listeOutput;
    private int indexOutput;
    private boolean repeter;

    public SourceDigitaleM() {
        listeOutput = new ArrayList<>();
        indexOutput = -1;
    }

    public void flipRepeter() {
        repeter = !repeter;
    }

    public boolean isRepeter() {
        return repeter;
    }

    @Override
    public boolean lireOutput() {
        ++indexOutput;
        if (repeter || indexOutput < listeOutput.size()){
        indexOutput = indexOutput % listeOutput.size();
        return listeOutput.get(indexOutput);
        }else {
            return false;
        }
    }

    @Override
    public void setListeOutput(ArrayList<Boolean> list) {
        this.listeOutput = list;
    }

    @Override
    public void setOutput(String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != '1' && string.charAt(i) != '0') {
                string = string.substring(0, i) + string.substring(i + 1);
                --i;
            }
        }
        listeOutput.clear();
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '1') {
                listeOutput.add(Boolean.TRUE);
            } else {
                listeOutput.add(Boolean.FALSE);
            }
        }
    }

}
