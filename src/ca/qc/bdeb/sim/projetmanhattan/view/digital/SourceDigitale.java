/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Composant;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class SourceDigitale extends Connectable implements Composant {

    private ArrayList<Boolean> listeOutput;
    private int indexOutput;
    private boolean repeter;

    public SourceDigitale() {
        super(TypeComposant.SOURCE_DIGITALE);
        listeOutput = new ArrayList<>();
        cotesConnectes[0] = 1;
    }

    public void flipRepeter() {
        repeter = !repeter;
    }

    public boolean isRepeter() {
        return repeter;
    }

    public boolean lireOutput() {
        ++indexOutput;
        if (repeter || indexOutput < listeOutput.size()) {
            indexOutput = indexOutput % listeOutput.size();
            return listeOutput.get(indexOutput);
        } else {
            return false;
        }
    }

    public void setListeOutput(ArrayList<Boolean> list) {
        this.listeOutput = list;
    }

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
