/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.qc.bdeb.sim.projetmanhattan.view;

import ca.qc.bdeb.sim.projetmanhattan.model.Noeud;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author blood_000
 */
public class CircuitGraphique {

    private Connectable[][] connectables;
    private boolean[][] connectablesPasses;
    private ArrayList<Lien> liens;
    private ArrayList<Noeud> noeuds;

    public CircuitGraphique(Connectable[][] connectables) {
        this.connectables = connectables;
        connectablesPasses = new boolean[connectables.length][connectables[0].length];
    }

    public void creerLiens() {

        for (int i = 0; i < connectables.length; ++i) {
            for (int j = 1; j < connectables[i].length; ++j) {
                if (!connectablesPasses[i][j]) {
                    connectablesPasses[i][j] = true;
                    if (connectables[i][j] instanceof Fil) {
                        Noeud noeud = new Noeud();
                        noeud.ajouterFil((Fil) connectables[i][j]);

                        //section recurrence
                        ArrayList<Point> casesAttachees = trouverCasesAttachees(i, j, ((Fil) connectables[i][j]).getType(), ((Fil) connectables[i][j]).getOrientation(), null);
                    }
                }
            }
        }
    }

    private ArrayList<Point> trouverCasesAttachees(int i, int j, int type, int orientation, Point pointAEnlever) {
        //orientation: 1 = haut, 2 = droit, 3 = bas, 4 = gauche
        //type: 1 = droit, 2 = coin, 3 = t, 4 = croix
        ArrayList<Point> points = new ArrayList<>();
        if (type == 1 || type == 3 || type == 4) {
            if (orientation == 1 || orientation == 3) {
                points.add(new Point(i - 1, j));
                points.add(new Point(i + 1, j));
            } else if (orientation == 2 || orientation == 4) {
                points.add(new Point(i, j - 1));
                points.add(new Point(i, j + 1));
            }
        } else if (type == 2) {
            if (orientation == 1) {
                points.add(new Point(i - 1, j));
                points.add(new Point(i, j + 1));
            } else if (orientation == 2) {
                points.add(new Point(i, j + 1));
                points.add(new Point(i + 1, j));
            } else if (orientation == 3) {
                points.add(new Point(i + 1, j));
                points.add(new Point(i, j - 1));
            } else if (orientation == 4) {
                points.add(new Point(i, j - 1));
                points.add(new Point(i - 1, j));
            }
        }
        if (type == 3) {
            if (orientation == 1) {
                points.add(new Point(i, j + 1));
            } else if (orientation == 2) {
                points.add(new Point(i + 1, j));
            } else if (orientation == 3) {
                points.add(new Point(i + 1, j));
            } else if (orientation == 4) {
                points.add(new Point(i, j - 1));
            }
        }
        if (type == 4) {
            if (orientation == 1 || orientation == 3) {
                if (orientation == 1) {
                    points.add(new Point(i, j + 1));
                    points.add(new Point(i, j - 1));
                } else if (orientation == 2 || orientation == 4) {
                    points.add(new Point(i - 1, j));
                    points.add(new Point(i + 1, j));
                }
            }
        }

        return points;
    }

}
