package ca.qc.bdeb.sim.projetmanhattan.model;

import ca.qc.bdeb.sim.projetmanhattan.view.FilAbstrait;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class Noeud {
    private ArrayList<FilAbstrait> fils;
    private ArrayList<Byte> connexions;
    private ArrayList<Composant> composants;

    public Noeud() {
        fils = new ArrayList<>();
        connexions = new ArrayList<>();
        composants = new ArrayList<>();
    }
    
    public void ajouterFil(FilAbstrait fil){
        fils.add(fil);
    }
    
    public void ajouterComposant(Composant composant){
        composants.add(composant);
    }
    
    
}
