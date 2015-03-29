/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.Noeud;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class CircuitGraphique {

    private Connectable[][] connectables;
    private boolean[][] connectablesPasses;
    private Circuit circuit;

    public CircuitGraphique(Connectable[][] connectables, Circuit circuit) {
        this.connectables = connectables;
        this.circuit = circuit;
        connectablesPasses = new boolean[connectables.length][connectables[0].length];
    }

    public void creerLiens() {
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 1; j < connectables[i].length; ++j) {
                if (!connectablesPasses[i][j]) {
                    connectablesPasses[i][j] = true;
                    if (connectables[i][j] instanceof FilAbstrait) {
                        ArrayList<Connectable> membresDuNoeud = new ArrayList<>();
                        membresDuNoeud.add(connectables[i][j]);
                        membresDuNoeud.addAll(retournerEnfants((FilAbstrait) connectables[i][j], i, j));
                        filtrerMembresDuNoeud(membresDuNoeud);
                        Noeud noeud = new Noeud();
                        for (Connectable connectable : membresDuNoeud) {
                            if (connectable instanceof FilAbstrait) {
                                noeud.ajouterFil((FilAbstrait) connectable);
                            } else if (connectable instanceof Composant) {
                                noeud.ajouterComposant(((ComposantGraphique) connectable).getEnfant());
                            }
                        }
                        circuit.ajouterNoeud(noeud);
                    }
                }
            }
        }
    }

    public ArrayList<Connectable> filtrerMembresDuNoeud(ArrayList<Connectable> membresDuNoeud) {
        for (int i = 0; i < membresDuNoeud.size(); ++i) {
            for (int j = i + 1; j < membresDuNoeud.size(); ++j) {
                if (membresDuNoeud.get(i) == membresDuNoeud.get(j) || membresDuNoeud.get(j) == null) {
                    membresDuNoeud.remove(j--);
                }
            }
        }
        return membresDuNoeud;
    }

    public ArrayList<Connectable> retournerEnfants(FilAbstrait fil, int i, int j) {
        ArrayList<Connectable> membresDuNoeud = new ArrayList<>();

        if (fil.getCotesConnectes()[0] == 1 && i > 0) {
            membresDuNoeud.add(connectables[i - 1][j]);
            connectablesPasses[i - 1][j] = false;
            if (connectables[i - 1][j] instanceof FilAbstrait) {
                membresDuNoeud.addAll(retournerEnfants(((FilAbstrait) connectables[i - 1][j]), i - 1, j));
            }
        }
        if (fil.getCotesConnectes()[1] == 1 && j < connectables[i].length - 1) {
            membresDuNoeud.add(connectables[i][j + j]);
            connectablesPasses[i][j + 1] = false;
            if (connectables[i][j + 1] instanceof FilAbstrait) {
                membresDuNoeud.addAll(retournerEnfants(((FilAbstrait) connectables[i][j + 1]), i, j + 1));
            }
        }
        if (fil.getCotesConnectes()[2] == 1 && i < connectables.length - 1) {
            membresDuNoeud.add(connectables[i + 1][j]);
            connectablesPasses[i + 1][j] = false;
            if (connectables[i + 1][j] instanceof FilAbstrait) {
                membresDuNoeud.addAll(retournerEnfants(((FilAbstrait) connectables[i + 1][j]), i + 1, j));
            }
        }
        if (fil.getCotesConnectes()[3] == 1 && j > 0) {
            membresDuNoeud.add(connectables[i][j - 1]);
            connectablesPasses[i][j - 1] = false;
            if (connectables[i][j - 1] instanceof FilAbstrait) {
                membresDuNoeud.addAll(retournerEnfants(((FilAbstrait) connectables[i][j - 1]), i, j - 1));
            }
        }
        return membresDuNoeud;
    }

}
