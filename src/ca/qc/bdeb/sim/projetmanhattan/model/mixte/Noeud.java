package ca.qc.bdeb.sim.projetmanhattan.model.mixte;

import ca.qc.bdeb.sim.projetmanhattan.view.analog.Ground;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Composant;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilAbstrait;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class Noeud {

    private double tension;

    private ArrayList<FilAbstrait> fils;
    private ArrayList<SourceFEM> sourcesFEMNeg;
    private ArrayList<SourceFEM> sourcesFEMPos;
    private ArrayList<Resistance> resistances;
    private ArrayList<SourceCourant> sourcesCourant;
    private Ground ground;
    private ArrayList<Composant> entrees;
    private ArrayList<Composant> sorties;

    public Noeud() {
        fils = new ArrayList<>();
        resistances = new ArrayList<>();
        sourcesCourant = new ArrayList<>();
        sourcesFEMNeg = new ArrayList<>();
        sourcesFEMPos = new ArrayList<>();
    }

    public void ajouterEntree(Composant comp) {
        entrees.add(comp);
    }

    public void ajouterSortie(Composant comp) {
        sorties.add(comp);
    }

    public ArrayList<Composant> getEntrees() {
        return entrees;
    }

    public ArrayList<Composant> getSorties() {
        return sorties;
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
        for (FilAbstrait fil : fils) {
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
