/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class SourceDigitaleM implements SourceDigitaleI {

    private ArrayList<Boolean> listeOutput;
    private int indexOutput;

    public SourceDigitaleM() {
        listeOutput = new ArrayList<>();
        indexOutput = -1;
    }

    @Override
    public boolean lireOutput() {
        ++indexOutput;
        indexOutput = indexOutput % listeOutput.size();
        return listeOutput.get(indexOutput);

    }

    @Override
    public void setListeOutput(ArrayList<Boolean> list) {
        this.listeOutput = list;
    }

    @Override
    public void setOutput(String string) {
        for(int i = 0; i < string.length(); ++i){
            if (string.charAt(i) != '1' && string.charAt(i) != '0'){
                string = string.substring(0, i) + string.substring(i+1);
                --i;
            }
        }
        listeOutput.clear();
        for(int i =0; i < string.length(); ++i){
            if (string.charAt(i) == '1'){
                listeOutput.add(Boolean.TRUE);
            } else {
                listeOutput.add(Boolean.FALSE);
            }
        }
    }

}
