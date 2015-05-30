package ca.qc.bdeb.sim.projetmanhattan;

import ca.qc.bdeb.sim.projetmanhattan.controller.FXMLDocumentController;
import ca.qc.bdeb.sim.projetmanhattan.model.analog.CircuitAnalogue;
import ca.qc.bdeb.sim.projetmanhattan.controller.AnalyseC;
import ca.qc.bdeb.sim.projetmanhattan.model.digital.CircuitDigital;
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
        stage.setTitle("Projet Manhattan");
        CircuitAnalogue circuitAnalogue = new CircuitAnalogue();
        CircuitDigital circuitNumerique = new CircuitDigital();
        AnalyseC circuitGraphique = new AnalyseC();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/mixte/FXMLDocument.fxml"));
        Parent root = (Parent) loader.load();
        FXMLDocumentController ctrl = loader.getController();

        ctrl.setCircuitAnalogue(circuitAnalogue);
        ctrl.setCircuitNumerique(circuitNumerique);
        ctrl.setCircuitGraphique(circuitGraphique);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private static void main(String[] args) {
        launch(args);
    }

}
