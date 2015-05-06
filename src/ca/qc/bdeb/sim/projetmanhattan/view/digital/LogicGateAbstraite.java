/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ImageChangeable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public abstract class LogicGateAbstraite extends ImageChangeable implements ComposantDigital {

    protected byte entrees;

    protected byte typeGate;

    protected Noeud noeudEntreeA;
    protected Noeud noeudEntreeB;
    protected Noeud noeudSortie;
    protected boolean actifTemp;
    protected boolean passee;
    protected boolean aBoucler;

    public LogicGateAbstraite(TypeComposant typeComposant) {
        super(typeComposant);

        typeGate = -1;
        switchGate();

    }

    public boolean isActif() {
        return actif;
    }

    @Override
    public void ajouterNoeudEntree(Noeud noeud) {
        if (noeudEntreeA == null) {
            noeudEntreeA = noeud;
        } else if (noeudEntreeB == null) {
            noeudEntreeB = noeud;
        } else {
            System.out.println("Erreur: Logic Gate mal connectée");
        }
    }

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

    public abstract void calculerCourant();

    protected void transfererCourant() {
        passee = true;
        if (actifTemp != actif) {
            if (actif) {
                System.out.println("on");
                noeudSortie.augmenterTensionDigital();
            } else {
                noeudSortie.diminuerTensionDigital();
            }
        }
    }

    public Noeud getNoeudSortie() {
        return noeudSortie;
    }

    public Noeud getNoeudEntreeA() {
        return noeudEntreeA;
    }

    public Noeud getNoeudEntreeB() {
        return noeudEntreeB;
    }

    public boolean isABoucler() {
        return aBoucler;
    }

    @Override
    public void updateActif() {
        if (passee) {
            aBoucler = true;
        } else {
            calculerCourant();
        }
    }

    @Override
    public void ajouterNoeudSortie(Noeud noeud) {
        this.noeudSortie = noeud;
    }

    public void setPassee(boolean passee) {
        this.passee = passee;
    }

    public void setaBoucler(boolean aBoucler) {
        this.aBoucler = aBoucler;
    }

    public void resetBools() {
        passee = false;
        aBoucler = false;
    }

    public boolean isPassee() {
        return passee;
    }

}
