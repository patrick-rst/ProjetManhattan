package ca.qc.bdeb.sim.projetmanhattan.view.digital;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ImageChangeable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import java.util.ArrayList;
import javafx.scene.image.Image;

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
    private boolean actifTemp;

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

    public void flipRepeter() {
        repeter = !repeter;
    }

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

    public void setListeOutput(ArrayList<Boolean> list) {
        this.listeOutput = list;
    }

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
    }

    @Override
    public void updateActif() {
        actifTemp = actif;
        actif = lireOutput();

        if (actifTemp != actif && noeudSortie != null) {
            if (actif) {
                noeudSortie.augmenterTensionDigital();
            } else {
                noeudSortie.diminuerTensionDigital();
            }
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
