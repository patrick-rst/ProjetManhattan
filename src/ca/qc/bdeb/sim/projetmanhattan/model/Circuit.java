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

    private int nombreNoeuds;
    private int nombreSourcesFEM;
    private int nombreSourcesCourant;

    private int[][] matriceA;
    private int[][] matriceG;
    private int[][] matriceB;
    private int[][] matriceC;
    private int[][] matriceD;
    private int[] matriceZ;
    private int[] matriceI;
    private int[] matriceE;
    private int[] matriceX;

    public Circuit() {
        resistances = new ArrayList<>();
        noeuds = new ArrayList<>();
        sourcesFEM = new ArrayList<>();
        sourcesCourant = new ArrayList<>();

        //methode qui remplit les listes
        nombreNoeuds = noeuds.size();
        nombreSourcesFEM = sourcesFEM.size();
        nombreSourcesCourant = sourcesCourant.size();

        matriceA = new int[nombreNoeuds + nombreSourcesFEM][nombreNoeuds + nombreSourcesFEM];
        matriceG = new int[nombreNoeuds][nombreNoeuds];
        matriceB = new int[nombreNoeuds][nombreSourcesFEM];
        matriceC = new int[nombreSourcesFEM][nombreNoeuds];
        matriceD = new int[nombreSourcesFEM][nombreSourcesFEM];
        matriceZ = new int[nombreNoeuds + nombreSourcesFEM];
        matriceI = new int[nombreSourcesCourant];
        matriceE = new int[nombreSourcesFEM];
        matriceX = new int[nombreNoeuds + nombreSourcesFEM];

    }

    public void combinerMatriceZ(){
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
    
    public void ajouterNoeud(Noeud noeud){
        noeuds.add(noeud);
    }

}
