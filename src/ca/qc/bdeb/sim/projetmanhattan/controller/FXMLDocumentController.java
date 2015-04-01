package ca.qc.bdeb.sim.projetmanhattan.controller;

import ca.qc.bdeb.sim.projetmanhattan.model.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.view.CircuitGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.Connectable;
import ca.qc.bdeb.sim.projetmanhattan.view.FilCoin;
import ca.qc.bdeb.sim.projetmanhattan.view.FilCroix;
import ca.qc.bdeb.sim.projetmanhattan.view.FilDroit;
import ca.qc.bdeb.sim.projetmanhattan.view.FilT;
import ca.qc.bdeb.sim.projetmanhattan.view.ResistanceGraphique;
import ca.qc.bdeb.sim.projetmanhattan.view.SourceFEMGraphique;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

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
    GridPane grid;

    @FXML
    StackPane resistance;

    @FXML
    StackPane source;
    
    Circuit c;
    CircuitGraphique cg;

//    public FXMLDocumentController(Circuit c, CircuitGraphique cg) {
//        this.c = c;
//        this.cg = cg;
//    }

    public void setC(Circuit c) {
        this.c = c;
    }

    public void setCg(CircuitGraphique cg) {
        this.cg = cg;
    }
    
    public void print() {
        System.out.println(".............");
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
    }

    @FXML
    private void mouseClickCase(MouseEvent event) {
        ImageView source = (ImageView) event.getSource();
        if (event.getButton().equals(MouseButton.PRIMARY) && source.getImage() != null) {
            source.setRotate(source.getRotate() + 90);
            
            int row = grid.getRowIndex(source);
            int column = grid.getColumnIndex(source);
            
            
            ((Connectable) circuit[row][column]).rotater();
            
            
            
        } else if (event.getButton().equals(MouseButton.SECONDARY) && source.getImage() != null) {
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
            SourceFEMGraphique source = new SourceFEMGraphique(c);
            circuit[row][column] = source;
        } else if (id.equals("resistance")) {
            ResistanceGraphique resistance = new ResistanceGraphique(c);
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

    public Connectable[][] getCircuit() {
        return circuit;
    }

}
