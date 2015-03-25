package ca.qc.bdeb.sim.projetmanhattan.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;


/**
 * Controlleur
 * @author Marc-Antoine Lalonde
 * @author Patrick Richer St-Onge
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    StackPane resistance;
    
    @FXML
    StackPane source;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resistance.setStyle("-fx-padding: 10;\n" +
                            "-fx-background-color: firebrick;");
        
        source.setStyle("-fx-padding: 10;\n" +
                            "-fx-background-color: blue;");        
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
            
            String id = db.getString();
            addComposant(id);
            
            
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
        db.setContent(content);
        
        source.setImage(null);
        
        event.consume();    
    }
    
    @FXML
    private void keyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            System.out.println("Enter pressed");
        }        
    }
    
    @FXML
    private void mouseClickCase(MouseEvent event) {
        ImageView source = (ImageView) event.getSource(); 
        source.setRotate(source.getRotate()+90);
    }
    
    private void addComposant(String id) {
        if (id.equals("source")) {
            
        }
        else if (id.equals("resistance")) {
            
        }
        else if (id.equals("filDroit")) {
            
        }
        else if (id.equals("filCoin")) {
            
        }
        else if (id.equals("filT")) {
            
        }
        else if (id.equals("filCroix")) {
            
        }
        else {
            System.out.println("ERROR:Composant not implemented");
        }
    }

    
   
    
}
