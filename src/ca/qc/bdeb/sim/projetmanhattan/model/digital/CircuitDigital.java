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

    private final ArrayList<Noeud> noeuds;
    private final ArrayList<Diode> diodes;
    private final ArrayList<SourceDigitale> sourcesDigitales;
    private final ArrayList<LumiereOutput> lumieres;
    private final ArrayList<LogicGateAbstraite> gates;
    private final ArrayList<LogicGateAbstraite> gatesABoucler;
    private FXMLDocumentController controller;

    private boolean run;
    private Thread thread;
    private final int delaiTic;

    /**
     * Crée le nouveau circuit digital et initialise les diverses variables*
     */
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

    /**
     * Assigne un controleur à la variable controller
     *
     * @param controller le controleur de l'interface
     */
    public void setController(FXMLDocumentController controller) {
        this.controller = controller;
    }

    /**
     * Part un thread qui simule le courant dans le circuit selon les connexions
     * et les input des sourcesDigitales. Appelle aussi les méthodes qui
     * actualisent les images
     */
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

    /**
     * Arrete l'analyse et prepare le circuit pour une prochaine "run"
     */
    public void stopAnalyse() {

        run = false;
        resetGates();
        resetSourcesDigitales();
    }

    /**
     * Remet les sources digitales a zero pour les synchroniser en cas de
     * modification du circuit.
     */
    public void resetSourcesDigitales() {
        for (SourceDigitale source : sourcesDigitales) {
            source.remettreAZero();
        }
    }

    /**
     * Ajoute un élément à la liste de sources
     *
     * @param sourceDigitale L'élément à ajouter
     */
    public void ajouterSourceDigitale(SourceDigitale sourceDigitale) {
        sourcesDigitales.add(sourceDigitale);
    }

    /**
     * Ajoute un élément à la liste de diodes
     *
     * @param diode La diode à ajouter
     */
    public void ajouterDiode(Diode diode) {
        diodes.add(diode);
    }

    /**
     * ajoute un noeud à la liste de noeuds
     *
     * @param noeud Le noeud à ajouter
     */
    @Override
    public void ajouterNoeud(Noeud noeud) {
        noeuds.add(noeud);
    }

    /**
     * Ajoute une lumière à la liste de lumières
     *
     * @param lumiere la lumière à ajouter
     */
    public void ajouterLumiere(LumiereOutput lumiere) {
        lumieres.add(lumiere);
    }

    private void resetGates() {
        for (LogicGateAbstraite gate : gates) {
            gate.resetConnexions();
        }
    }

    /**
     * Remet la grille à zéro pour commencer un nouveau circuit.
     */
    @Override
    public void wipe() {
        noeuds.clear();
        diodes.clear();
        sourcesDigitales.clear();
        gates.clear();
        gatesABoucler.clear();
    }

    private void ajouterGatesABoucler() {
        for (LogicGateAbstraite gate : gates) {
            if (gate.isABoucler()) {
                if (!gatesABoucler.contains(gate)) {
                    gatesABoucler.add(gate);
                }
            }
            gate.resetBools();
        }
    }

    /**
     * Ajoute une porte logque à la liste de portes
     *
     * @param gate le composant à ajouter
     */
    public void ajouterGate(LogicGateAbstraite gate) {
        gates.add(gate);
    }

}
