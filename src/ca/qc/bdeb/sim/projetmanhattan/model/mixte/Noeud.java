package ca.qc.bdeb.sim.projetmanhattan.model.mixte;

import ca.qc.bdeb.sim.projetmanhattan.view.analog.Ground;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ComposantDigital;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilAbstrait;
import java.util.ArrayList;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class Noeud {

    private double tension;

    private final ArrayList<FilAbstrait> fils;
    private final ArrayList<SourceFEM> sourcesFEMNeg;
    private final ArrayList<SourceFEM> sourcesFEMPos;
    private final ArrayList<Resistance> resistances;
    private final ArrayList<SourceCourant> sourcesCourant;
    private Ground ground;

    private final ArrayList<ComposantDigital> entrees;
    private final ArrayList<ComposantDigital> sorties;

    /**
     * Initialise le Noeud et ses variables
     */
    public Noeud() {
        fils = new ArrayList<>();
        resistances = new ArrayList<>();
        sourcesCourant = new ArrayList<>();
        sourcesFEMNeg = new ArrayList<>();
        sourcesFEMPos = new ArrayList<>();
        entrees = new ArrayList<>();
        sorties = new ArrayList<>();
    }

    /**
     * S'assure que le noeud est au bon statut. Utilise avant d'actualiser les
     * images.
     */
    public void assurerCourant() {
        for (int i = 0; i < entrees.size() && !isActif(); ++i) {
            if (entrees.get(i).isActif()) {
                allumerNoeud();
            }
        }
    }

    /**
     * Ajoute un composant qui envoie du courant dans le noeud à la liste de
     * composants qui envoient du courant dans le noeud
     *
     * @param comp le composant qui envoie du courant dans le noeud
     */
    public void ajouterEntree(ComposantDigital comp) {
        entrees.add(comp);
    }

    /**
     *
     * @param comp Ajoute ce composant à la liste de composants qui recoivent du
     * courant du noeud
     */
    public void ajouterSortie(ComposantDigital comp) {
        sorties.add(comp);
    }

    /**
     *
     * @return la liste de composants qui envoient du courant dans le noeud
     */
    public ArrayList<ComposantDigital> getEntrees() {
        return entrees;
    }

    /**
     *
     * @return la liste de composants qui recoivent du courant du noeud
     */
    public ArrayList<ComposantDigital> getSorties() {
        return sorties;
    }

    /**
     *
     * @param fil ajoute ce fil à la liste de fils
     */
    public void ajouterFil(FilAbstrait fil) {
        fils.add(fil);
    }

    /**
     *
     * @return la liste de résistances connectées au noeud
     */
    public ArrayList<Resistance> getResistances() {
        return resistances;
    }

    /**
     *
     * @return la liste de sourcesFEM connectées au noeud par la borne négative
     */
    public ArrayList<SourceFEM> getSourcesFEMNeg() {
        return sourcesFEMNeg;
    }

    /**
     *
     * @return la liste de sourcesFEM connectées au noeud par la borne positive
     */
    public ArrayList<SourceFEM> getSourcesFEMPos() {
        return sourcesFEMPos;
    }

    /**
     *
     * @return la liste de sources de courant connectées au noeud
     */
    public ArrayList<SourceCourant> getSourcesCourant() {
        return sourcesCourant;
    }

    /**
     *
     * @return la tension présente dans le noeud
     */
    public double getTension() {
        return tension;
    }

    /**
     * met à jour la tension présente dans le noeud
     *
     * @param tension la tension présente dans le noeud
     */
    public void setTension(double tension) {
        this.tension = tension;
        for (FilAbstrait fil : fils) {
            fil.setTension(tension);
        }
    }

    /**
     *
     * @return le ground connecté au noeud
     */
    public Ground getGround() {
        return ground;
    }

    /**
     * Selectionne le ground connecté au noeud
     *
     * @param ground Le ground connecté au noeud
     */
    public void setGround(Ground ground) {
        this.ground = ground;
    }

    /**
     * Change le statut du noeud de off à on et change la tension dans les noeud
     * pour actualiser l'image
     */
    public void allumerNoeud() {
        if (!isActif()) {
            ++tension;
        }
        setTension(tension);
        if (tension == 1) {
            for (ComposantDigital comp : sorties) {
                comp.updateActif();
            }
        }
    }

    /**
     * Change le statut du noeud de on à off et change la tension dans les noeud
     * pour actualiser l'image
     *
     */
    public void eteindreNoeud() {
        if (isActif()) {
            --tension;
        }
        setTension(tension);
        if (tension == 0) {
            for (ComposantDigital comp : sorties) {
                comp.updateActif();
            }
        }
    }

    /**
     *
     * @return indique si le noeud est allumé ou éteint.
     */
    public boolean isActif() {
        return tension != 0;
    }

}
