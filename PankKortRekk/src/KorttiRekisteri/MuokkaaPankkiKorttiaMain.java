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
public class MuokkaaPankkiKorttiaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("MuokkaaPankkiKorttiaGUIView.fxml"));
            final Pane root = ldr.load();
            //final MuokkaaPankkiKorttiaGUIController muokkaapankkikorttiaCtrl = (MuokkaaPankkiKorttiaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("muokkaapankkikorttia.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("MuokkaaPankkiKorttia");
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