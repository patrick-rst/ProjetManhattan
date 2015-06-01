package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.exceptions.AnalyseCircuitException;
import ca.qc.bdeb.sim.projetmanhattan.exceptions.CircuitPasGroundException;
import ca.qc.bdeb.sim.projetmanhattan.exceptions.LogicGateConnectionException;
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
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.text.TextFlow;
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

    ObservableList<String> items;

    Map<ImageView, Tooltip> toolTipMap = new HashMap<ImageView, Tooltip>();

    private final PopOver composantEditor = new PopOver();
    private ImageView lastSource = null;

    private Connectable[][] connectables2D;
    private CircuitAnalogue circuitAnalogue;
    private CircuitDigital circuitNumerique;
    private AnalyseC circuitGraphique;

    /**
     * Méthode nécessaire pour le controlleur (première méthode executé)
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createMenu();
        numerique.setDisable(true);

        connectables2D = new Connectable[10][10];

        items = FXCollections.observableArrayList("Bienvenue!");
        list.setItems(items);
    }

    /**
     * Détecte lorsqu'on composant est "drag" de la boite
     *
     * @param event
     */
    @FXML
    private void dragComposant(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(source.getImage());
        content.putString(source.getId());
        db.setContent(content);

        event.consume();

        removeTooltip();
    }

    /**
     * Détecte lorsqu'un composant se trouver au-dessus d'une zone qui peut etre
     * "drop"
     *
     * @param event
     */
    @FXML
    private void overComposant(DragEvent event) {
        if (event.getDragboard().hasImage() && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }

    /**
     * Détecte lorsqu'un composant est "drop" sur la grille
     *
     * @param event
     */
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

    /**
     * Détecte lorsqu'on composant est "drag" de la grille
     *
     * @param event
     */
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

        removeTooltip();

    }

    /**
     * Dérecte lorsqu'il a un click de la souris dans la grille Sélectionne le
     * composant qui est clické
     *
     * @param event
     */
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

    /**
     * Reset la source qui est sélectionné
     */
    private void resetLastSource() {
        if (lastSource != null) {
            lastSource.setStyle("");
        }
        lastSource = null;
    }

    /**
     * Méthode appeller par le CircuitDigitale pour que le circuit numérique
     * s'update en temps réel
     */
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

    /**
     * Change l'image des gates numériques
     */
    private void changeImage() {
        ImageView imgView = lastSource;

        if (imgView != null) {
            int row = grid.getRowIndex(lastSource);
            int column = grid.getColumnIndex(lastSource);

            if (connectables2D[row][column] instanceof ImageChangeable) {
                ImageChangeable compAllumable = (ImageChangeable) connectables2D[row][column];

                compAllumable.nextImage();

                if (compAllumable instanceof LogicGateAbstraite) {
                    LogicGateAbstraite lga = (LogicGateAbstraite) compAllumable;
                    lga.switchGate();
                }

                imgView.setImage(compAllumable.getImage(compAllumable.isActif()));
            }
        }
    }

    /**
     * Méthode qui initialise le menu en entier Créer les titres des menus
     * Associe les méthodes aux sous-menus Associe les raccouris claviers aux
     * sous-menus
     */
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
                stop();
                fileChooser();
            }
        });

        mnuItemLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                wipe();
                fileOpener();
            }
        });

        mnuItemRun.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                run();
            }
        });

        mnuItemStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stop();
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
                stop();
                removeTooltip();
                rotate();
            }
        });

        mnuItemChangeImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stop();
                changeImage();
            }
        });

        mnuItemValue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stop();
                removeTooltip();
                modifierValeur();
            }
        });

        mnuItemEffaceConsole.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                removeTooltip();
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
                dialog.setHeight(600);
                dialog.setWidth(700);

                TextFlow group = new TextFlow();
                Scene scene = new Scene(group);

                String general = "L'interface du programme est divisé en 4 parties. "
                        + "La barre de menu en haut permet d'effectuer plusieurs opérations décrites ci-dessus. "
                        + "Les onglets à gauche permettre d'utilser les différents composants disponibles en les drag and drop sur la grille. "
                        + "La grille au centre est la zone de travail ou on peut construire un circuit. "
                        + "La boite en bas affiche le résultats de l'analyse et les messages d'information ou d'erreur.\n\n";

                String sauvegarde = "Les circuits sur la grille peuvent être sauvegardé à l'aide du menu 'Fichier'. "
                        + "Il faut choisir l'emplacement du fichier de sauvegarde et son nom. "
                        + "Le fichier va avoir l'extension '.ser'. "
                        + "Il est par la suite possible de charger le circuit sur la grille en ouvrant le fichier sauvegardé. "
                        + "Les raccouris claviers sont 'Ctrl-S' pour sauvegarder et 'Ctrl-O' pour ouvrir.\n\n";

                String mode = "Deux modes sont disponibles, soit analogue et numérique. Chaque mode permet d'utiliser seulement les composant respectifs "
                        + "de cette mode. Lorsqu'on change de mode, la grille est effacé (il faut donc sauvegarder avant si on veut conserver son circuit). "
                        + "On peut changer de mode à l'aide du menu 'Mode' ou des raccouris claviers 'Ctrl-N' (pour numérique) et 'Crtl-A' (pour analogue).\n\n";

                String actionGenerale = "Pour analyser le circuit : Action > Exécuter ou appuyer sur la touche 'R'\n"
                        + "Pour effacer tous les composants sur la grille : Action > Effacer Tout ou appuyer sur la touche 'W'\n"
                        + "Pour arrêter l'analyse du circuit en numérique: Action > Arrêt ou appuyer sur la touche 'A'\n"
                        + "Pour effacer la console : Action > Effacer la console ou appuyer sur la touche 'Ctrl-W'\n\n";

                String actionComposant = "Afin d'utiliser les commandes suivantes, un composant sur la grille doit être sélectionné en appuyant dessus avec la souris.\n"
                        + "Pour tourner un composant : Action > Tourner ou appuyer sur la touche 'T'\n"
                        + "Pour modifier la valeur d'un composant : Action > Modifier la valeur ou appuyer sur la touche 'E'\n"
                        + "Pour changer l'image d'une porte numérique : Action > Changer l'image ou appuyer sur la touche 'M'\n\n";

                Text all = new Text(general + sauvegarde + mode + actionGenerale + actionComposant);

                group.getChildren().add(all);

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
                Text info = new Text(25, 100, "Ce programme a été créé dans le cadre du cours de projet d'intégration \nen Sciences informatiques et mathématiques au Collège de Bois-de-Boulogne.");
                group.getChildren().addAll(title, credits, info);

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

    /**
     * Execution de l'analyse sur un circuit analogue ou numérique Affichage des
     * valeurs
     */
    private void run() {
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
                        String info = String.format("Résistance %d\nRésistance: %.2f\nCourant: %.2f", resistanceCount + 1, r.getResistance(), r.getCourant());
                        Tooltip tooltip = new Tooltip(info);
                        hackTooltipStartTiming(tooltip);
                        Tooltip.install(imgV, tooltip);
                        items.add(info);
                        resistanceCount = resistanceCount + 1;
                        toolTipMap.put(imgV, tooltip);
                    } else if (connectables2D[i][j] instanceof SourceFEM) {
                        SourceFEM s = (SourceFEM) connectables2D[i][j];
                        String info = String.format("Source FEM %d\nTension: %.2f\nCourant: %.2f", sourceFEMCount + 1, s.getForceElectroMotrice(), s.getCourant());
                        Tooltip tooltip = new Tooltip(info);
                        hackTooltipStartTiming(tooltip);
                        Tooltip.install(imgV, tooltip);
                        items.add(info);
                        sourceFEMCount = sourceFEMCount + 1;
                        toolTipMap.put(imgV, tooltip);
                    }
                }
            }

        } else if (numerique.isDisabled() == false) {
            //Numérique
            stop();

            //Une ptit pause pour laisse le temps au thread d'arrêter
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            try {
                circuitGraphique.preparerAnalyse(circuitNumerique, connectables2D);
            } catch (LogicGateConnectionException e) {
                items.add(e.getMessage());
            }
        }
        circuitNumerique.analyserCircuit();
    }

    /**
     * Arrête l'analyse du circuit numérique
     */
    private void stop() {
        if (numerique.isDisabled() == false) {
            circuitNumerique.stopAnalyse();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    ImageView img = (ImageView) getNodeByRowColumnIndex(grid, i, j);

                    if (connectables2D[i][j] instanceof ImageChangeable) {
                        ImageChangeable ic = (ImageChangeable) connectables2D[i][j];
                        ic.setActif(false);
                        img.setImage(ic.getImage(false));
                    }

                }
            }
        }
    }

    /**
     * Enlever tous les tooltips sur la grille
     */
    private void removeTooltip() {
        for (Map.Entry<ImageView, Tooltip> entry : toolTipMap.entrySet()) {
            Tooltip.uninstall(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Tourne les composants sur la grille
     */
    private void rotate() {
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

    /**
     * Permet de modifier la valeur des composants, tel que Resistance,
     * SourceTension et SourceCourant Modifie aussi les SourceDigitale pour
     * entrer une chaine de 0 et 1
     */
    private void modifierValeur() {
        ImageView source = lastSource;

        if (source != null) {
            String id = source.getId();

            int row = grid.getRowIndex(source);
            int column = grid.getColumnIndex(source);

            Label lblComposant = new Label();
            Label lblUnite = new Label();
            TextField txtValeur = new TextField();
            txtValeur.setPrefWidth(50);
            Button btn = new Button("Ok");
            btn.setId(row + "," + column);

            if (source.getImage() != null && !source.getId().matches("fil.+|.+Gate|light|sourceDigitale")) {
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

            } else if (source.getImage() != null && source.getId().equals("sourceDigitale")) {
                lblComposant.setText("Source digitale");
                lblUnite.setText("");
                SourceDigitale sourceDigitale = (SourceDigitale) connectables2D[row][column];
                txtValeur.setText(sourceDigitale.getListeOutput());

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

                        ((SourceDigitale) connectables2D[row][column]).setListeOutput(txtValeur.getText());

                        composantEditor.hide();
                    }
                });

                composantEditor.setDetachable(false);
                composantEditor.setContentNode(box);
                composantEditor.show(source, 15);
            }
        }
    }

    /**
     * Efface au complet la grille et reset tout
     */
    private void wipe() {
        removeTooltip();
        stop();

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

    /**
     * Fenetre pour choisir le fichier pour sauvegarder
     */
    private void fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sauvargarder circuit");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serializable Object", "*.ser"));
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());
        if (file != null) {
            writeFile(file);
            items.add(String.format("Circuit sauvegardé : '%s'", file.getAbsolutePath()));
        }

    }

    /**
     * Fenetre pour choisir le fichier à ouvrir
     */
    private void fileOpener() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ouvrir circuit");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Serializable Object", "*.ser"));
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if (file != null) {
            readFile(file);
            items.add(String.format("Circuit ouvert : '%s'", file.getAbsolutePath()));
        }

    }

    /**
     * Écriture de la Sauvegarde comme object sérialisable
     *
     * @param file le fichier à utiliser pour la sauvegarde
     */
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

    /**
     * Lecture d'un fichier avec un object Sauvegarde Initialise tous les
     * composants et l'affichage sur la grille
     *
     * @param file le fichier à lire
     */
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
                    tmp.setImage(new Image(pathImg + "resistance.png"));
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
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "and1.png"));
                    tmp.setId("andGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof ORGate) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "or1.png"));
                    tmp.setId("orGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof NOTGate) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "not1.png"));
                    tmp.setId("notGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof NORGate) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "nor1.png"));
                    tmp.setId("norGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof NANDGate) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "nand1.png"));
                    tmp.setId("nandGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof XORGate) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "xor1.png"));
                    tmp.setId("xorGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof XNORGate) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "xnor1.png"));
                    tmp.setId("xnorGate");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof LumiereOutput) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "lightOff.png"));
                    tmp.setId("light");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                } else if (c instanceof SourceDigitale) {
                    ImageView tmp = new ImageView();
                    tmp.setImage(new Image(pathImg + "sourceDigitale.png"));
                    tmp.setId("sourceDigitale");
                    initializeImageView(tmp, c);
                    grid.add(tmp, column, row);
                }

            }
        }

    }

    /**
     * Initialise les caractérisques nécessaire pour les ImageView contenu dans
     * la grille
     *
     * @param imgv la case en question
     * @param c le composant qui va dans la case
     */
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

    /**
     * Retourne un Node selon la position
     *
     * @param grid l'objet Grid à utiliser
     * @param row la position de la ligne
     * @param column la position de la colonne
     * @return
     */
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

    /**
     * Enlever un composant du tableau 2D
     *
     * @param row la position de la ligne
     * @param column la position de la colonne
     */
    private void removeComposant(int row, int column) {
        connectables2D[row][column] = null;
    }

    /**
     * Ajoute un composant dans le tableau 2D
     *
     * @param id le type de composant
     * @param row le position de la ligne
     * @param column la position de la colonne
     */
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

    /**
     * Un petit hack pour diminuer le temps nécessaire pour faire apparaitre le
     * tooltip
     *
     * @param tooltip l'objet Tooltip en question
     */
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
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
        }
    }

}
