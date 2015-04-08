package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.CircuitGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilCoin;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilCroix;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilDroit;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilT;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.ResistanceGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceCourantGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceFEMGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
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

    
    @FXML
    BorderPane pane;

    @FXML
    GridPane grid;
    
    
    
    
    Connectable[][] circuit2D = new Connectable[10][10];

    PopOver composantEditor = new PopOver();

    Circuit circuit;
    CircuitGraphique circuitGraphique;
    
    
    
    

    @FXML
    private void analyserCircuit(ActionEvent event) {
        circuitGraphique.preparerAnalyse(circuit2D);
        circuit.analyserCircuit();
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



    @FXML
    private void keyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.P)) {
            System.out.println("P pressed");
            printCircuitArray();
        } else if (event.getCode().equals(KeyCode.R)) {
            System.out.println("R pressed");
            circuitGraphique.preparerAnalyse(circuit2D);
            circuit.analyserCircuit();
        }
    }

    @FXML
    private void mouseClickCase(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        if (event.getButton().equals(MouseButton.PRIMARY) && source.getImage() != null) {
            source.setRotate(source.getRotate() + 90);

            int row = grid.getRowIndex(source);
            int column = grid.getColumnIndex(source);

            ((Connectable) circuit2D[row][column]).rotater();

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

            if (id.equals("sourceTension")) {
                lblComposant.setText("Source de tension");
                lblUnite.setText("Volt");
                SourceFEMGraphique sourceTension = (SourceFEMGraphique) circuit2D[row][column];
                txtValeur.setText(sourceTension.getForceElectroMotrice() + "");
            } else if (id.equals("sourceCourant")) { 
                lblComposant.setText("Source de courant");
                lblUnite.setText("Ampère");
                SourceCourantGraphique sourceCourant = (SourceCourantGraphique) circuit2D[row][column];
                txtValeur.setText(sourceCourant.getCourant() + "");
            } else if (id.equals("resistance")) {
                lblComposant.setText("Resistance");
                lblUnite.setText("Ohm");
                ResistanceGraphique resistance = (ResistanceGraphique) circuit2D[row][column];
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

                    TypeComposant typeComposant  = circuit2D[row][column].getTypeComposant();

                    try {
                        if (typeComposant == TypeComposant.RESISTANCE) {
                            ((ResistanceGraphique) circuit2D[row][column]).setResistance(Double.parseDouble(txtValeur.getText()));
                        } else if (typeComposant == TypeComposant.SOURCE_TENSION) {
                            ((SourceFEMGraphique) circuit2D[row][column]).setForceElectroMotrice(Double.parseDouble(txtValeur.getText()));
                        } else if (typeComposant == TypeComposant.SOURCE_COURANT) {
                            ((SourceCourantGraphique) circuit2D[row][column]).setCourant(Double.parseDouble(txtValeur.getText()));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("INPUT ERROR: Pas un nombre");
                    }
                    composantEditor.hide();

                }
            });

            composantEditor.setDetachable(false);
            composantEditor.setContentNode(box);
            composantEditor.show((ImageView) event.getSource(), 15);

        }
    }
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createMenu();
    }
    
    public void setCircuit(Circuit c) {
        this.circuit = c;
    }

    public void setCircuitGraphique(CircuitGraphique cg) {
        this.circuitGraphique = cg;
    }    
    
    private void createMenu() {
        MenuBar mnuBar = new MenuBar();
        Menu mnuFile = new Menu("File");
        Menu mnuRun = new Menu("Run");
        
        MenuItem mnuItemSave = new MenuItem("Save");
        MenuItem mnuItemLoad = new MenuItem("Load");
        MenuItem mnuItemRun = new MenuItem("Run");
        
        mnuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                writeFile();
            }
        });        
        
        mnuItemLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                readFile();
            }
        });        

        mnuItemRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                circuitGraphique.preparerAnalyse(circuit2D);
                circuit.analyserCircuit();
            }

        });        
 
        mnuFile.getItems().addAll(mnuItemSave,mnuItemLoad);
        mnuRun.getItems().addAll(mnuItemRun);        
        mnuBar.getMenus().addAll(mnuFile,mnuRun);

        pane.setTop(mnuBar);        
    }
    
    private void writeFile() {
               Sauvegarde save = new Sauvegarde();
               save.setCircuit(circuit2D);
               
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
    
    private void readFile() {
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
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {                
                    ex.printStackTrace();
                }                
                
                circuit2D = save.getCircuit();

                for (int row=0; row<10; row++) {
                    for (int column=0; column<10; column++) {
                        if (circuit2D[row][column] instanceof ResistanceGraphique) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/resistance.png"));
                            tmp.setId("resistance");
                            grid.add(tmp, column, row);
                        }
                        else if (circuit2D[row][column] instanceof SourceFEMGraphique) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/source_tension.png"));
                            tmp.setId("sourceTension");
                            grid.add(tmp, column, row);
                        }
                        else if (circuit2D[row][column] instanceof SourceCourantGraphique) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/source_courant.png"));
                            tmp.setId("sourceCourant");
                            grid.add(tmp, column, row);
                        }      
                        else if (circuit2D[row][column] instanceof FilDroit) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/fil_droit.png"));
                            tmp.setId("filDroit");
                            grid.add(tmp, column, row);                       
                        } 
                        else if (circuit2D[row][column] instanceof FilCoin) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/fil_coin.png"));
                            tmp.setId("filCoin");
                            grid.add(tmp, column, row);
                        }
                        else if (circuit2D[row][column] instanceof FilT) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/fil_t.png"));
                            tmp.setId("filT");
                            grid.add(tmp, column, row);
                        }
                        else if (circuit2D[row][column] instanceof FilCroix) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image("file:img/fil_croix.png"));
                            tmp.setId("filCroix");
                            grid.add(tmp, column, row);
                        }                         
                    }
                }
               
                System.out.println("Done reading");        
    }
    

    
    private Node getNodeByRowColumnIndex(GridPane grid, int row, int column) {
        Node result = null;
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }   
    
    private void removeComposant(int row, int column) {
        circuit2D[row][column] = null;
    }    
    
    private void addComposant(String id, int row, int column) {
        if (id.equals("sourceTension")) {
            SourceFEMGraphique sourceTension = new SourceFEMGraphique();
            circuit2D[row][column] = sourceTension;
        } else if (id.equals("sourceCourant")) {
            SourceCourantGraphique sourceCourant = new SourceCourantGraphique();
            circuit2D[row][column] = sourceCourant;
        } else if (id.equals("resistance")) {
            ResistanceGraphique resistance = new ResistanceGraphique();
            circuit2D[row][column] = resistance;
        } else if (id.equals("filDroit")) {
            FilDroit filDroit = new FilDroit();
            circuit2D[row][column] = filDroit;
        } else if (id.equals("filCoin")) {
            FilCoin filCoin = new FilCoin();
            circuit2D[row][column] = filCoin;
        } else if (id.equals("filT")) {
            FilT filT = new FilT();
            circuit2D[row][column] = filT;
        } else if (id.equals("filCroix")) {
            FilCroix filCroix = new FilCroix();
            circuit2D[row][column] = filCroix;
        } else {
            System.out.println("ERROR:Composant not implemented");
        }
    }

    private void printCircuitArray() {
        for (Connectable[] tab : circuit2D) {
            for (Connectable c : tab) {
                System.out.print(String.format("%12s", c));
            }
            System.out.println("");
        }
    }
    
    private void printCircuitArray(Connectable[][] array) {
        for (Connectable[] tab : array) {
            for (Connectable c : tab) {
                System.out.print(String.format("%12s", c));
            }
            System.out.println("");
        }
    }    

    public Connectable[][] getCircuit() {
        return circuit2D;
    }        
    



}
