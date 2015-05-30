package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.exceptions.AnalyseCircuitException;
import ca.qc.bdeb.sim.projetmanhattan.exceptions.CircuitPasGroundException;
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
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LumiereOutput;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NANDGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NOTGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.SourceDigitale;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.XNORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.XORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.ImageChangeable;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.TypeComposant;
import java.io.File;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private BorderPane pane;

    @FXML
    private GridPane grid;

    @FXML
    private TitledPane analogue;

    @FXML
    private TitledPane numerique;
    
    @FXML
    private ListView<String> list;
    
    ObservableList<String> items = FXCollections.observableArrayList("Bienvenue!");

    private Connectable[][] connectables2D = new Connectable[10][10];

    private final PopOver composantEditor = new PopOver();

    private CircuitAnalogue circuitAnalogue;
    private CircuitDigital circuitNumerique;
    private AnalyseC circuitGraphique;
    
    private ImageView lastSource = null;

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

            resetLastSource();
            
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }
    
    private void resetLastSource() {
        if (lastSource != null) {
            lastSource.setStyle("");
        }
        lastSource = null;
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

        if (numerique.isDisabled() == false) {
            circuitNumerique.stopAnalyse();
        }

    }

    public void updateCircuitNumerique() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Connectable c = connectables2D[i][j];
                if (c instanceof ImageChangeable) {
                    ImageView imgV = (ImageView) getNodeByRowColumnIndex(grid, i, j);

                    ImageChangeable imgC = (ImageChangeable) c;
                    if (imgC.isActif()) {
                        imgV.setImage(imgC.getImage(true));
                    } else {
                        imgV.setImage(imgC.getImage(false));
                    }
                }
            }
        }

    }

    private void changeImage() {
        ImageView imgView = lastSource;

        int row = grid.getRowIndex(lastSource);
        int column = grid.getColumnIndex(lastSource);
        
        if (connectables2D[row][column] instanceof ImageChangeable) {
            ImageChangeable compAllumable = (ImageChangeable) connectables2D[row][column];

            compAllumable.nextImage();
            imgView.setImage(compAllumable.getImage(compAllumable.isActif()));
        }
    }

    @FXML
    private void mouseClickCase(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();

        if (event.getButton().equals(MouseButton.PRIMARY) && source.getImage() != null && source.getStyle().equals("")) {
            source.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
            if (lastSource == null) {
                lastSource = source;
            } else if (lastSource != source) {
                lastSource.setStyle("");
                lastSource = source;
            }
        } else if (event.getButton().equals(MouseButton.PRIMARY) && source.getImage() != null && !source.getStyle().equals("")) {
            source.setStyle("");
            lastSource = null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createMenu();
        numerique.setDisable(true);
        
        list.setItems(items);
    }

    public void setCircuitAnalogue(CircuitAnalogue c) {
        this.circuitAnalogue = c;
    }

    public void setCircuitNumerique(CircuitDigital c) {
        this.circuitNumerique = c;
        circuitNumerique.setController(this);
    }

    public void setCircuitGraphique(AnalyseC cg) {
        this.circuitGraphique = cg;
    }

    private void createMenu() {
        MenuBar mnuBar = new MenuBar();
        Menu mnuFile = new Menu("Fichier");
        Menu mnuMode = new Menu("Mode");
        Menu mnuAction = new Menu("Action");
        Menu mnuAide = new Menu("Aide");

        MenuItem mnuItemSave = new MenuItem("Sauvegarder");
        MenuItem mnuItemLoad = new MenuItem("Ouvrir");
        
        MenuItem mnuItemAnalogue = new MenuItem("Changer pour Analogue");
        MenuItem mnuItemNumerique = new MenuItem("Changer pour Numérique");
        
        MenuItem mnuItemRun = new MenuItem("Exécuter");
        MenuItem mnuItemStop = new MenuItem("Arrêt (Numérique)");
        MenuItem mnuItemWipe = new MenuItem("Effacer Tout");
        MenuItem mnuItemRotate = new MenuItem("Tourner");
        MenuItem mnuItemChangeImage = new MenuItem("Changer l'image (Numérique)");
        MenuItem mnuItemValue = new MenuItem("Modifier la valeur");
        MenuItem mnuItemEffaceConsole = new MenuItem("Effacer la console");
        
        MenuItem mnuItemAide = new MenuItem("Aide");
        MenuItem mnuItemAbout = new MenuItem("À propos");

        mnuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fileChooser();
            }
        });

        mnuItemLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                fileOpener();
            }
        });

        mnuItemRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (analogue.isDisabled() == false) {
                    //Analogue
                    circuitGraphique.preparerAnalyse(circuitAnalogue, connectables2D);
                    try {
                        circuitAnalogue.analyserCircuit();
                    } catch (CircuitPasGroundException e) {
                        items.add(e.getMessage());
                    } catch (AnalyseCircuitException e) {
                        items.add(e.getMessage());
                    }
                    
                    int resistanceCount = 0;
                    int sourceFEMCount = 0;
                    
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            ImageView imgV = (ImageView) getNodeByRowColumnIndex(grid, i, j);
                            if (connectables2D[i][j] instanceof Resistance) {
                                Resistance r = (Resistance) connectables2D[i][j];
                                String info = String.format("Résistance %d\nRésistance: %.2f\nCourant: %.2f", resistanceCount+1, r.getResistance(), r.getCourant());
                                Tooltip tooltip = new Tooltip(info);
                                hackTooltipStartTiming(tooltip);
                                Tooltip.install(imgV, tooltip);
                                items.add(info);
                                resistanceCount = resistanceCount + 1;
                            } else if (connectables2D[i][j] instanceof SourceFEM) {
                                SourceFEM s = (SourceFEM) connectables2D[i][j];
                                String info = String.format("Source FEM %d\nTension: %.2f\nCourant: %.2f", sourceFEMCount+1, s.getForceElectroMotrice(), s.getCourant());
                                Tooltip tooltip = new Tooltip(info);
                                hackTooltipStartTiming(tooltip);
                                Tooltip.install(imgV, tooltip);
                                items.add(info);
                                sourceFEMCount = sourceFEMCount + 1;
                            }
                        }
                    }

                } else if (numerique.isDisabled() == false) {
                    //Numérique
                    circuitNumerique.stopAnalyse();
                    circuitGraphique.preparerAnalyse(circuitNumerique, connectables2D);
                    circuitNumerique.analyserCircuit();
                }

            }
        });
        
        mnuItemStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (numerique.isDisabled() == false) {
                    circuitNumerique.stopAnalyse();
                }                
            }
        });        

        mnuItemWipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                wipe();
            }
        });
        
        mnuItemRotate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (lastSource != null) {
                    ImageView source = lastSource;

                    double rotation = source.getRotate() + 90;

                    source.setRotate(rotation);

                    int row = grid.getRowIndex(source);
                    int column = grid.getColumnIndex(source);

                    Connectable c = (Connectable) connectables2D[row][column];

                    c.rotater();
                    c.setRotation(rotation);                       
                }
             
            }
        });
        
        mnuItemChangeImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                changeImage();
            }
        });
        
        mnuItemValue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                ImageView source = lastSource;
                if (lastSource != null && source.getImage() != null && !source.getId().matches("fil.+|.+Gate|light|sourceDigitale")) {
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
                        items.add("Erreur : Composant pas implémenté");
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

                            TypeComposant typeComposant = connectables2D[row][column].getTypeComposant();

                            try {
                                if (typeComposant == TypeComposant.RESISTANCE) {
                                    ((Resistance) connectables2D[row][column]).setResistance(Double.parseDouble(txtValeur.getText()));
                                } else if (typeComposant == TypeComposant.SOURCE_TENSION) {
                                    ((SourceFEM) connectables2D[row][column]).setForceElectroMotrice(Double.parseDouble(txtValeur.getText()));
                                } else if (typeComposant == TypeComposant.SOURCE_COURANT) {
                                    ((SourceCourant) connectables2D[row][column]).setCourant(Double.parseDouble(txtValeur.getText()));
                                }
                            } catch (NumberFormatException e) {
                                items.add("Erreur : Pas un nombre");
                            }
                            composantEditor.hide();

                        }
                    });

                    composantEditor.setDetachable(false);
                    composantEditor.setContentNode(box);
                    composantEditor.show(source, 15);

                }    
            }
        });        
        
        mnuItemEffaceConsole.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                items.clear();
            }

        });        
        

        mnuItemAnalogue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                wipe();
                numerique.setDisable(true);
                analogue.setDisable(false);
            }

        });

        mnuItemNumerique.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                wipe();
                analogue.setDisable(true);
                numerique.setDisable(false);
            }

        });

        mnuItemAide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Stage dialog = new Stage();
                dialog.initStyle(StageStyle.UTILITY);
                dialog.setHeight(500);
                dialog.setWidth(500);
                Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
                dialog.setScene(scene);
                dialog.show();
            }
        });

        mnuItemAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Stage dialog = new Stage();
                dialog.initStyle(StageStyle.UTILITY);
                dialog.setHeight(200);
                dialog.setWidth(500);

                Group group = new Group();
                Scene scene = new Scene(group);

                Text title = new Text(25, 25, "Projet Manhattan");
                Text credits = new Text(25, 50, "Marc-Antoine Lalonde\nPatrick Richer St-Onge");
                group.getChildren().addAll(title, credits);

                dialog.setScene(scene);
                dialog.show();
            }
        });

        mnuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        mnuItemLoad.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        
        mnuItemAnalogue.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        mnuItemNumerique.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        
        mnuItemRun.setAccelerator(new KeyCodeCombination(KeyCode.R));
        mnuItemStop.setAccelerator(new KeyCodeCombination(KeyCode.A));
        mnuItemWipe.setAccelerator(new KeyCodeCombination(KeyCode.W));
        mnuItemRotate.setAccelerator(new KeyCodeCombination(KeyCode.T));
        mnuItemValue.setAccelerator(new KeyCodeCombination(KeyCode.E));
        mnuItemChangeImage.setAccelerator(new KeyCodeCombination(KeyCode.M));
        mnuItemEffaceConsole.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        
        
        mnuFile.getItems().addAll(mnuItemSave, mnuItemLoad);
        mnuMode.getItems().addAll(mnuItemAnalogue, mnuItemNumerique);
        mnuAction.getItems().addAll(mnuItemRun, mnuItemStop, mnuItemWipe, mnuItemRotate, mnuItemChangeImage, mnuItemValue, mnuItemEffaceConsole);
        mnuAide.getItems().addAll(mnuItemAide, mnuItemAbout);
        mnuBar.getMenus().addAll(mnuFile, mnuMode, mnuAction, mnuAide);

        pane.setTop(mnuBar);
    }

    private void wipe() {
        if (numerique.isDisabled() == false) {
            circuitNumerique.stopAnalyse();
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ImageView imgV = (ImageView) getNodeByRowColumnIndex(grid, i, j);
                imgV.setImage(null);
                imgV.setId(null);
                removeComposant(i, j);
            }
        }
        
        resetLastSource();
    }

    private void fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvargarder circuit");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serializable Object", "*.ser"));
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());
        if (file != null) {
            writeFile(file);
        }
        items.add(String.format("Circuit sauvegardé : '%s'",file.getAbsolutePath()));
    }

    private void fileOpener() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir circuit");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serializable Object", "*.ser"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            readFile(file);
        }
        items.add(String.format("Circuit ouvert : '%s'",file.getAbsolutePath()));
    }

    private void writeFile(File file) {
        Sauvegarde save = new Sauvegarde(10);
        save.setCircuit(connectables2D);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(save);
            oos.close();
        } catch (IOException ex) {

        }
    }

    private void readFile(File file) {
        Sauvegarde save = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            save = (Sauvegarde) ois.readObject();
            ois.close();
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {

        }

        connectables2D = save.getCircuit();

        String pathImg = "file:src/ca/qc/bdeb/sim/projetmanhattan/view/img/";

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                Connectable c = connectables2D[row][column];
                if (c instanceof Resistance) {
                    ImageView tmp = new ImageView();
                    Resistance r = (Resistance) c;
                    tmp.setImage(new Image(r.getImgPath()));
                    tmp.setId("resistance");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof SourceFEM) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "source_tension.png"));
                    tmp.setId("sourceTension");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof SourceCourant) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "source_courant.png"));
                    tmp.setId("sourceCourant");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof Ground) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "ground.png"));
                    tmp.setId("ground");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof FilDroit) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "fil_droit.png"));
                    tmp.setId("filDroit");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof FilCoin) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "fil_coin.png"));
                    tmp.setId("filCoin");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof FilT) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "fil_t.png"));
                    tmp.setId("filT");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof FilCroix) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "fil_croix.png"));
                    tmp.setId("filCroix");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof ANDGate) {
                    //ANDGate compAllumable = (ANDGate) c; 
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "and1.png"));
                    tmp.setId("andGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof ORGate) {
                    //ORGate compAllumable = (ORGate) c; 
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "or1.png"));
                    tmp.setId("orGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof NOTGate) {
                    //NOTGate compAllumable = (NOTGate) c; 
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "not1.png"));
                    tmp.setId("notGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                }
            }
        }

    }

    private void initializeImageView(ImageView imgv, Connectable c) {
        imgv.setFitWidth(50);
        imgv.setFitHeight(50);
        imgv.setOnDragDetected(this::dragComposantFromGrid);
        imgv.setOnDragDropped(this::dropComposant);
        imgv.setOnDragOver(this::overComposant);
        imgv.setOnMouseClicked(this::mouseClickCase);
        imgv.setPickOnBounds(true);
        imgv.setPreserveRatio(true);
        imgv.setRotate(c.getRotation());
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
            items.add("Erreur : Composant pas implémenté");
        }
    }

    private static void hackTooltipStartTiming(Tooltip tooltip) {
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
        }
    }

}
