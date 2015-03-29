/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

/**
 *
 * @author blood_000
 */
public class FilDroit extends FilAbstrait {

    public FilDroit() {
        super();
        cotesConnectes[0] = 1;
        cotesConnectes[2] = 1;

    }

    @Override
    public String toString() {
        return "FilDroit";
    }

}
