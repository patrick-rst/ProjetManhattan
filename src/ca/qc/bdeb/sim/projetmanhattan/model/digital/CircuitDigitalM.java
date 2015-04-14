/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class CircuitDigitalM implements Circuit, Runnable {

    private ArrayList<NoeudDigital> noeuds;
    private ArrayList<DiodeM> diodes;
    private ArrayList<SourceDigitaleM> sourcesDigitales;
    private ArrayList<ANDGateM> andGates;
    private ArrayList<ORGateM> orGates;
    private ArrayList<NOTGateM> notGates;
    private boolean run;
    private Thread thread;
    private int delaiTic;

    public CircuitDigitalM() {
        noeuds = new ArrayList<>();
        diodes = new ArrayList<>();
        sourcesDigitales = new ArrayList<>();
        andGates = new ArrayList<>();
        orGates = new ArrayList<>();
        notGates = new ArrayList<>();
        thread = new Thread();

        delaiTic = 100;
    }

    public void analyserCircuit() {
        run = true;

        thread.start();
    }

    public void stopAnalyse() {
        run = false;
    }

    public void ajouterORGate(ORGateM gate) {
        orGates.add(gate);
    }

    public void ajouterNOTGate(NOTGateM gate) {
        notGates.add(gate);
    }

    public void ajouterSourceDigitale(SourceDigitaleM sourceDigitale) {
        sourcesDigitales.add(sourceDigitale);
    }

    public void ajouterANDGate(ANDGateM gate) {
        andGates.add(gate);
    }

    public void ajouterDiode(DiodeM diode) {
        diodes.add(diode);
    }

    public void ajouterNoeud(NoeudDigital noeud) {
        noeuds.add(noeud);
    }

    @Override
    public void wipe() {
        noeuds.clear();
        diodes.clear();
        sourcesDigitales.clear();
        andGates.clear();
        orGates.clear();
        notGates.clear();
    }

    @Override
    public void run() {
        boolean[] noeudsPasses = new boolean[noeuds.size()];
        boolean allTrue = true;
        long tempsDebut;
        long tempsSleep;
        while (run) {
            for (boolean bool : noeudsPasses) {
                bool = false;
            }
            while (!allTrue) {
            ///bla bla bla
                for (int i = 0; i < noeudsPasses.length && allTrue; ++i) {
                    if (!noeudsPasses[i]) {
                        allTrue = false;
                    }
                }
            }

        }
    }

}
