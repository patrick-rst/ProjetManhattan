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
public interface SourceDigitaleI {
    
    public boolean lireOutput();
    
    public void setListeOutput(ArrayList<Boolean> list);
    
    public void setOutput(String string);
    
}
