/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import java.io.Serializable;

/**
 *
 * @author 
 */
public class Sauvegarde implements Serializable {
    
    private ConnectableV[][] circuit;

    public Sauvegarde() {
        circuit = new ConnectableV[10][10];
    }

    public void setCircuit(ConnectableV[][] circuit) {
        this.circuit = circuit;
        
//        for (ConnectableV[] tab : this.circuit) {
//            for (ConnectableV c : tab) {
//                if (c == null) {
//                    c = new Empty();
//                }
//            }
//        }  
        
    }

    public ConnectableV[][] getCircuit() {
//        for (ConnectableV[] tab : this.circuit) {
//            for (ConnectableV c : tab) {
//                if (c instanceof Empty) {
//                    c = null;
//                }
//            }
//        }
        
        return circuit;
    }
    
    
    
    
    
    
    
    
}
