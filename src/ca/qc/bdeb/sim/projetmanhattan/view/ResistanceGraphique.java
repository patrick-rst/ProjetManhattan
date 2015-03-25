/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.model.ResistanceAbstraite;

/**
 *
 * @author blood_000
 */
public class ResistanceGraphique extends Resistance implements Connectable {

    private int orientation;
    private ResistanceAbstraite resistance;

    @Override
    public Byte[] getCotesConnectes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
