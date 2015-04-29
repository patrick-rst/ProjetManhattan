/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.Diode;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LogicGateAbstraite;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LumiereOutput;
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
    private ArrayList<LumiereOutput> lumieres;
    private ArrayList<LogicGateAbstraite> gates;
    private ArrayList<LogicGateAbstraite> gatesABoucler;

    private boolean run;
    private Thread thread;
    private int delaiTic;

    public CircuitDigital() {
        noeuds = new ArrayList<>();
        diodes = new ArrayList<>();
        sourcesDigitales = new ArrayList<>();
        lumieres = new ArrayList<>();
        gates = new ArrayList<>();
        gatesABoucler = new ArrayList<>();

        thread = new Thread();

        delaiTic = 25;
    }

    public void analyserCircuit() {
        run = true;

        thread.start();
    }

    public void stopAnalyse() {
        run = false;
        try {
            Thread.sleep(delaiTic);
        } catch (InterruptedException ex) {
            Logger.getLogger(CircuitDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
        thread = new Thread();
    }

    public boolean isRun() {
        return run;
    }

    public void ajouterSourceDigitale(SourceDigitale sourceDigitale) {
        sourcesDigitales.add(sourceDigitale);
    }

    public void ajouterDiode(Diode diode) {
        diodes.add(diode);
    }

    @Override
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add(noeud);
    }

    public void ajouterLumiere(LumiereOutput lumiere) {
        lumieres.add(lumiere);
    }

    @Override
    public void wipe() {
        noeuds.clear();
        diodes.clear();
        sourcesDigitales.clear();
        for (LogicGateAbstraite gate : gates) {
            gate.reset();
        }
        gates.clear();
        gatesABoucler.clear();
    }

    @Override
    public void run() {
        int nombreBouclesParCycle = 10;
        int compteBoucles = 0;
        long tempsDebut;
        long delaiSleep;

        while (run) {
            tempsDebut = System.currentTimeMillis();
            ////////////////////////////////////////////////////////////////////
            compteBoucles %= nombreBouclesParCycle;
            if (compteBoucles == 0) {
                gatesABoucler.clear();
                for (SourceDigitale source : sourcesDigitales) {
                    source.updateActif();
                    ajouterGatesABoucler();
                }
            }

            for (LogicGateAbstraite gate : gatesABoucler) {
                gate.updateActif();
            }

            ajouterGatesABoucler();

            ++compteBoucles;
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

    public void ajouterGatesABoucler() {
        for (LogicGateAbstraite gate : gates) {
            if (gate.isABoucler()) {
                if (!gatesABoucler.contains(gate)) {
                    gatesABoucler.add(gate);
                }
            }
            gate.resetBools();
        }
    }

    public void ajouterGate(LogicGateAbstraite gate) {
        gates.add(gate);
    }

}
