package ca.qc.bdeb.sim.projetmanhattan.model.mixte;

import ca.qc.bdeb.sim.projetmanhattan.view.analog.GroundV;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.ResistanceV;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceCourantV;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceFEMV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilA;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class Noeud {

    private double tension;

    private ArrayList<FilA> fils;
    private ArrayList<SourceFEMV> sourcesFEMNeg;
    private ArrayList<SourceFEMV> sourcesFEMPos;
    private ArrayList<ResistanceV> resistances;
    private ArrayList<SourceCourantV> sourcesCourant;
    private GroundV ground;
    private ArrayList<ComposantVI> entrees;
    private ArrayList<ComposantVI> sorties;

    public Noeud() {
        fils = new ArrayList<>();
        resistances = new ArrayList<>();
        sourcesCourant = new ArrayList<>();
        sourcesFEMNeg = new ArrayList<>();
        sourcesFEMPos = new ArrayList<>();
    }

    public void ajouterEntree(ComposantVI comp) {
        entrees.add(comp);
    }

    public void ajouterSortie(ComposantVI comp) {
        sorties.add(comp);
    }

    public ArrayList<ComposantVI> getEntrees() {
        return entrees;
    }

    public ArrayList<ComposantVI> getSorties() {
        return sorties;
    }

    public void ajouterFil(FilA fil) {
        fils.add(fil);
    }

    public ArrayList<ResistanceV> getResistances() {
        return resistances;
    }

    /*public ArrayList<FilAbstrait> getFils() {
     return fils;
     }*/
    public ArrayList<SourceFEMV> getSourcesFEMNeg() {
        return sourcesFEMNeg;
    }

    public ArrayList<SourceFEMV> getSourcesFEMPos() {
        return sourcesFEMPos;
    }

    public ArrayList<SourceCourantV> getSourcesCourant() {
        return sourcesCourant;
    }

    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
        for (FilA fil : fils) {
            fil.setTension(tension);
        }
    }

    public GroundV getGround() {
        return ground;
    }

    public void setGround(GroundV ground) {
        this.ground = ground;
    }

}
