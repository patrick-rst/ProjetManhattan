/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ANDGateV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.DiodeV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NOTGateV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ORGateV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.SourceDigitaleV;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class CircuitDigitalM implements Circuit, Runnable {

    private ArrayList<Noeud> noeuds;
    private ArrayList<DiodeV> diodes;
    private ArrayList<SourceDigitaleV> sourcesDigitales;
    private ArrayList<ANDGateV> andGates;
    private ArrayList<ORGateV> orGates;
    private ArrayList<NOTGateV> notGates;
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

    public void ajouterORGate(ORGateV gate) {
        orGates.add(gate);
    }

    public void ajouterNOTGate(NOTGateV gate) {
        notGates.add(gate);
    }

    public void ajouterSourceDigitale(SourceDigitaleV sourceDigitale) {
        sourcesDigitales.add(sourceDigitale);
    }

    public void ajouterANDGate(ANDGateV gate) {
        andGates.add(gate);
    }

    public void ajouterDiode(DiodeV diode) {
        diodes.add(diode);
    }

    public void ajouterNoeud(Noeud noeud) {
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
