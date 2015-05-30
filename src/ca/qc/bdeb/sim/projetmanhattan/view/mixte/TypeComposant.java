package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 *
 * La liste de tous les types de composants utilisés dans le programme. Sert à
 * la détection de la validité des composants dans l'interface.
 */
public enum TypeComposant {

    FIL,
    GROUND,
    RESISTANCE,
    SOURCE_TENSION,
    SOURCE_COURANT,
    SOURCE_DIGITALE,
    DIODE,
    ANDGATE,
    ORGATE,
    NOTGATE,
    NAND_GATE,
    NOR_GATE,
    XOR_GATE,
    XNOR_GATE,
    LUMIERE_OUTPUT
}
