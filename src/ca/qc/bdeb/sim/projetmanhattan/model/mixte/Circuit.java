package ca.qc.bdeb.sim.projetmanhattan.model.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public interface Circuit {

    public void wipe();
    public void analyserCircuit();
    public void ajouterNoeud(Noeud noeud);
}
