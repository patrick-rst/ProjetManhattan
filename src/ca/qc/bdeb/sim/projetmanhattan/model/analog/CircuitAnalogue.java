package ca.qc.bdeb.sim.projetmanhattan.model.analog;

import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Noeud;
import ca.qc.bdeb.sim.projetmanhattan.model.mixte.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Ground;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceFEM;
import java.util.ArrayList;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

/**
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class CircuitAnalogue implements Circuit {

    private ArrayList<Resistance> resistances;
    private ArrayList<Noeud> noeuds;
    private ArrayList<SourceFEM> sourcesFEM;
    private ArrayList<SourceCourant> sourcesCourant;
    private ArrayList<Noeud> noeudsGround;
    private ArrayList<Ground> grounds;

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

    public CircuitAnalogue() {
        resistances = new ArrayList<>();
        noeuds = new ArrayList<>();
        sourcesFEM = new ArrayList<>();
        sourcesCourant = new ArrayList<>();
        grounds = new ArrayList<>();
        noeudsGround = new ArrayList<>();

    }

    public void wipe() {
        resistances.clear();
        noeuds.clear();
        sourcesFEM.clear();
        sourcesCourant.clear();
        grounds.clear();
    }

    @Override
    public void analyserCircuit() {
        boolean grounde = selectionnerNoeudGround();
        if (grounde) {
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

            try {
                construireMatriceG();
                construireMatriceBetC();
                construireMatriceZ();
                combinerMatriceA();
                resoudreCircuitAnalogue();

                distribuerInfos();

            } catch (Exception e) {
                System.out.println("Erreur lors de l'analyse du circuit");
            }
        } else {
            System.out.println("Circuit non groundé!");
        }
    }

    private void distribuerInfos() {
        for (int i = 0; i < nombreNoeuds; ++i) {
            noeuds.get(i).setTension(matriceX[i]);
        }
        for (int i = 0; i < nombreSourcesFEM; ++i) {
            sourcesFEM.get(i).setCourant(matriceX[nombreNoeuds + i]);
        }

        donnerCourantAuxResistances();
    }

    private void donnerCourantAuxResistances() {
        double v1 = 0;
        double v2 = 0;

        for (Resistance resistance : resistances) {
            v1 = 0;
            v2 = 0;
            for (Noeud noeud : noeuds) {
                if (noeud.getResistances().contains(resistance)) {
                    if (v1 == 0) {
                        v1 = noeud.getTension();
                        System.out.println("v1 " + v1);
                    } else {
                        v2 = noeud.getTension();
                        System.out.println("v2 " + v2);
                    }
                }
            }
            double deltaV = Math.abs(v2 - v1);
            resistance.setCourant(deltaV / resistance.getResistance());

        }
    }

    private void resoudreCircuitAnalogue() {
        DenseMatrix64F matA = new DenseMatrix64F(nombreNoeuds + nombreSourcesFEM, nombreNoeuds + nombreSourcesFEM);
        for (int i = 0; i < matriceA.length; ++i) {
            for (int j = 0; j < matriceA[i].length; ++j) {
                matA.set(i, j, matriceA[i][j]);
            }
        }

        DenseMatrix64F matZ = new DenseMatrix64F(nombreNoeuds + nombreSourcesFEM, 1);
        for (int i = 0; i < matriceZ.length; ++i) {
            matZ.set(i, matriceZ[i]);
        }

        DenseMatrix64F matX = new DenseMatrix64F(nombreNoeuds + nombreSourcesFEM, 1);
        if (!CommonOps.solve(matA, matZ, matX)) {
            throw new IllegalArgumentException("Singular matrix");
        }

        for (int i = 0; i < matX.numRows; ++i) {
            for (int j = 0; j < matX.numCols; ++j) {
                matriceX[i] = matX.get(i, j);
            }
        }
    }

    private void construireMatriceZ() {
        for (int i = 0; i < nombreNoeuds; ++i) {
            for (SourceCourant sourceCourant : noeuds.get(i).getSourcesCourant()) {
                matriceZ[i] += sourceCourant.getCourant();
            }
        }

        for (int i = 0; i < nombreSourcesFEM; ++i) {
            matriceZ[i + nombreNoeuds] = sourcesFEM.get(i).getForceElectroMotrice();
        }
    }

    private void construireMatriceBetC() {
        for (int i = 0; i < nombreSourcesFEM; ++i) {
            for (int j = 0; j < nombreNoeuds; ++j) {
                if (noeuds.get(j).getSourcesFEMPos().contains(sourcesFEM.get(i))) {
                    matriceB[j][i] = 1;
                    matriceC[i][j] = 1;
                } else if (noeuds.get(j).getSourcesFEMNeg().contains(sourcesFEM.get(i))) {
                    matriceB[j][i] = -1;
                    matriceC[i][j] = -1;
                }
            }
        }

    }

    private void construireMatriceG() {
        for (int i = 0; i < resistances.size(); ++i) {
            int n1 = -1;
            int n2 = -1;
            double valeurAAjouter = 1 / resistances.get(i).getResistance();
            System.out.println(valeurAAjouter);
            for (int j = 0; j < noeuds.size(); ++j) {
                if (noeuds.get(j).getResistances().contains(resistances.get(i))) {
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

    private boolean selectionnerNoeudGround() {
        boolean enleve = false;
        for (int i = 0; i < noeuds.size(); ++i) {
            if (noeuds.get(i).getGround() != null) {
                noeudsGround.add(noeuds.get(i));
                noeuds.remove(i);
                enleve = true;
                break;
            }
        }
        return enleve;
    }

    private void combinerMatriceA() {
        ajouterMatriceG();
        ajouterMatriceB();
        ajouterMatriceC();
        ajouterMatriceD();
    }

    private void ajouterMatriceG() {
        for (int i = 0; i < matriceG.length; ++i) {
            for (int j = 0; j < matriceG[i].length; ++j) {
                matriceA[i][j] = matriceG[i][j];
            }
        }
    }

    private void ajouterMatriceB() {
        for (int i = 0; i < matriceB.length; ++i) {
            for (int j = 0; j < matriceB[i].length; ++j) {
                matriceA[i][j + nombreNoeuds] = matriceB[i][j];
            }
        }
    }

    private void ajouterMatriceC() {
        for (int i = 0; i < matriceC.length; ++i) {
            for (int j = 0; j < matriceC[i].length; ++j) {
                matriceA[i + nombreNoeuds][j] = matriceC[i][j];
            }
        }
    }

    private void ajouterMatriceD() {
        for (int i = 0; i < matriceD.length; ++i) {
            for (int j = 0; j < matriceD[i].length; ++j) {
                matriceA[i + nombreNoeuds][j + nombreNoeuds] = matriceD[i][j];
            }
        }
    }

    @Override
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

    public void ajouterGround(Ground g) {
        grounds.add(g);
    }

}
