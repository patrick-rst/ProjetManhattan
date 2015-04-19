/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.CircuitAnalogueM;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.CircuitDigitalM;
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
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ComposantVI;
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
                        circuit.ajouterResistance((ResistanceV) connectables[i][j]);
                    } else if (connectables[i][j] instanceof SourceCourantV) {
                        circuit.ajouterSourceCourant((SourceCourantV) connectables[i][j]);
                    } else if (connectables[i][j] instanceof SourceFEMV) {
                        circuit.ajouterSourceFEM((SourceFEMV) connectables[i][j]);
                    } else if (connectables[i][j] instanceof GroundV) {
                        circuit.ajouterGround((GroundV) connectables[i][j]);
                    }
                }
            }
            creerLiens(circuit);
        } catch (Exception e) {
            System.out.println("Erreur: Circuit invalide");
        }
    }

    public void preparerAnalyseDigitale(CircuitDigitalM circuit, ConnectableV[][] connectables) {
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 0; j < connectables[i].length; ++j) {
                if (connectables[i][j] instanceof DiodeV) {
                    circuit.ajouterDiode((DiodeV) connectables[i][j]);
                } else if (connectables[i][j] instanceof SourceDigitaleV) {
                    circuit.ajouterSourceDigitale((SourceDigitaleV) connectables[i][j]);
                } else if (connectables[i][j] instanceof ANDGateV) {
                    circuit.ajouterANDGate((ANDGateV) connectables[i][j]);
                } else if (connectables[i][j] instanceof ORGateV) {
                    circuit.ajouterORGate((ORGateV) connectables[i][j]);
                } else if (connectables[i][j] instanceof NOTGateV) {
                    circuit.ajouterNOTGate((NOTGateV) connectables[i][j]);
                }
            }
        }
        //creerLiens(circuit);
    }

    public void creerLiens(CircuitAnalogueM circuit) {
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

    public void gererLienDetecte(int i, int j, Noeud noeud, int origine) {
        if (connectables[i][j] instanceof FilA) {

            connectablesPasses[i][j] = true;
            noeud.ajouterFil((FilA) connectables[i][j]);
            retournerEnfants(((FilA) connectables[i][j]), i, j, noeud);

        } else if (connectables[i][j] instanceof ResistanceV) {
            noeud.getResistances().add((ResistanceV) connectables[i][j]);
        } else if (connectables[i][j] instanceof SourceCourantV) {
            noeud.getSourcesCourant().add((SourceCourantV) connectables[i][j]);
        } else if (connectables[i][j] instanceof SourceFEMV) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) {
                noeud.getSourcesFEMNeg().add((SourceFEMV) connectables[i][j]);
            } else {
                noeud.getSourcesFEMPos().add((SourceFEMV) connectables[i][j]);
            }
        } else if (connectables[i][j] instanceof GroundV) {
            noeud.setGround((GroundV) connectables[i][j]);
        } else if (connectables[i][j] instanceof DiodeV) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) {
                noeud.ajouterSortie((ComposantVI) connectables[i][j]);
            } else {
                noeud.ajouterEntree((ComposantVI) connectables[i][j]);
            }
        } else if (connectables[i][j] instanceof NOTGateV) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) {

            } else {

            }
        }/*else if (connectables[i][j] instanceof ){
         if (connectables[i][j].getCotesConnectes()[origine] == -1) {
                
         } else {
                
         }
         }*/


    }

    public void retournerEnfants(FilA fil, int i, int j, Noeud noeud) {

        if (fil.getCotesConnectes()[0] == 1 && i > 0 && connectables[i - 1][j] != null && connectables[i - 1][j].getCotesConnectes()[2] != 0 && !connectablesPasses[i - 1][j]) {
            gererLienDetecte((i - 1), j, noeud, 2);
        }
        if (fil.getCotesConnectes()[1] == 1 && j < connectables[i].length - 1 && connectables[i][j + 1] != null && connectables[i][j + 1].getCotesConnectes()[3] != 0 && !connectablesPasses[i][j + 1]) {
            gererLienDetecte(i, (j + 1), noeud, 3);
        }
        if (fil.getCotesConnectes()[2] == 1 && i < connectables.length - 1 && connectables[i + 1][j] != null && connectables[i + 1][j].getCotesConnectes()[0] != 0 && !connectablesPasses[i + 1][j]) {
            gererLienDetecte((i + 1), j, noeud, 0);
        }
        if (fil.getCotesConnectes()[3] == 1 && j > 0 && connectables[i][j - 1] != null && connectables[i][j - 1].getCotesConnectes()[1] != 0 && !connectablesPasses[i][j - 1]) {
            gererLienDetecte(i, (j - 1), noeud, 1);
        }
    }

}
