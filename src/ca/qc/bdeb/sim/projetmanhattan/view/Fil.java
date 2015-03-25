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
public class Fil implements Connectable {

    private int type;
    private int orientation;

    public Fil(int type, int orientation) {
        this.type = type;
        this.orientation = orientation;
    }

    @Override
    public int getOrientation() {
        return orientation;
    }

    public int getType() {
        return type;
    }
    
    

}
