package ca.qc.bdeb.sim.projetmanhattan;

import ca.qc.bdeb.sim.projetmanhattan.controller.FXMLDocumentController;
import ca.qc.bdeb.sim.projetmanhattan.model.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.view.CircuitGraphique;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main
 *
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Circuit circuit = new Circuit();
        FXMLDocumentController controller = new FXMLDocumentController();
        CircuitGraphique circuitGraphique = new CircuitGraphique(controller.getCircuit(), circuit);

        Parent root = FXMLLoader.load(getClass().getResource("view/FXMLDocument.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Simtronique");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
