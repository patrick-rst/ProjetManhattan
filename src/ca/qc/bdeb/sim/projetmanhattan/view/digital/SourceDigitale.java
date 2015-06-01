package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ImageChangeable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import java.util.ArrayList;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class SourceDigitale extends ImageChangeable implements ComposantDigital {

    private ArrayList<Boolean> listeOutput;
    private int indexOutput;
    private boolean repeter;

    private Noeud noeudSortie;

    /**
     * Initialise l'instance de la classe et ses variables si nécessaire.
     */
    public SourceDigitale() {
        super(TypeComposant.SOURCE_DIGITALE);
        listeOutput = new ArrayList<>();
        setListeOutput("1100");
        cotesConnectes[1] = 1;
        repeter = true;
        addImageActif("sourceDigitaleon.png");
        addImage("sourceDigitale.png");
        imageActive = listeImages.get(0);
    }

    /**
     * remet le cycle d'output au debut
     */
    public void remettreAZero() {
        indexOutput = -1;
    }

    private void flipRepeter() {
        repeter = !repeter;
    }

    /**
     *
     * @return l'état de la variable repeter, soit si la source doit rouler en
     * boucle ou non
     */
    public boolean isRepeter() {
        return repeter;
    }

    private boolean lireOutput() {
        ++indexOutput;
        if (repeter || indexOutput < listeOutput.size()) {
            indexOutput = indexOutput % listeOutput.size();

            actif = listeOutput.get(indexOutput);
        } else {
            actif = false;
        }
        return actif;
    }

    /**
     *
     * @param list la liste des intervalles ou la source émet un courant
     * (on/off)
     */
    public void setListeOutput(ArrayList<Boolean> list) {
        this.listeOutput = list;
    }

    /**
     *
     * @param string la liste des intervalles ou la source émet un courant
     * (on/off)
     */
    public void setListeOutput(String string) {
        ArrayList<Character> liste = new ArrayList<>();
        for (int i = 0; i < string.length(); ++i) {
            liste.add(string.charAt(i));
        }
        for (int i = 0; i < liste.size(); ++i) {
            if (liste.get(i) != '1' && liste.get(i) != '0') {
                liste.remove(i);
                --i;
            }
        }
        listeOutput.clear();
        for (int i = 0; i < liste.size(); ++i) {
            if (liste.get(i) == '1') {
                listeOutput.add(Boolean.TRUE);
            } else {
                listeOutput.add(Boolean.FALSE);
            }
        }
        
        if (listeOutput.size() == 0) {
            listeOutput.add(Boolean.FALSE);
        }
    }

    /**
     * Retourne une chaine contenant la liste des intervalles ou la source émet un courant
     * @return une chaine de 0 et 1
     */
    public String getListeOutput() {
        String chaine = "";
        for (boolean b : listeOutput) {
            if (b) {
                chaine = chaine + "1";
            } else {
                chaine = chaine + "0";
            }
        }
        return chaine;
    }
    
    

    @Override
    public void updateActif() {
        actif = lireOutput();

        if (actif && noeudSortie != null) {
            noeudSortie.allumerNoeud();
        } else {
            noeudSortie.eteindreNoeud();
        }
    }

    @Override
    public void ajouterNoeudEntree(Noeud noeud) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ajouterNoeudSortie(Noeud noeud) {
        this.noeudSortie = noeud;
    }

    @Override
    public boolean isActif() {
        return actif;
    }

}
