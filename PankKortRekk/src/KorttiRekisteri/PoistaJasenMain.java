package KorttiRekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author OMISTAJA
 * @version 9.2.2025
 *
 */
public class PoistaJasenMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PoistaJasenGUIView.fxml"));
            final Pane root = ldr.load();
            //final PoistaJasenGUIController poistajasenCtrl = (PoistaJasenGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("poistajasen.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("PoistaJasen");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}