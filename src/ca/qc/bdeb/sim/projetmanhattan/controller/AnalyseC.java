/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.CircuitAnalogue;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.CircuitDigital;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Composant;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Ground;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ANDGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.Diode;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NOTGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.SourceDigitale;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ComposantDigital;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LogicGateAbstraite;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LumiereOutput;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilAbstrait;

/**
 *
 * @author blood_000
 */
public class AnalyseC {

    private Connectable[][] connectables;
    private boolean[][] connectablesPasses;

    public AnalyseC() {
    }

    public void preparerAnalyse(Circuit circuit, Connectable[][] connectables) {
        try {
            if (circuit instanceof CircuitAnalogue) {
                preparerAnalyseAnalogue((CircuitAnalogue) circuit, connectables);
            } else {
                preparerAnalyseDigitale((CircuitDigital) circuit, connectables);
            }
            creerLiensSansFil(circuit);
            creerLiens(circuit);
        } catch (Exception e) {
            System.out.println("Erreur: Circuit invalide");
        }
    }

    public void preparerAnalyseAnalogue(CircuitAnalogue circuit, Connectable[][] connectables) {

        this.connectables = connectables;
        circuit.wipe();
        connectablesPasses = new boolean[this.connectables.length][this.connectables[0].length];
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 0; j < connectables[i].length; ++j) {
                if (connectables[i][j] instanceof Resistance) {
                    circuit.ajouterResistance((Resistance) connectables[i][j]);
                } else if (connectables[i][j] instanceof SourceCourant) {
                    circuit.ajouterSourceCourant((SourceCourant) connectables[i][j]);
                } else if (connectables[i][j] instanceof SourceFEM) {
                    circuit.ajouterSourceFEM((SourceFEM) connectables[i][j]);
                } else if (connectables[i][j] instanceof Ground) {
                    circuit.ajouterGround((Ground) connectables[i][j]);
                }
            }
        }
    }

    public void preparerAnalyseDigitale(CircuitDigital circuit, Connectable[][] connectables) {
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 0; j < connectables[i].length; ++j) {
                if (connectables[i][j] instanceof LogicGateAbstraite) {
                    circuit.ajouterGate((LogicGateAbstraite) connectables[i][j]);
                } else if (connectables[i][j] instanceof SourceDigitale) {
                    circuit.ajouterSourceDigitale((SourceDigitale) connectables[i][j]);
                } else if (connectables[i][j] instanceof LumiereOutput) {
                    circuit.ajouterLumiere((LumiereOutput) connectables[i][j]);
                }
            }
        }
    }

    public void creerLiensSansFil(Circuit circuit) {
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 0; j < connectables.length; ++j) {
                if (connectables[i][j] instanceof Composant) {
                    if (j < connectables[i].length - 1 && connectables[i][j].getCotesConnectes()[1] != 0 && connectables[i][j + 1] instanceof Composant && connectables[i][j + 1].getCotesConnectes()[3] != 0) {
                        Noeud noeud = new Noeud();
                        gererLienDetecte(i, j + 1, noeud, 3);
                        gererLienDetecte(i, j, noeud, 1);
                        circuit.ajouterNoeud(noeud);
                    }
                    if (i < connectables.length - 1 && connectables[i][j].getCotesConnectes()[2] != 0 && connectables[i + 1][j] instanceof Composant && connectables[i + 1][j].getCotesConnectes()[0] != 0) {
                        Noeud noeud = new Noeud();
                        gererLienDetecte(i + 1, j, noeud, 0);
                        gererLienDetecte(i, j, noeud, 2);
                        circuit.ajouterNoeud(noeud);
                    }
                }
            }
        }
    }

    public void creerLiens(Circuit circuit) {
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 0; j < connectables[i].length; ++j) {
                if (connectables[i][j] instanceof FilAbstrait && !connectablesPasses[i][j]) {

                    connectablesPasses[i][j] = true;
                    Noeud noeud = new Noeud();
                    noeud.ajouterFil((FilAbstrait) connectables[i][j]);
                    retournerEnfants((FilAbstrait) connectables[i][j], i, j, noeud);
                    circuit.ajouterNoeud(noeud);
                }
            }
        }
    }

    public void gererLienDetecte(int i, int j, Noeud noeud, int origine) {
        if (connectables[i][j] instanceof FilAbstrait) {

            connectablesPasses[i][j] = true;
            noeud.ajouterFil((FilAbstrait) connectables[i][j]);
            retournerEnfants(((FilAbstrait) connectables[i][j]), i, j, noeud);

        } else if (connectables[i][j] instanceof Resistance) {
            noeud.getResistances().add((Resistance) connectables[i][j]);
        } else if (connectables[i][j] instanceof SourceCourant) {
            noeud.getSourcesCourant().add((SourceCourant) connectables[i][j]);
        } else if (connectables[i][j] instanceof Ground) {
            noeud.setGround((Ground) connectables[i][j]);
        } else if (connectables[i][j] instanceof SourceFEM) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) {
                noeud.getSourcesFEMNeg().add((SourceFEM) connectables[i][j]);
            } else {
                noeud.getSourcesFEMPos().add((SourceFEM) connectables[i][j]);
            }
        } else if (connectables[i][j] instanceof SourceDigitale) {
            noeud.ajouterEntree((ComposantDigital) connectables[i][j]);
            ((ComposantDigital) connectables[i][j]).ajouterNoeudSortie(noeud);
        } else if (connectables[i][j] instanceof LumiereOutput) {
            noeud.ajouterSortie((ComposantDigital) connectables[i][j]);
            ((ComposantDigital) connectables[i][j]).ajouterNoeudEntree(noeud);
        } else if (connectables[i][j] instanceof Diode) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) {
                noeud.ajouterSortie((ComposantDigital) connectables[i][j]);
                ((ComposantDigital) connectables[i][j]).ajouterNoeudEntree(noeud);
            } else {
                noeud.ajouterEntree((ComposantDigital) connectables[i][j]);
                ((ComposantDigital) connectables[i][j]).ajouterNoeudSortie(noeud);
            }
        } else if (connectables[i][j] instanceof NOTGate) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) {
                noeud.ajouterSortie((ComposantDigital) connectables[i][j]);
                ((ComposantDigital) connectables[i][j]).ajouterNoeudEntree(noeud);
            } else {
                noeud.ajouterEntree((ComposantDigital) connectables[i][j]);
                ((ComposantDigital) connectables[i][j]).ajouterNoeudSortie(noeud);
            }
        } else if (connectables[i][j] instanceof ORGate) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) {
                noeud.ajouterSortie((ComposantDigital) connectables[i][j]);
                ((ComposantDigital) connectables[i][j]).ajouterNoeudEntree(noeud);
            } else {
                noeud.ajouterEntree((ComposantDigital) connectables[i][j]);
                ((ComposantDigital) connectables[i][j]).ajouterNoeudSortie(noeud);
            }
        } else if (connectables[i][j] instanceof ANDGate) {
            if (connectables[i][j].getCotesConnectes()[origine] == -1) {
                noeud.ajouterSortie((ComposantDigital) connectables[i][j]);
                ((ComposantDigital) connectables[i][j]).ajouterNoeudEntree(noeud);
            } else {
                noeud.ajouterEntree((ComposantDigital) connectables[i][j]);
                ((ComposantDigital) connectables[i][j]).ajouterNoeudSortie(noeud);
            }
        }
    }

    public void retournerEnfants(FilAbstrait fil, int i, int j, Noeud noeud) {

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
