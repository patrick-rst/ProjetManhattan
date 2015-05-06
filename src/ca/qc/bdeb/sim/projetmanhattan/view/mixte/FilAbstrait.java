package ca.qc.bdeb.sim.projetmanhattan.view.mixte;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public abstract class FilAbstrait extends ImageChangeable {

    protected double tension;

    public boolean isActif() {
        return tension != 0;
    }

    public FilAbstrait() {
        super(TypeComposant.FIL);
        this.cotesConnectes = new byte[4];
    }

    public double getTension() {
        return tension;
    }

    public void setTension(double tension) {
        this.tension = tension;
    }

}
