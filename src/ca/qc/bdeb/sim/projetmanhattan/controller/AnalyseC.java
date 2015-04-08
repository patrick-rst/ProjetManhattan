/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.CircuitAnalogueM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.GroundM;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ResistanceM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourantM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceFEMM;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.ANDGateM;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.CircuitDigitalM;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.DiodeM;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.NOTGateM;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.ORGateM;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.SourceDigitaleM;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.GroundV;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.ResistanceV;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceCourantV;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceFEMV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ANDGateV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.DiodeV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NOTGateV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ORGateV;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.SourceDigitaleV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilA;

/**
 *
 * @author blood_000
 */
public class AnalyseC {

    private ConnectableV[][] connectables;
    private boolean[][] connectablesPasses;

    public AnalyseC() {
    }

    public void preparerAnalyse(Circuit circuit, ConnectableV[][] connectables) {
        if (circuit instanceof CircuitAnalogueM) {
            preparerAnalyseAnalogue((CircuitAnalogueM) circuit, connectables);
        } else {
            preparerAnalyseDigitale((CircuitDigitalM) circuit, connectables);
        }
    }

    public void preparerAnalyseAnalogue(CircuitAnalogueM circuit, ConnectableV[][] connectables) {
        try {
            this.connectables = connectables;
            circuit.wipe();
            connectablesPasses = new boolean[this.connectables.length][this.connectables[0].length];
            for (int i = 0; i < connectables.length; ++i) {
                for (int j = 0; j < connectables[i].length; ++j) {
                    if (connectables[i][j] instanceof ResistanceV) {
                        circuit.ajouterResistance((ResistanceM) ((ResistanceV) connectables[i][j]).getEnfant());
                    } else if (connectables[i][j] instanceof SourceCourantV) {
                        circuit.ajouterSourceCourant((SourceCourantM) ((SourceCourantV) connectables[i][j]).getEnfant());
                    } else if (connectables[i][j] instanceof SourceFEMV) {
                        circuit.ajouterSourceFEM((SourceFEMM) ((SourceFEMV) connectables[i][j]).getEnfant());
                    } else if (connectables[i][j] instanceof GroundV) {
                        circuit.ajouterGround((GroundM) ((GroundV) connectables[i][j]).getEnfant());
                    }
                }
            }
            creerLiensAnalogues(circuit);
        } catch (Exception e) {
            System.out.println("Erreur: Circuit invalide");
        }
    }

    public void preparerAnalyseDigitale(CircuitDigitalM circuit, ConnectableV[][] connectables) {
        for (int i = 0; i < connectables.length; ++i) {
                for (int j = 0; j < connectables[i].length; ++j) {
                    if (connectables[i][j] instanceof DiodeV) {
                        circuit.ajouterDiode((DiodeM) ((DiodeV) connectables[i][j]).getEnfant());
                    } else if (connectables[i][j] instanceof SourceDigitaleV) {
                        circuit.ajouterSourceDigitale((SourceDigitaleM) ((SourceDigitaleV) connectables[i][j]).getEnfant());
                    } else if (connectables[i][j] instanceof ANDGateV) {
                        circuit.ajouterANDGate((ANDGateM) ((ANDGateV) connectables[i][j]).getEnfant());
                    } else if (connectables[i][j] instanceof ORGateV) {
                        circuit.ajouterORGate((ORGateM) ((ORGateV) connectables[i][j]).getEnfant());
                    }else if (connectables[i][j] instanceof NOTGateV) {
                        circuit.ajouterNOTGate((NOTGateM) ((NOTGateV) connectables[i][j]).getEnfant());
                    }
                }
            }
            //creerLiens(circuit);
    }

    public void creerLiensAnalogues(CircuitAnalogueM circuit) {
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 0; j < connectables[i].length; ++j) {
                if (connectables[i][j] instanceof FilA && !connectablesPasses[i][j]) {

                    connectablesPasses[i][j] = true;
                    Noeud noeud = new Noeud();
                    noeud.ajouterFil((FilA) connectables[i][j]);
                    retournerEnfants((FilA) connectables[i][j], i, j, noeud);
                    circuit.ajouterNoeud(noeud);
                }
            }
        }
    }

    public void gererLienDetecteAnalogue(int i, int j, Noeud noeud, int origine) {
        if (connectables[i][j] instanceof FilA) {

            connectablesPasses[i][j] = true;
            noeud.ajouterFil((FilA) connectables[i][j]);
            retournerEnfants(((FilA) connectables[i][j]), i, j, noeud);

        } else if (connectables[i][j] instanceof ResistanceV) {
            noeud.getResistances().add((ResistanceM) ((ResistanceV) connectables[i][j]).getEnfant());
        } else if (connectables[i][j] instanceof SourceCourantV) {
            noeud.getSourcesCourant().add((SourceCourantM) ((SourceCourantV) connectables[i][j]).getEnfant());
        } else if (connectables[i][j] instanceof SourceFEMV) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) { // PROBLEME TROUVE??
                noeud.getSourcesFEMNeg().add((SourceFEMM) ((SourceFEMV) connectables[i][j]).getEnfant());
            } else {
                noeud.getSourcesFEMPos().add((SourceFEMM) ((SourceFEMV) connectables[i][j]).getEnfant());
            }
        } else if (connectables[i][j] instanceof GroundV) {
            noeud.setGround((GroundM) ((GroundV) connectables[i][j]).getEnfant());
        }

    }

    public void retournerEnfants(FilA fil, int i, int j, Noeud noeud) {

        if (fil.getCotesConnectes()[0] == 1 && i > 0 && connectables[i - 1][j] != null && connectables[i - 1][j].getCotesConnectes()[2] != 0 && !connectablesPasses[i - 1][j]) {
            gererLienDetecteAnalogue((i - 1), j, noeud, 2);

        }
        if (fil.getCotesConnectes()[1] == 1 && j < connectables[i].length - 1 && connectables[i][j + 1] != null && connectables[i][j + 1].getCotesConnectes()[3] != 0 && !connectablesPasses[i][j + 1]) {
            gererLienDetecteAnalogue(i, (j + 1), noeud, 3);
        }
        if (fil.getCotesConnectes()[2] == 1 && i < connectables.length - 1 && connectables[i + 1][j] != null && connectables[i + 1][j].getCotesConnectes()[0] != 0 && !connectablesPasses[i + 1][j]) {
            gererLienDetecteAnalogue((i + 1), j, noeud, 0);
        }
        if (fil.getCotesConnectes()[3] == 1 && j > 0 && connectables[i][j - 1] != null && connectables[i][j - 1].getCotesConnectes()[1] != 0 && !connectablesPasses[i][j - 1]) {
            gererLienDetecteAnalogue(i, (j - 1), noeud, 1);
        }
    }

}
