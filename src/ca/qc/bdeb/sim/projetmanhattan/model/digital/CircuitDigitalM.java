/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class CircuitDigitalM implements Circuit {

    private ArrayList<Noeud> noeuds;
    private ArrayList<DiodeM> diodes;
    private ArrayList<SourceDigitaleM> sourcesDigitales;
    private ArrayList<ANDGateM> andGates;
    private ArrayList<ORGateM> orGates;
    private ArrayList<NOTGateM> notGates;

    public CircuitDigitalM() {
        noeuds = new ArrayList<>();
        diodes = new ArrayList<>();
        sourcesDigitales = new ArrayList<>();
        andGates = new ArrayList<>();
        orGates = new ArrayList<>();
        notGates = new ArrayList<>();
    }

    @Override
    public void ajouterNoeud(Noeud noeud) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void wipe() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
