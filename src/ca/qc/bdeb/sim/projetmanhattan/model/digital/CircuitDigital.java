/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ANDGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.Diode;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LumiereOutput;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NOTGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.SourceDigitale;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blood_000
 */
public class CircuitDigital implements Circuit, Runnable {

    private ArrayList<Noeud> noeuds;
    private ArrayList<Diode> diodes;
    private ArrayList<SourceDigitale> sourcesDigitales;
    private ArrayList<ANDGate> andGates;
    private ArrayList<ORGate> orGates;
    private ArrayList<NOTGate> notGates;
    private ArrayList<LumiereOutput> lumieres;
    private boolean run;
    private Thread thread;
    private int delaiTic;

    public CircuitDigital() {
        noeuds = new ArrayList<>();
        diodes = new ArrayList<>();
        sourcesDigitales = new ArrayList<>();
        andGates = new ArrayList<>();
        orGates = new ArrayList<>();
        notGates = new ArrayList<>();
        lumieres = new ArrayList<>();

        thread = new Thread();

        delaiTic = 250;
    }

    public void analyserCircuit() {
        run = true;

        thread.start();
    }

    public void stopAnalyse() {
        run = false;
    }

    public void ajouterORGate(ORGate gate) {
        orGates.add(gate);
    }

    public void ajouterNOTGate(NOTGate gate) {
        notGates.add(gate);
    }

    public void ajouterSourceDigitale(SourceDigitale sourceDigitale) {
        sourcesDigitales.add(sourceDigitale);
    }

    public void ajouterANDGate(ANDGate gate) {
        andGates.add(gate);
    }

    public void ajouterDiode(Diode diode) {
        diodes.add(diode);
    }

    @Override
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add(noeud);
    }
    
    public void ajouterLumiere(LumiereOutput lumiere){
        lumieres.add(lumiere);
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
        long delaiSleep;
        while (run) {
            tempsDebut = System.currentTimeMillis();
            ////////////////////////////////////////////////////////////////////
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

            ////////////////////////////////////////////////////////////////////
            delaiSleep = delaiTic - System.currentTimeMillis() + tempsDebut;
            try {
                Thread.sleep(delaiSleep);
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException");
                Logger.getLogger(CircuitDigital.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
