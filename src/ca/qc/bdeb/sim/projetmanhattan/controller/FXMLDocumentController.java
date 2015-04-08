package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.model.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.view.CircuitGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.FilCoin;
import ca.qc.bdeb.sim.projetmanhattan.view.FilCroix;
import ca.qc.bdeb.sim.projetmanhattan.view.FilDroit;
import ca.qc.bdeb.sim.projetmanhattan.view.FilT;
import ca.qc.bdeb.sim.projetmanhattan.view.ResistanceGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.SourceCourantGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.SourceFEMGraphique;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import org.controlsfx.control.PopOver;

/**
 * Controlleur
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class FXMLDocumentController implements Initializable {


    Connectable[][] circuit = new Connectable[10][10];

    PopOver composantEditor = new PopOver();

    @FXML
    BorderPane pane;
    
    @FXML
    GridPane grid;

    Circuit c;
    CircuitGraphique cg;

    public void setC(Circuit c) {
        this.c = c;
    }

    public void setCg(CircuitGraphique cg) {
        this.cg = cg;
    }
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MenuBar mnuBar = new MenuBar();
        Menu mnuFile = new Menu("File");
        Menu mnuRun = new Menu("Run");
        
        MenuItem mnuItemSave = new MenuItem("Save");
        MenuItem mnuItemLoad = new MenuItem("Load");
        
        mnuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
               Sauvegarde save = new Sauvegarde();
               save.setCircuit(circuit);
               
//                ObjectOutputStream fichier = null;
//                try {
//                    File f = new File("save1.ser");
//                    f.delete();
//                    fichier = new ObjectOutputStream(new FileOutputStream("save1.ser"));
//                    fichier.writeObject(save);
//                    fichier.close();
//                } catch (FileNotFoundException e) {
//                    System.out.println("Erreur: Impossible de créer le fichier pour l'écriture");
//                } catch (IOException e) {
//                    System.out.println("Erreur: I/O durant l'écriture");
//                    printCircuitArray(save.getCircuit());
//                } finally {
//                    try {
//                        if (fichier != null) {
//                            fichier.close();
//                        }  
//                    } catch (IOException e) {
//                        System.out.println("Erreur: I/O durant la fermeture");
//                    }            
//                } 
               
               
                try {
                    FileOutputStream fout = new FileOutputStream("save2.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(save);
                    oos.close();
                    System.out.println("Done");

                } catch (IOException ex) {
                    ex.printStackTrace();
                }              
               
               
               
               
               
               
               
            }
        });        
        
        mnuItemLoad.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
//                Sauvegarde save = null;
//                ObjectInputStream fichier = null;
//                try {
//                    fichier = new ObjectInputStream(new FileInputStream("save1.ser"));
//                    Object contenu = fichier.readObject();
//                    save = (Sauvegarde) contenu;
//                } catch (EOFException e) {
//                } catch (FileNotFoundException e) {
//                    System.out.println("Erreur: Impossible de lire le fichier");
//                } catch (IOException e) {
//                    System.out.println("Erreur: I/O durant la lecture");
//                } catch (ClassNotFoundException e) {
//                    System.out.println("Erreur: Le contenu n'appartient pas à la classe Sauvegarde");
//                } finally {
//                    try {
//                        if (fichier != null) {
//                            fichier.close();
//                        }  
//                    } catch (IOException e) {
//                        System.out.println("Erreur: I/O durant la fermeture");
//                    }
//                }
//                circuit = save.getCircuit();
                
                Sauvegarde save = null;
                
                try {
                    FileInputStream fin = new FileInputStream("save2.ser");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    save = (Sauvegarde) ois.readObject();
                    ois.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }                
                
                circuit = save.getCircuit();

                for (int i=0; i<10; i++) {
                    for (int j=0; j<10; j++) {
                        if (circuit[i][j] instanceof ResistanceGraphique) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/resistance.png"));
                            tmp.setId("resistance");
                            grid.add(tmp, i, j);
                        }
                        else if (circuit[i][j] instanceof SourceFEMGraphique) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/source_tension.png"));
                            tmp.setId("sourceTension");
                            grid.add(tmp, i, j);
                        }
                        else if (circuit[i][j] instanceof SourceCourantGraphique) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/source_courant.png"));
                            tmp.setId("sourceCourant");
                            grid.add(tmp, i, j);
                        }      
                        else if (circuit[i][j] instanceof FilDroit) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/fil_droit.png"));
                            tmp.setId("filDroit");
                            //grid.add(tmp, i, j);
                            GridPane.setConstraints(tmp, j, i);
                            grid.getChildren().add(tmp);                            
                        } 
                        else if (circuit[i][j] instanceof FilCoin) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/fil_coin.png"));
                            tmp.setId("filCoin");
                            grid.add(tmp, i, j);
                        }
                        else if (circuit[i][j] instanceof FilT) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/fil_t.png"));
                            tmp.setId("filT");
                            grid.add(tmp, i, j);
                        }
                        else if (circuit[i][j] instanceof FilCroix) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/fil_croix.png"));
                            tmp.setId("filCroix");
                            grid.add(tmp, i, j);
                        }                        
                        
                        
                    }
                }
               
                System.out.println("Done reading");
                
            }
        });        
        
        
        MenuItem mnuItemRun = new MenuItem("Run");
        
        mnuItemRun.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                cg.preparerAnalyse(circuit);
                c.analyserCircuit();                
            }
        });        
 
        mnuFile.getItems().addAll(mnuItemSave,mnuItemLoad);
        mnuRun.getItems().addAll(mnuItemRun);        
        mnuBar.getMenus().addAll(mnuFile,mnuRun);
        
        
        pane.setTop(mnuBar);
        
        
    }
    
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }   
    

    @FXML
    private void analyserCircuit(ActionEvent event) {
        cg.preparerAnalyse(circuit);
        c.analyserCircuit();
    }
    
    @FXML
    private void dragComposant(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(source.getImage());
        content.putString(source.getId());
        db.setContent(content);

        event.consume();
    }

    @FXML
    private void overComposant(DragEvent event) {
        if (event.getDragboard().hasImage() && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }

    @FXML
    private void dropComposant(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasImage() && db.hasString()) {
            ImageView target = (ImageView) event.getGestureTarget();
            target.setImage(db.getImage());
            target.setId(db.getString());

            String id = db.getString();
            int row = grid.getRowIndex(target);
            int column = grid.getColumnIndex(target);
            removeComposant(row, column);
            target.setRotate(0);
            addComposant(id, row, column);

            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void dragComposantFromGrid(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(source.getImage());
        content.putString(source.getId());
        db.setContent(content);
        
        int row = grid.getRowIndex(source);
        int column = grid.getColumnIndex(source);
        removeComposant(row, column);

        source.setImage(null);
        source.setId(null);

        event.consume();
    }

    private void removeComposant(int row, int column) {
        circuit[row][column] = null;
    }

    @FXML
    private void keyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.P)) {
            System.out.println("P pressed");
            printCircuitArray();
        }
        else if (event.getCode().equals(KeyCode.R)) {
            System.out.println("R pressed");
            cg.preparerAnalyse(circuit);
            c.analyserCircuit();            
        } 
    }

    @FXML
    private void mouseClickCase(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        if (event.getButton().equals(MouseButton.PRIMARY) && source.getImage() != null) {
            source.setRotate(source.getRotate() + 90);
            
            
            int row = grid.getRowIndex(source);
            int column = grid.getColumnIndex(source);
            
            
            ((Connectable) circuit[row][column]).rotater();
            
            
            
        } else if (event.getButton().equals(MouseButton.SECONDARY) && source.getImage() != null && !source.getId().matches("fil.+")) {
            String id = source.getId();

            int row = grid.getRowIndex(source);
            int column = grid.getColumnIndex(source);

            Label lblComposant = new Label();
            Label lblUnite = new Label();
            TextField txtValeur = new TextField();
            txtValeur.setPrefWidth(50);
            Button btn = new Button("Ok");
            btn.setId(row + "," + column);

            if (id.equals("source")) {
                lblComposant.setText("Source");
                lblUnite.setText("Volt");
                SourceFEMGraphique sourceFEM = (SourceFEMGraphique) circuit[row][column];
                txtValeur.setText(sourceFEM.getForceElectroMotrice() + "");
            } else if (id.equals("resistance")) {
                lblComposant.setText("Resistance");
                lblUnite.setText("Ohm");
                ResistanceGraphique resistance = (ResistanceGraphique) circuit[row][column];
                txtValeur.setText(resistance.getResistance() + "");
            } else {
                System.out.println("ERROR:Composant not implemented");
            }

            HBox box = new HBox();
            box.setPadding(new Insets(15, 15, 15, 15));
            box.setSpacing(10);
            box.getChildren().addAll(lblComposant, txtValeur, lblUnite, btn);

            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button source = (Button) event.getSource();
                    String id = source.getId();

                    int row = Integer.parseInt(id.split(",")[0]);
                    int column = Integer.parseInt(id.split(",")[1]);

                    String composant = circuit[row][column].toString();

                    if (composant.equals("Resistance")) {
                        ResistanceGraphique resistance = (ResistanceGraphique) circuit[row][column];
                        resistance.setResistance(Double.parseDouble(txtValeur.getText()));
                    } else if (composant.equals("SourceFEM")) {
                        SourceFEMGraphique sourceFEM = (SourceFEMGraphique) circuit[row][column];
                        sourceFEM.setForceElectroMotrice(Double.parseDouble(txtValeur.getText()));
                    }
                    
                    composantEditor.hide();

                }
            });

            composantEditor.setDetachable(false);
            composantEditor.setContentNode(box);
            //composantEditor.setArrowLocation(ArrowLocation.BOTTOM_LEFT);
            //composantEditor.setCornerRadius(4);
            //composantEditor.setDetachedTitle("Composant");
            composantEditor.show((ImageView) event.getSource(), 15);

        }
    }

    private void addComposant(String id, int row, int column) {
        if (id.equals("source")) {
            SourceFEMGraphique source = new SourceFEMGraphique();
            circuit[row][column] = source;
        } else if (id.equals("resistance")) {
            ResistanceGraphique resistance = new ResistanceGraphique();
            circuit[row][column] = resistance;
        } else if (id.equals("filDroit")) {
            FilDroit filDroit = new FilDroit();
            circuit[row][column] = filDroit;
        } else if (id.equals("filCoin")) {
            FilCoin filCoin = new FilCoin();
            circuit[row][column] = filCoin;
        } else if (id.equals("filT")) {
            FilT filT = new FilT();
            circuit[row][column] = filT;
        } else if (id.equals("filCroix")) {
            FilCroix filCroix = new FilCroix();
            circuit[row][column] = filCroix;
        } else {
            System.out.println("ERROR:Composant not implemented");
        }
    }

    private void printCircuitArray() {
        for (Connectable[] tab : circuit) {
            for (Connectable c : tab) {
                System.out.print(String.format("%12s", c));
            }
            System.out.println("");
        }
    }
    
    private void printCircuitArray(Connectable[][] test) {
        for (Connectable[] tab : test) {
            for (Connectable c : tab) {
                System.out.print(String.format("%12s", c));
            }
            System.out.println("");
        }
    }    

    public Connectable[][] getCircuit() {
        return circuit;
    }

}
