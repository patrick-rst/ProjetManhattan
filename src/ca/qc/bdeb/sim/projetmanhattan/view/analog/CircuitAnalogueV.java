/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.analog;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ConnectableV;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilA;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.CircuitAnalogueM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.GroundM;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.ResistanceM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceCourantM;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.SourceFEMM;

/**
 *
 * @author blood_000
 */
public class CircuitAnalogueV {

    private ConnectableV[][] connectables;
    private boolean[][] connectablesPasses;
    private CircuitAnalogueM circuit;

    public CircuitAnalogueV(CircuitAnalogueM circuit) {
        this.circuit = circuit;
    }

    public void preparerAnalyse(ConnectableV[][] connectables) {
        try{
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
        creerLiens();
        } catch(Exception e){
            System.out.println("Erreur: Circuit invalide");
        }
    }

    public void creerLiens() {
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
