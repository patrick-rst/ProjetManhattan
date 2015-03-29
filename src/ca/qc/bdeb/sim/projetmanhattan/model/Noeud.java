package ca.qc.bdeb.sim.projetmanhattan.model;

import ca.qc.bdeb.sim.projetmanhattan.view.FilAbstrait;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class Noeud {

    private ArrayList<FilAbstrait> fils;
    private ArrayList<Byte> connexions;
    private ArrayList<Composant> composants;
    private ArrayList<SourceFEM> sourcesFEMNeg;
    private ArrayList<SourceFEM> sourcesFEMPos;
    private ArrayList<Resistance> resistances;
    private ArrayList<SourceCourant> sourcesCourant;

    public Noeud() {
        fils = new ArrayList<>();
        connexions = new ArrayList<>();
        composants = new ArrayList<>();
    }

    public void ajouterFil(FilAbstrait fil) {
        fils.add(fil);
    }

    public void ajouterComposant(Composant composant) {
        composants.add(composant);
    }

    public ArrayList<Composant> getComposants() {
        return composants;
    }

    public ArrayList<Resistance> getResistances() {
        return resistances;
    }

    public ArrayList<FilAbstrait> getFils() {
        return fils;
    }

    public ArrayList<SourceFEM> getSourcesFEMNeg() {
        return sourcesFEMNeg;
    }

    public ArrayList<SourceFEM> getSourcesFEMPos() {
        return sourcesFEMPos;
    }

    public ArrayList<SourceCourant> getSourcesCourant() {
        return sourcesCourant;
    }
    
    
    
    

}
