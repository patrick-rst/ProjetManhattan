/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model;

import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class Circuit {

    private ArrayList<Resistance> resistances;
    private ArrayList<Noeud> noeuds;
    private ArrayList<SourceFEM> sourcesFEM;
    private ArrayList<SourceCourant> sourcesCourant;
    private Noeud noeudGround;

    private int nombreNoeuds;
    private int nombreSourcesFEM;
    private int nombreSourcesCourant;

    private double[][] matriceA;
    private double[][] matriceG;
    private double[][] matriceB;
    private double[][] matriceC;
    private double[][] matriceD;
    private double[] matriceZ;
    private double[] matriceI;
    private double[] matriceE;
    private double[] matriceX;

    public Circuit() {
        resistances = new ArrayList<>();
        noeuds = new ArrayList<>();
        sourcesFEM = new ArrayList<>();
        sourcesCourant = new ArrayList<>();

        //methode qui remplit les listes
        nombreNoeuds = noeuds.size();
        nombreSourcesFEM = sourcesFEM.size();
        nombreSourcesCourant = sourcesCourant.size();

        matriceA = new double[nombreNoeuds + nombreSourcesFEM][nombreNoeuds + nombreSourcesFEM];
        matriceG = new double[nombreNoeuds][nombreNoeuds];
        matriceB = new double[nombreNoeuds][nombreSourcesFEM];
        matriceC = new double[nombreSourcesFEM][nombreNoeuds];
        matriceD = new double[nombreSourcesFEM][nombreSourcesFEM];
        matriceZ = new double[nombreNoeuds + nombreSourcesFEM];
        matriceI = new double[nombreSourcesCourant];
        matriceE = new double[nombreSourcesFEM];
        matriceX = new double[nombreNoeuds + nombreSourcesFEM];

    }

    public void selectionnerNoeudGround() {
        for (int i = 0; i < noeuds.size(); ++i) {
            Noeud noeud = noeuds.get(i);
            for (int j = 0; j < noeuds.get(i).getComposants().size(); ++j) {
                if (noeuds.get(i).getComposants().get(j) instanceof Ground) {
                    noeudGround = noeuds.get(i);
                    noeuds.remove(i);
                    break;
                }
            }
        }
    }

    public void combinerMatriceZ() {
        ajouterMatriceI();
        ajouterMatriceE();
    }

    public void combinerMatriceA() {
        ajouterMatriceG();
        ajouterMatriceB();
        ajouterMatriceC();
        ajouterMatriceD();
    }

    public void ajouterMatriceI() {
        for (int i = 0; i < matriceI.length; ++i) {
            matriceZ[i] = matriceI[i];
        }
    }

    public void ajouterMatriceE() {
        for (int i = 0; i < matriceE.length; ++i) {
            matriceZ[i + nombreSourcesCourant] = matriceE[i];
        }
    }

    public void ajouterMatriceG() {
        for (int i = 0; i < matriceG.length; ++i) {
            for (int j = 0; j < matriceG[i].length; ++j) {
                matriceA[i][j] = matriceG[i][j];
            }
        }
    }

    public void ajouterMatriceB() {
        for (int i = 0; i < matriceB.length; ++i) {
            for (int j = 0; j < matriceB[i].length; ++j) {
                matriceA[i][j + nombreNoeuds] = matriceB[i][j];
            }
        }
    }

    public void ajouterMatriceC() {
        for (int i = 0; i < matriceC.length; ++i) {
            for (int j = 0; j < matriceC[i].length; ++j) {
                matriceA[i + nombreNoeuds][j] = matriceG[i][j];
            }
        }
    }

    public void ajouterMatriceD() {
        for (int i = 0; i < matriceD.length; ++i) {
            for (int j = 0; j < matriceD[i].length; ++j) {
                matriceA[i + nombreNoeuds][j + nombreNoeuds] = matriceG[i][j];
            }
        }
    }

    public void ajouterNoeud(Noeud noeud) {
        noeuds.add(noeud);
    }

    public void ajouterResistance(Resistance resistance) {
        resistances.add(resistance);
    }

    public void ajouterSourceFEM(SourceFEM sourceFEM) {
        sourcesFEM.add(sourceFEM);
    }

    public void ajouterSourceCourant(SourceCourant sourceCourant) {
        sourcesCourant.add(sourceCourant);
    }

}
