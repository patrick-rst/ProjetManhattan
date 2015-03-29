/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.model.Composant;
import ca.qc.bdeb.sim.projetmanhattan.model.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.model.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.model.SourceFEM;
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
        int compteNoeuds = 0;
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 1; j < connectables[i].length; ++j) {
                if (!connectablesPasses[i][j]) {
                    connectablesPasses[i][j] = true;
                    if (connectables[i][j] instanceof FilAbstrait) {
                        Noeud noeud = new Noeud();
                        ArrayList<Connectable> membresDuNoeud = new ArrayList<>();
                        membresDuNoeud.add(connectables[i][j]);
                        membresDuNoeud.addAll(retournerEnfants((FilAbstrait) connectables[i][j], i, j, noeud));
                        filtrerMembresDuNoeud(membresDuNoeud);

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

    public void gererLienDetecte(int i, int j, Noeud noeud) {
        if (connectables[i][j] instanceof ResistanceGraphique) {
            noeud.getResistances().add((Resistance) ((ResistanceGraphique) connectables[i][j]).getEnfant());
        } else if (connectables[i][j] instanceof SourceCourantGraphique) {
            noeud.getSourcesCourant().add((SourceCourant) ((SourceCourantGraphique) connectables[i][j]).getEnfant());
        } else if (connectables[i][j] instanceof SourceFEMGraphique) {
            if (connectables[i][j].getCotesConnectes()[2] == -1) {
                noeud.getSourcesFEMNeg().add((SourceFEM) ((SourceFEMGraphique) connectables[i][j]).getEnfant());
            } else {
                noeud.getSourcesFEMPos().add((SourceFEM) ((SourceFEMGraphique) connectables[i][j]).getEnfant());
            }
        } else if (connectables[i][j] instanceof FilAbstrait) {
            noeud.getFils().add((FilAbstrait) connectables[i][j]);
            retournerEnfants(((FilAbstrait) connectables[i][j]), i, j, noeud);
        }
        connectablesPasses[i][j] = false;
    }

    public ArrayList<Connectable> retournerEnfants(FilAbstrait fil, int i, int j, Noeud noeud) {
        ArrayList<Connectable> membresDuNoeud = new ArrayList<>();

        if (fil.getCotesConnectes()[0] == 1 && i > 0 && connectables[i - 1][j].getCotesConnectes()[2] != 0) {
            gererLienDetecte(i - 1, j, noeud);
        }
        if (fil.getCotesConnectes()[1] == 1 && j < connectables[i].length - 1 && connectables[i][j + 1].getCotesConnectes()[3] != 0) {
            gererLienDetecte(i, j + 1, noeud);
        }
        if (fil.getCotesConnectes()[2] == 1 && i < connectables.length - 1 && connectables[i + 1][j].getCotesConnectes()[0] != 0) {
            gererLienDetecte(i + i, j, noeud);
        }
        if (fil.getCotesConnectes()[3] == 1 && j > 0 && connectables[i][j - 1].getCotesConnectes()[1] != 0) {
            gererLienDetecte(i, j - 1, noeud);
        }
        return membresDuNoeud;
    }

}
