package KorttiRekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author OMISTAJA
 * @version 13.4.2025
 *
 */
public class TietueDialogMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("TietueDialogGUIView.fxml"));
            final Pane root = ldr.load();
            //final TietueDialogGUIController tietuedialogCtrl = (TietueDialogGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("tietuedialog.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("TietueDialog");
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