package ca.qc.bdeb.sim.projetmanhattan.exceptions;

/**
 * Exception lorsqu'il n'a pas de ground dans le circuit
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class CircuitPasGroundException extends RuntimeException {

    public CircuitPasGroundException(String message) {
        super(message);
    }

}
