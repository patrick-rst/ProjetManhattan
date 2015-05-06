package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import java.io.Serializable;

/**
 * Sauvegarde d'un circuit
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class Sauvegarde implements Serializable {

    private Connectable[][] circuit;

    /**
     * Crée un object Sauvegarde pour contenir un circuit
     *
     * @param size la grandeur du tableau 2D
     */
    public Sauvegarde(int size) {
        circuit = new Connectable[size][size];
    }

    /**
     * Set le circuit à sauvegarder
     *
     * @param circuit le circuit à sauvergarder
     */
    public void setCircuit(Connectable[][] circuit) {
        this.circuit = circuit;
    }

    /**
     * Retourne le circuit dans le sauvegarde
     *
     * @return le circuit dans la sauvegarde
     */
    public Connectable[][] getCircuit() {
        return circuit;
    }

}
