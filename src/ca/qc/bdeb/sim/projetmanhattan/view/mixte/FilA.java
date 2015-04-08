/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.mixte;


/**
 *
 * @author blood_000
 */
public abstract class FilA extends ConnectableV {

    protected double tension;
    
    public FilA() {
        super(TypeComposantE.FIL);
        this.cotesConnectes = new byte[4];
    }

    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
    }
    
    
}
