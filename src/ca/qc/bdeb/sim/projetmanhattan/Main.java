package ca.qc.bdeb.sim.projetmanhattan;

import ca.qc.bdeb.sim.projetmanhattan.controller.Controleur;
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
        CircuitGraphique circuitGraphique = new CircuitGraphique(circuit);

//        Parent root = FXMLLoader.load(getClass().getResource("view/FXMLDocument.fxml"));
        
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom_control.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
//        
        
        
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        Parent root = fxmlLoader.load(getClass().getResource("view/FXMLDocument.fxml"));
//        FXMLDocumentController fxmlController = (FXMLDocumentController) fxmlLoader.getController();  
        
        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("view/FXMLDocument.fxml"));
         Parent root = (Parent) loader.load();
         FXMLDocumentController ctrl = loader.getController();
         

        
        ctrl.print();
        
        ctrl.setC(circuit);
        ctrl.setCg(circuitGraphique);
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Simtronique");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
