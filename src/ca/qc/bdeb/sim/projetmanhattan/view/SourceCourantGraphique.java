/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceCourantAbstraite;

/**
 *
 * @author blood_000
 */
public class SourceCourantGraphique extends SourceCourant implements Connectable {

    private int orientation;
    private SourceCourantAbstraite sourceCourant;

    private byte[] cotesConnectes;

    @Override
    public byte[] getCotesConnectes() {
        return cotesConnectes;
    }

}
