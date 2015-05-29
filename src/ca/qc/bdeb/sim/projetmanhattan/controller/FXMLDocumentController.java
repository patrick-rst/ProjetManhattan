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
import ca.qc.bdeb.sim.projetmanhattan.view.digital.LumiereOutput;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NANDGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.NOTGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.ORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.SourceDigitale;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.XNORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.digital.XORGate;
import ca.qc.bdeb.sim.projetmanhattan.view.mixte.FilAbstrait;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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

    private Stage stage;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView andGate;

    @FXML
    private TitledPane analogue;

    @FXML
    private TitledPane numerique;

    @FXML
    private TitledPane mixte;

    private int mouseRow;
    private int mouseColumn;

    private Connectable[][] connectables2D = new Connectable[10][10];

    private final PopOver composantEditor = new PopOver();

    private CircuitAnalogue circuitAnalogue;
    private CircuitDigital circuitNumerique;
    private AnalyseC circuitGraphique;

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

        if (numerique.isDisabled() == false) {
            circuitNumerique.stopAnalyse();
        }

    }

    @FXML
    private void keyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.P)) {
            System.out.println("P pressed");
            printCircuitArray();
        } else if (event.getCode().equals(KeyCode.M)) {
            System.out.println("M pressed");

            changeImage();

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
        ImageView imgView = (ImageView) getNodeByRowColumnIndex(grid, mouseRow, mouseColumn);

        if (connectables2D[mouseRow][mouseColumn] instanceof ImageChangeable) {
            ImageChangeable compAllumable = (ImageChangeable) connectables2D[mouseRow][mouseColumn];

            compAllumable.nextImage();
            imgView.setImage(compAllumable.getImage(compAllumable.isActif()));
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

        mouseRow = (int) y / 50;
        mouseColumn = (int) x / 50;
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
        circuitNumerique.setController(this);
    }

    public void setCircuitGraphique(AnalyseC cg) {
        this.circuitGraphique = cg;
    }

    private void createMenu() {
        MenuBar mnuBar = new MenuBar();
        Menu mnuFile = new Menu("File");
        Menu mnuMode = new Menu("Mode");
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
                //writeFile();
                fileChooser();
            }
        });

        mnuItemLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //readFile();
                fileOpener();
            }
        });

        mnuItemRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                if (analogue.isDisabled() == false) {
                    //Analogue
                    circuitGraphique.preparerAnalyse(circuitAnalogue, connectables2D);
                    circuitAnalogue.analyserCircuit();

                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            ImageView imgV = (ImageView) getNodeByRowColumnIndex(grid, i, j);
                            if (connectables2D[i][j] instanceof Resistance) {
                                Resistance r = (Resistance) connectables2D[i][j];
                                String info = String.format("Résistance: %.2f\nCourant: %.2f", r.getResistance(), r.getCourant());
                                Tooltip tooltip = new Tooltip(info);
                                hackTooltipStartTiming(tooltip);
                                Tooltip.install(imgV, tooltip);
                                //System.out.println("RESISTANCE");
                                //System.out.println("Resistance:"+r.getResistance());
                                //System.out.println("Courant:"+Math.abs(r.getCourant()));
                            } else if (connectables2D[i][j] instanceof SourceFEM) {
                                SourceFEM s = (SourceFEM) connectables2D[i][j];
                                String info = String.format("Tension: %.2f\nCourant: %.2f", s.getForceElectroMotrice(), s.getCourant());
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
                    //Numérique
                    circuitNumerique.stopAnalyse();
                    circuitGraphique.preparerAnalyse(circuitNumerique, connectables2D);
                    circuitNumerique.analyserCircuit();
                }

            }
        });

        mnuItemWipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                wipe();
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

        mnuItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        mnuItemLoad.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        mnuItemRun.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        mnuItemWipe.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
        mnuItemAnalogue.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        mnuItemNumerique.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        mnuFile.getItems().addAll(mnuItemSave, mnuItemLoad);
        mnuMode.getItems().addAll(mnuItemAnalogue, mnuItemNumerique);
        mnuAction.getItems().addAll(mnuItemRun, mnuItemWipe);
        mnuBar.getMenus().addAll(mnuFile, mnuMode, mnuAction);

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
    }

    private void fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Circuit");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serializable Object", "*.ser"));
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());
        if (file != null) {
            writeFile(file);
        }
    }

    private void fileOpener() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Circuit");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serializable Object", "*.ser"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            readFile(file);
        }
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
                    //tmp.setImage(new Image(pathImg + "resistance.png"));
                    Resistance r = (Resistance) c;
                    tmp.setImage(new Image(r.getImgPath()));
                    tmp.setId("resistance");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof SourceFEM) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "source_tension.png"));
                    tmp.setId("sourceTension");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof SourceCourant) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "source_courant.png"));
                    tmp.setId("sourceCourant");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof Ground) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "ground.png"));
                    tmp.setId("ground");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof FilDroit) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "fil_droit.png"));
                    tmp.setId("filDroit");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof FilCoin) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "fil_coin.png"));
                    tmp.setId("filCoin");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof FilT) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "fil_t.png"));
                    tmp.setId("filT");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof FilCroix) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "fil_croix.png"));
                    tmp.setId("filCroix");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof ANDGate) {
                    //ANDGate compAllumable = (ANDGate) c; 
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "and1.png"));
                    tmp.setId("andGate");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof ORGate) {
                    //ORGate compAllumable = (ORGate) c; 
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "or1.png"));
                    tmp.setId("orGate");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                } else if (c instanceof NOTGate) {
                    //NOTGate compAllumable = (NOTGate) c; 
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "not1.png"));
                    tmp.setId("notGate");
                    initializeImageView(tmp);
                    grid.add(tmp, column, row);
                }
            }
        }

    }

    private void initializeImageView(ImageView imgv) {
        imgv.setFitWidth(50);
        imgv.setFitHeight(50);
        imgv.setOnDragDetected(this::dragComposantFromGrid);
        imgv.setOnDragDropped(this::dropComposant);
        imgv.setOnDragOver(this::overComposant);
        imgv.setOnMouseClicked(this::mouseClickCase);
        imgv.setPickOnBounds(true);
        imgv.setPreserveRatio(true);
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

    private Connectable[][] getCircuit() {
        return connectables2D;
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
