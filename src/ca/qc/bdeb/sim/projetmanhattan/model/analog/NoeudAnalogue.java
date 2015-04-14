package ca.qc.bdeb.sim.projetmanhattan.model.analog;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.GroundM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ResistanceM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourantM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceFEMM;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilA;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class NoeudAnalogue {

    private double tension;

    private ArrayList<FilA> fils;
    private ArrayList<Byte> connexions;
    private ArrayList<SourceFEMM> sourcesFEMNeg;
    private ArrayList<SourceFEMM> sourcesFEMPos;
    private ArrayList<ResistanceM> resistances;
    private ArrayList<SourceCourantM> sourcesCourant;
    private GroundM ground;

    public NoeudAnalogue() {
        fils = new ArrayList<>();
        connexions = new ArrayList<>();
        resistances = new ArrayList<>();
        sourcesCourant = new ArrayList<>();
        sourcesFEMNeg = new ArrayList<>();
        sourcesFEMPos = new ArrayList<>();
    }

    public void ajouterFil(FilA fil) {
        fils.add(fil);
    }

    public ArrayList<ResistanceM> getResistances() {
        return resistances;
    }

    /*public ArrayList<FilAbstrait> getFils() {
     return fils;
     }*/
    public ArrayList<SourceFEMM> getSourcesFEMNeg() {
        return sourcesFEMNeg;
    }

    public ArrayList<SourceFEMM> getSourcesFEMPos() {
        return sourcesFEMPos;
    }

    public ArrayList<SourceCourantM> getSourcesCourant() {
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

    public GroundM getGround() {
        return ground;
    }

    public void setGround(GroundM ground) {
        this.ground = ground;
    }

}
