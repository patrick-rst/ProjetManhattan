/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Composant;

/**
 *
 * @author blood_000
 */
public interface ComposantDigital extends Composant {

    public void updateActif();

    public void ajouterNoeudEntree(Noeud noeud);

    public void ajouterNoeudSortie(Noeud noeud);
}
