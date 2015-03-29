/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model;

/**
 *
 * @author blood_000
 */
public class Composant {
    private Noeud noeudA;
    private Noeud noeudB;

    public Composant() {
        
    }

    public Noeud getNoeudA() {
        return noeudA;
    }

    public Noeud getNoeudB() {
        return noeudB;
    }

    public void setNoeudA(Noeud noeudA) {
        this.noeudA = noeudA;
    }

    public void setNoeudB(Noeud noeudB) {
        this.noeudB = noeudB;
    }
    
    
    
    
}
