/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilA;

/**
 *
 * @author blood_000
 */
public class FilCroix extends FilA {

    public FilCroix() {
        super();
        cotesConnectes[0] = 1;
        cotesConnectes[1] = 1;
        cotesConnectes[2] = 1;
        cotesConnectes[3] = 1;
    }

    @Override
    public String toString() {
        return "FilCroix";
    }

}
