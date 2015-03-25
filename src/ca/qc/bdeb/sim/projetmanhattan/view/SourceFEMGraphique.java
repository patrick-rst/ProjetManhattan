/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceFEMAbstraite;

/**
 *
 * @author blood_000
 */
public class SourceFEMGraphique extends SourceFEM implements Connectable {

    private int orientation;
    private SourceFEMAbstraite sourceFEM;

    @Override
    public Byte[] getCotesConnectes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
