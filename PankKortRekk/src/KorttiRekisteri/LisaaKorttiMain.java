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
public class LisaaKorttiMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("LisaaKorttiGUIView.fxml"));
            final Pane root = ldr.load();
            //final LisaaKorttiGUIController lisaakorttiCtrl = (LisaaKorttiGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("lisaakortti.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("LisaaKortti");
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