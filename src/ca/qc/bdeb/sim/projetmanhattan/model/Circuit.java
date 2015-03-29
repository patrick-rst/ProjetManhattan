/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.model;

import java.util.ArrayList;
import org.ejml.simple.SimpleMatrix;

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
        matriceX = new double[nombreNoeuds + nombreSourcesFEM];

    }

    public void analyserCircuit() {
        construireMatriceG();
        construireMatriceBetC();
        construireMatriceZ();
        combinerMatriceA();
        resoudreCircuitAnalogue();
    }

    public void resoudreCircuitAnalogue() {
        SimpleMatrix matA = new SimpleMatrix(nombreNoeuds + nombreSourcesFEM, nombreNoeuds + nombreSourcesFEM);
        for (int i = 0; i < matriceA.length; ++i) {
            for (int j = 0; j < matriceA[i].length; ++j) {
                matA.set(i, j, matriceA[i][j]);
            }
        }

        SimpleMatrix matZ = new SimpleMatrix(nombreNoeuds + nombreSourcesFEM, 1);
        for (int i = 0; i < matriceZ.length; ++i) {
            matZ.set(i, matriceZ[i]);
        }

        try {
            SimpleMatrix matX = matA.solve(matZ);

            for (int i = 0; i < nombreNoeuds + nombreSourcesFEM; ++i) {
                matriceX[i] = matX.get(i);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de ls resolution de la matrice");
        }

    }

    public void construireMatriceZ() {
        for (int i = 0; i < nombreNoeuds; ++i) {
            for (Composant composant : noeuds.get(i).getComposants()) {
                if (composant instanceof SourceCourant) {
                    matriceZ[i] += ((SourceCourant) composant).getCourant();
                }
            }
        }

        for (int i = 0; i < nombreSourcesFEM; ++i) {
            matriceZ[i + nombreNoeuds] = sourcesFEM.get(i).getForceElectroMotrice();
        }
    }

    public void construireMatriceBetC() {
        int n1 = -1;
        int n2 = -2;
        for (int i = 0; i < nombreSourcesFEM; ++i) {
            for (int j = 0; j < nombreNoeuds; ++j) {
                if (noeuds.get(j).getComposants().contains(sourcesFEM.get(i))) {
                    //if (sourcesFEM.get(i).)
                }
            }
        }

    }

    public void construireMatriceG() {
        int n1 = -1;
        int n2 = -1;
        for (int i = 0; i < resistances.size(); ++i) {
            double valeurAAjouter = 1 / resistances.get(i).getResistance();
            for (int j = 0; j < noeuds.size(); ++j) {
                if (noeuds.get(j).getComposants().contains(resistances.get(i))) {
                    if (n1 == -1) {
                        n1 = j;
                    } else {
                        n2 = j;
                    }
                }
            }
            matriceG[n1][n1] += valeurAAjouter;
            if (n2 != -1) {
                matriceG[n2][n2] += valeurAAjouter;
                matriceG[n1][n2] -= valeurAAjouter;
                matriceG[n2][n1] -= valeurAAjouter;
            }

        }
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

    public void combinerMatriceA() {
        ajouterMatriceG();
        ajouterMatriceB();
        ajouterMatriceC();
        ajouterMatriceD();
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
