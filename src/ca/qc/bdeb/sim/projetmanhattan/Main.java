package ca.qc.bdeb.sim.projetmanhattan;

import ca.qc.bdeb.sim.projetmanhattan.controller.FXMLDocumentController;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.Circuit;
import ca.qc.bdeb.sim.projetmanhattan.view.analog.CircuitGraphique;
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
        stage.setTitle("Simtronique");
        Circuit circuit = new Circuit();
        CircuitGraphique circuitGraphique = new CircuitGraphique(circuit);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/FXMLDocument.fxml"));
        Parent root = (Parent) loader.load();
        FXMLDocumentController ctrl = loader.getController();
        
        ctrl.setCircuit(circuit);
        ctrl.setCircuitGraphique(circuitGraphique);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
