package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.exceptions.LogicGateConnectionException;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ImageChangeable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public abstract class LogicGateAbstraite extends ImageChangeable implements ComposantDigital {

    /**
     * Le type de porte logique utilisé pour la validation dans l'interface
     */
    protected byte typeGate;

    /**
     * Le premier noeud qui envoie du courant à la porte
     */
    protected Noeud noeudEntreeA;

    /**
     * le deuxieme noeud qui envoie du courant à la porte, si applicable (ex:
     * diode vs NORgate
     */
    protected Noeud noeudEntreeB;

    /**
     * Le noeud qui recoit du courant de la porte logique
     */
    protected Noeud noeudSortie;

    /**
     * La variable qui détecte le changement dans l'état (on/off) du al porte
     * logique
     */
    protected boolean actifTemp;

    /**
     * Variable qui détermine si la porte logique a déjà été analysée dans une
     * passe dans la boucle d'analyse
     */
    protected boolean passee;

    /**
     * Détermine si la porte doit etre analysée à nouveau pour le 'flicker'
     */
    protected boolean aBoucler;

    /**
     * Initialise la porte logique et ses parametres
     *
     * @param typeComposant Le type de porte logique
     */
    public LogicGateAbstraite(TypeComposant typeComposant) {
        super(typeComposant);

        typeGate = -1;
        switchGate();

    }

    /**
     *
     * @return la valeur de la variable actif
     */
    @Override
    public boolean isActif() {
        return actif;
    }

    /**
     * Remet les connexions de la porte logique à zéro. Évite les bugs
     */
    public void resetConnexions() {
        noeudEntreeA = null;
        noeudEntreeB = null;
        noeudSortie = null;
    }

    /**
     *
     * @param noeud ajoute un noeud d'entree à la liste de noeuds d'entree
     */
    @Override
    public void ajouterNoeudEntree(Noeud noeud) {
        if (noeudEntreeA == null) {
            noeudEntreeA = noeud;
        } else if (noeudEntreeB == null) {
            noeudEntreeB = noeud;
        } else {
            throw new LogicGateConnectionException("Erreur: Logic Gate mal connectée");
        }
    }

    /**
     * Place les cotesConnectes au bons endroits pour matcher avec l'image que
     * l'utilisateur voit.
     */
    public void switchGate() {
        ++typeGate;
        typeGate %= 3;

        if (typeGate == 0) {
            cotesConnectes[0] = -1;
            cotesConnectes[1] = 1;
            cotesConnectes[2] = -1;
            cotesConnectes[3] = 0;
        } else if (typeGate == 1) {
            cotesConnectes[0] = -1;
            cotesConnectes[1] = 1;
            cotesConnectes[2] = 0;
            cotesConnectes[3] = -1;
        } else if (typeGate == 2) {
            cotesConnectes[0] = 0;
            cotesConnectes[1] = 1;
            cotesConnectes[2] = -1;
            cotesConnectes[3] = -1;
        }

        //showImage
    }

    /**
     * Détermine si la porte logique doit envoyer du courant ou non selon les
     * inputs qu'elle reçoit*
     */
    protected abstract void calculerCourant();

    /**
     * Détermine si les noeuds de sortie connectés doivent être actualisés suite
     * à un changement d'état*
     */
    protected void transfererCourant() {
        passee = true;

        if (actifTemp != actif) {
            if (actif && noeudSortie != null) {
                noeudSortie.allumerNoeud();
            } else {
                noeudSortie.eteindreNoeud();
            }
        }
    }

    /**
     *
     * @return le noeud qui recoit du courant de la porte
     */
    public Noeud getNoeudSortie() {
        return noeudSortie;
    }

    /**
     *
     * @return Le premier noeud qui envoie du courant à la porte
     */
    public Noeud getNoeudEntreeA() {
        return noeudEntreeA;
    }

    /**
     *
     * @return Le deuxieme noeud qui envoie du courant à la porte
     *
     */
    public Noeud getNoeudEntreeB() {
        return noeudEntreeB;
    }

    /**
     *
     * @return la variable qui sert à afficher le 'flicker'
     */
    public boolean isABoucler() {
        return aBoucler;
    }

    @Override
    public void updateActif() {
        if (passee) {
            aBoucler = true;
        } else {
            actifTemp = actif;
            calculerCourant();
            transfererCourant();
        }
    }

    @Override
    public void ajouterNoeudSortie(Noeud noeud) {
        this.noeudSortie = noeud;
    }

    /**
     *
     * @param passee attribue l'état de la variable passée (optimisation et
     * flicker)
     */
    public void setPassee(boolean passee) {
        this.passee = passee;
    }

    /**
     *
     * @param aBoucler attribue l'état de la variable responsable du flicker
     */
    public void setaBoucler(boolean aBoucler) {
        this.aBoucler = aBoucler;
    }

    /**
     * remet la porte à zéro pour une prochaine analyse
     */
    public void resetBools() {
        passee = false;
        aBoucler = false;
    }

    /**
     *
     * @return renvoie l'état de la variable passee
     */
    public boolean isPassee() {
        return passee;
    }

}
