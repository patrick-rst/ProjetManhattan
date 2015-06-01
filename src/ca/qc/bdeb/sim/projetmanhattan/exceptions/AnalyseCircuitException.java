package ca.qc.bdeb.sim.projetmanhattan.exceptions;

/**
 * Exception lorsque le modèle n'est pas capable d'analyser un circuit
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class AnalyseCircuitException extends RuntimeException {

    public AnalyseCircuitException(String message) {
        super(message);
    }
    
    
    
}
