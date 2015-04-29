package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.model.analog.CircuitAnalogue;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.CircuitDigital;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Ground;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilCoin;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilCroix;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilDroit;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilT;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.Resistance;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceCourant;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.SourceFEM;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ANDGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LogicGateAbstraite;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LumiereOutput;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NANDGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NOTGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.SourceDigitale;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.XNORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.XORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilAbstrait;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


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
    
    @FXML
    ImageView andGate;
    
    @FXML
    TitledPane analogue;
    
    @FXML
    TitledPane numerique;
    
    @FXML
    TitledPane mixte;    
    
    int mouseRow;
    int mouseColumn;
    
    
    Connectable[][] connectables2D = new Connectable[10][10];

    PopOver composantEditor = new PopOver();

    CircuitAnalogue circuitAnalogue;
    CircuitDigital circuitNumerique;
    AnalyseC circuitGraphique;

    
    

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
        } else if (event.getCode().equals(KeyCode.M)) {
            System.out.println("M pressed");
            
            ImageView imgView = (ImageView) getNodeByRowColumnIndex(grid, mouseRow, mouseColumn);
            
            if (connectables2D[mouseRow][mouseColumn] instanceof LogicGateAbstraite) {
                LogicGateAbstraite gate = (LogicGateAbstraite) connectables2D[mouseRow][mouseColumn];

                gate.nextImage();
                imgView.setImage(gate.getImage());                
            }

            
           
        }    
    }

    @FXML
    private void mouseClickCase(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        if (event.getButton().equals(MouseButton.PRIMARY) && source.getImage() != null) {
            source.setRotate(source.getRotate() + 90);

            int row = grid.getRowIndex(source);
            int column = grid.getColumnIndex(source);

            ((Connectable) connectables2D[row][column]).rotater();

        } else if (event.getButton().equals(MouseButton.SECONDARY) && source.getImage() != null && !source.getId().matches("fil.+|.+Gate|light|sourceDigitale")) {
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
                SourceFEM sourceTension = (SourceFEM) connectables2D[row][column];
                txtValeur.setText(sourceTension.getForceElectroMotrice() + "");
            } else if (id.equals("sourceCourant")) { 
                lblComposant.setText("Source de courant");
                lblUnite.setText("Ampère");
                SourceCourant sourceCourant = (SourceCourant) connectables2D[row][column];
                txtValeur.setText(sourceCourant.getCourant() + "");
            } else if (id.equals("resistance")) {
                lblComposant.setText("Resistance");
                lblUnite.setText("Ohm");
                Resistance resistance = (Resistance) connectables2D[row][column];
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

                    TypeComposant typeComposant  = connectables2D[row][column].getTypeComposant();

                    try {
                        if (typeComposant == TypeComposant.RESISTANCE) {
                            ((Resistance) connectables2D[row][column]).setResistance(Double.parseDouble(txtValeur.getText()));
                        } else if (typeComposant == TypeComposant.SOURCE_TENSION) {
                            ((SourceFEM) connectables2D[row][column]).setForceElectroMotrice(Double.parseDouble(txtValeur.getText()));
                        } else if (typeComposant == TypeComposant.SOURCE_COURANT) {
                            ((SourceCourant) connectables2D[row][column]).setCourant(Double.parseDouble(txtValeur.getText()));
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

        } else if (event.getButton().equals(MouseButton.SECONDARY)) {
            
            source.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
            
        }
    }
        
    
    @FXML
    private void mouseMoved(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        mouseRow = (int) y/50; 
        mouseColumn = (int) x/50;
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createMenu();
        numerique.setDisable(true);
    }
    
    public void setCircuitAnalogue(CircuitAnalogue c) {
        this.circuitAnalogue = c;
    }
    
    public void setCircuitNumerique(CircuitDigital c) {
        this.circuitNumerique = c;
    }    

    public void setCircuitGraphique(AnalyseC cg) {
        this.circuitGraphique = cg;
    }    
    
    private void createMenu() {
        MenuBar mnuBar = new MenuBar();
        Menu mnuFile = new Menu("File");
        Menu mnuMode = new Menu("Mode");
        Menu mnuRun = new Menu("Run");
        Menu mnuAction = new Menu("Action");

        MenuItem mnuItemSave = new MenuItem("Save");
        MenuItem mnuItemLoad = new MenuItem("Load");
        MenuItem mnuItemAnalogue = new MenuItem("Switch to Analogue");
        MenuItem mnuItemNumerique = new MenuItem("Switch to Numérique");
        MenuItem mnuItemRun = new MenuItem("Run", new ImageView(new Image("file:src/ca/qc/bdeb/sim/projetmanhattan/view/mixte/play.png")));
        MenuItem mnuItemWipe = new MenuItem("Wipe");
        
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
                if (analogue.isDisabled() == false) {
                    circuitGraphique.preparerAnalyse(circuitAnalogue, connectables2D);
                    circuitAnalogue.analyserCircuit();
                    
                    for (int i=0; i<10; i++) {
                        for (int j=0; j<10; j++) {
                            ImageView imgV = (ImageView) getNodeByRowColumnIndex(grid, i, j);
                            if (connectables2D[i][j] instanceof Resistance) {
                                Resistance r = (Resistance) connectables2D[i][j];
                                String info = String.format("Résistance: %.2f\nCourant: %.2f",r.getResistance(), r.getCourant());
                                Tooltip tooltip = new Tooltip(info);
                                hackTooltipStartTiming(tooltip);
                                Tooltip.install(imgV, tooltip);
                                //System.out.println("RESISTANCE");
                                //System.out.println("Resistance:"+r.getResistance());
                                //System.out.println("Courant:"+Math.abs(r.getCourant()));
                            } else if (connectables2D[i][j] instanceof SourceFEM) {
                                SourceFEM s = (SourceFEM) connectables2D[i][j];
                                String info = String.format("Tension: %.2f\nCourant: %.2f",s.getForceElectroMotrice(), s.getCourant());
                                Tooltip tooltip = new Tooltip(info);
                                hackTooltipStartTiming(tooltip);
                                Tooltip.install(imgV, tooltip);                                
                                //System.out.println("SOURCE");
                                //System.out.println("FEM:"+s.getForceElectroMotrice());
                                //System.out.println("Courant:"+Math.abs(s.getCourant()));
                            } else if (connectables2D[i][j] instanceof FilAbstrait) {
                                FilAbstrait f = (FilAbstrait) connectables2D[i][j];
                                //System.out.println("FIL");
                                //System.out.println("FEM:"+f.getTension());
                            }
                        }
                    }  
                    
                } else if (numerique.isDisabled() == false) {
                    circuitGraphique.preparerAnalyse(circuitNumerique, connectables2D);
                    circuitNumerique.analyserCircuit();                      
                }
                
                
            }
        });

        mnuItemWipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                for (int i=0; i<10; i++) {
                    for (int j=0; j<10; j++) {
                        ImageView imgV = (ImageView) getNodeByRowColumnIndex(grid, i, j);
                        imgV.setImage(null);
                        imgV.setId(null);
                        removeComposant(i, j);
                    }
                }
            }
        }); 

        mnuItemAnalogue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                numerique.setDisable(true);
                analogue.setDisable(false);
            }

        });

        mnuItemNumerique.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                analogue.setDisable(true);
                numerique.setDisable(false);
            }

        });

        mnuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        mnuItemLoad.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        mnuItemRun.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        mnuItemWipe.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        mnuItemAnalogue.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        mnuItemNumerique.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        mnuFile.getItems().addAll(mnuItemSave,mnuItemLoad);
        mnuMode.getItems().addAll(mnuItemAnalogue,mnuItemNumerique);
        mnuAction.getItems().addAll(mnuItemRun, mnuItemWipe);        
        mnuBar.getMenus().addAll(mnuFile,mnuMode,mnuAction);

        pane.setTop(mnuBar);        
    }
    
    private void writeFile() {
               Sauvegarde save = new Sauvegarde();
               save.setCircuit(connectables2D);
               
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
                
                connectables2D = save.getCircuit();

                String pathAnalog = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/analog/";
                String pathMixte = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/mixte/";
                String pathImg = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/";
                
                for (int row=0; row<10; row++) {
                    for (int column=0; column<10; column++) {
                        if (connectables2D[row][column] instanceof Resistance) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathAnalog+"resistance.png"));
                            tmp.setId("resistance");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);
                        }
                        else if (connectables2D[row][column] instanceof SourceFEM) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathAnalog+"source_tension.png"));
                            tmp.setId("sourceTension");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                            
                        }
                        else if (connectables2D[row][column] instanceof SourceCourant) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathAnalog+"source_courant.png"));
                            tmp.setId("sourceCourant");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                            
                        }     
                        else if (connectables2D[row][column] instanceof Ground) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathAnalog+"ground.png"));
                            tmp.setId("ground");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                            
                        }                         
                        else if (connectables2D[row][column] instanceof FilDroit) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathMixte+"fil_droit.png"));
                            tmp.setId("filDroit");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                            
                        } 
                        else if (connectables2D[row][column] instanceof FilCoin) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathMixte+"fil_coin.png"));
                            tmp.setId("filCoin");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                            
                        }
                        else if (connectables2D[row][column] instanceof FilT) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathMixte+"fil_t.png"));
                            tmp.setId("filT");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                            
                        }
                        else if (connectables2D[row][column] instanceof FilCroix) {
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathMixte+"fil_croix.png"));
                            tmp.setId("filCroix");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                            
                        }   
                        else if (connectables2D[row][column] instanceof ANDGate) {
                            //ANDGate gate = (ANDGate) connectables2D[row][column]; 
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathImg+"and1.png"));
                            tmp.setId("andGate");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                            
                        }  
                        else if (connectables2D[row][column] instanceof ORGate) {
                            //ORGate gate = (ORGate) connectables2D[row][column]; 
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathImg+"or1.png"));
                            tmp.setId("orGate");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                               
                        }  
                        else if (connectables2D[row][column] instanceof NOTGate) {
                            //NOTGate gate = (NOTGate) connectables2D[row][column]; 
                            ImageView tmp = new ImageView();
                            tmp.setImage(new Image(pathImg+"not1.png"));
                            tmp.setId("notGate");
                            initializeImageView(tmp);
                            grid.add(tmp, column, row);                              
                        }                          
                    }
                }
               
                System.out.println("Done reading");        
    }
    
    private void initializeImageView(ImageView imgv) {
        imgv.setFitWidth(50);
        imgv.setFitHeight(50);
        imgv.setOnDragDetected(this::dragComposantFromGrid);
        imgv.setOnDragDropped(this::dropComposant);
        imgv.setOnDragOver(this::overComposant);
        imgv.setOnMouseClicked(this::mouseClickCase);        
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
        connectables2D[row][column] = null;
    }    
    
    private void addComposant(String id, int row, int column) {
        if (id.equals("sourceTension")) {
            SourceFEM sourceTension = new SourceFEM();
            connectables2D[row][column] = sourceTension;
        } else if (id.equals("sourceCourant")) {
            SourceCourant sourceCourant = new SourceCourant();
            connectables2D[row][column] = sourceCourant;
        } else if (id.equals("resistance")) {
            Resistance resistance = new Resistance();
            connectables2D[row][column] = resistance;
        } else if (id.equals("ground")) {
            Ground ground = new Ground();
            connectables2D[row][column] = ground;
        } else if (id.equals("filDroit")) {
            FilDroit filDroit = new FilDroit();
            connectables2D[row][column] = filDroit;
        } else if (id.equals("filCoin")) {
            FilCoin filCoin = new FilCoin();
            connectables2D[row][column] = filCoin;
        } else if (id.equals("filT")) {
            FilT filT = new FilT();
            connectables2D[row][column] = filT;
        } else if (id.equals("filCroix")) {
            FilCroix filCroix = new FilCroix();
            connectables2D[row][column] = filCroix;
        } else if (id.equals("andGate")) {
            ANDGate andGate = new ANDGate();
            connectables2D[row][column] = andGate;
        } else if (id.equals("orGate")) {
            ORGate orGate = new ORGate();
            connectables2D[row][column] = orGate;
        } else if (id.equals("notGate")) {
            NOTGate notGate = new NOTGate();
            connectables2D[row][column] = notGate;
        } else if (id.equals("nandGate")) {
            NANDGate nandGate = new NANDGate();
            connectables2D[row][column] = nandGate;
        } else if (id.equals("norGate")) {
            NORGate norGate = new NORGate();
            connectables2D[row][column] = norGate;
        } else if (id.equals("xorGate")) {
            XORGate xorGate = new XORGate();
            connectables2D[row][column] = xorGate;
        } else if (id.equals("xnorGate")) {
            XNORGate xnorGate = new XNORGate();
            connectables2D[row][column] = xnorGate;
        } else if (id.equals("light")) {
            LumiereOutput light = new LumiereOutput();
            connectables2D[row][column] = light;
        } else if (id.equals("sourceDigitale")) {
            SourceDigitale sourceDigitale = new SourceDigitale();
            connectables2D[row][column] = sourceDigitale;
        } else {
            System.out.println("ERROR:Composant not implemented");
        }
    }

    private void printCircuitArray() {
        for (Connectable[] tab : connectables2D) {
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
        return connectables2D;
    }     
    
    public static void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    



}
