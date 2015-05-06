package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import ca.qc.bdeb.sim.projetmanhattan.controller.FXMLDocumentController;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.Diode;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LogicGateAbstraite;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LumiereOutput;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.SourceDigitale;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class CircuitDigital implements Circuit {

    private ArrayList<Noeud> noeuds;
    private ArrayList<Diode> diodes;
    private ArrayList<SourceDigitale> sourcesDigitales;
    private ArrayList<LumiereOutput> lumieres;
    private ArrayList<LogicGateAbstraite> gates;
    private ArrayList<LogicGateAbstraite> gatesABoucler;
    private FXMLDocumentController controller;

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

    public void setController(FXMLDocumentController controller) {
        this.controller = controller;
    }

    @Override
    public void analyserCircuit() {
        run = true;
        thread = new Thread() {
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

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.updateCircuitNumerique();
                        }
                    });
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
        };
        thread.setDaemon(true);
        thread.start();
    }

    public void stopAnalyse() {
        run = false;
        resetGates();
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

    public void resetGates() {
        for (LogicGateAbstraite gate : gates) {
            gate.resetConnexions();
        }
    }

    @Override
    public void wipe() {
        noeuds.clear();
        diodes.clear();
        sourcesDigitales.clear();
        gates.clear();
        gatesABoucler.clear();
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
