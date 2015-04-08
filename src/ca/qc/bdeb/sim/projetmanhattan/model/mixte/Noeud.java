package ca.qc.bdeb.sim.projetmanhattan.model.mixte;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.Ground;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.Ground;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilAbstrait;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class Noeud {

    private double tension;
    
    private ArrayList<FilAbstrait> fils;
    private ArrayList<Byte> connexions;
    private ArrayList<SourceFEM> sourcesFEMNeg;
    private ArrayList<SourceFEM> sourcesFEMPos;
    private ArrayList<Resistance> resistances;
    private ArrayList<SourceCourant> sourcesCourant;
    private Ground ground;

    public Noeud() {
        fils = new ArrayList<>();
        connexions = new ArrayList<>();
        resistances = new ArrayList<>();
        sourcesCourant = new ArrayList<>();
        sourcesFEMNeg = new ArrayList<>();
        sourcesFEMPos = new ArrayList<>();
    }

    public void ajouterFil(FilAbstrait fil) {
        fils.add(fil);
    }

    public ArrayList<Resistance> getResistances() {
        return resistances;
    }

    /*public ArrayList<FilAbstrait> getFils() {
        return fils;
    }*/

    public ArrayList<SourceFEM> getSourcesFEMNeg() {
        return sourcesFEMNeg;
    }

    public ArrayList<SourceFEM> getSourcesFEMPos() {
        return sourcesFEMPos;
    }

    public ArrayList<SourceCourant> getSourcesCourant() {
        return sourcesCourant;
    }

    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
        for(FilAbstrait fil: fils){
            fil.setTension(tension);
        }
    }

    public Ground getGround() {
        return ground;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }
    
    

}
