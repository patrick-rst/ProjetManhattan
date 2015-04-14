/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.ComposantI;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class NoeudDigital {

    private ArrayList<ComposantI> sources;
    private ArrayList<ComposantI> sorties;
    private boolean actif;

    public NoeudDigital() {
        sources = new ArrayList<>();
        sorties = new ArrayList<>();

    }

    public void ajouterSource(ComposantI comp) {
        sources.add(comp);
    }

    public void ajouterSortie(ComposantI comp) {
        sorties.add(comp);
    }

    public ArrayList<ComposantI> getSorties() {
        return sorties;
    }

    public ArrayList<ComposantI> getSources() {
        return sources;
    }

}
