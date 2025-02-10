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
public class LisaaHenkilonKorttiMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("LisaaHenkilonKorttiGUIView.fxml"));
            final Pane root = ldr.load();
            //final LisaaHenkilonKorttiGUIController lisaahenkilonkorttiCtrl = (LisaaHenkilonKorttiGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("lisaahenkilonkortti.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("LisaaHenkilonKortti");
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