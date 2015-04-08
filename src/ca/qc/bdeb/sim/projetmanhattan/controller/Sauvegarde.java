/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.view.Connectable;
import java.io.Serializable;

/**
 *
 * @author 
 */
public class Sauvegarde implements Serializable {
    
    private Connectable[][] circuit;

    public Sauvegarde() {
        circuit = new Connectable[10][10];
    }

    public void setCircuit(Connectable[][] circuit) {
        this.circuit = circuit;
        
//        for (Connectable[] tab : this.circuit) {
//            for (Connectable c : tab) {
//                if (c == null) {
//                    c = new Empty();
//                }
//            }
//        }  
        
    }

    public Connectable[][] getCircuit() {
//        for (Connectable[] tab : this.circuit) {
//            for (Connectable c : tab) {
//                if (c instanceof Empty) {
//                    c = null;
//                }
//            }
//        }
        
        return circuit;
    }
    
    
    
    
    
    
    
    
}
