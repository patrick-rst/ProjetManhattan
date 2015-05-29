package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public abstract class FilAbstrait extends ImageChangeable {

    /**
     * La tension qui circule dans le fil. Sert à actualiser l'image (actif ou
     * non)
     */
    protected double tension;

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public FilAbstrait() {
        super(TypeComposant.FIL);
        this.cotesConnectes = new byte[4];
    }

    private double getTension() {
        return tension;
    }

    /**
     *
     * @param tension attribue la tension présente dans le fil
     */
    public void setTension(double tension) {
        this.tension = tension;
    }

    /**
     *
     * @return true si la tension != 0, sinon false.
     */
    @Override
    public boolean isActif() {
        return tension != 0;
    }

}
