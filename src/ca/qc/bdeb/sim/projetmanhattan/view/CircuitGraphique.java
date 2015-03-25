/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import breadboard.Noeud;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class CircuitGraphique {

    private Connectable[][] connectables;
    private boolean[][] connectablesPasses;
    private ArrayList<Lien> liens;
    private ArrayList<Noeud> noeuds;

    public CircuitGraphique(Connectable[][] connectables) {
        this.connectables = connectables;
        connectablesPasses = new boolean[connectables.length][connectables[0].length];
    }

    public void creerLiens() {
        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 1; j < connectables[i].length; ++j) {
                if (!connectablesPasses[i][j]) {
                    connectablesPasses[i][j] = true;
                    if (connectables[i][j] instanceof Fil) {
                        Noeud noeud = new Noeud();
                        noeud.ajouterFil((Fil)connectables[i][j]);
                        
                        //section recurrence
                        
                    }
                }
            }
        }
    }

}
