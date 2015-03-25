package ca.qc.bdeb.sim.projetmanhattan.model;

import ca.qc.bdeb.sim.projetmanhattan.view.Fil;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class Noeud {
    private ArrayList<Fil> fils;
    private ArrayList<Byte> connexions;
    private ArrayList<Composant> composants;

    public Noeud() {
        fils = new ArrayList<>();
        connexions = new ArrayList<>();
        composants = new ArrayList<>();
    }
    
    public void ajouterFil(Fil fil){
        fils.add(fil);
    }
    
    
}
