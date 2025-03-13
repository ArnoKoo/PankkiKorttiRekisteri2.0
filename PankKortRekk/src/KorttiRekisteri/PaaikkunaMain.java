package KorttiRekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import KorttiRekisteri.Pankkikortti.*; // Ei tarvitse olla, mutta lisään sen varmuuden vuoksi koska muutaman kerran on tullut ongelmia, koska sitä ei ole


/**
 * @author OMISTAJA
 * @version 9.2.2025
 *
 */


public class PaaikkunaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaikkunaGUIView.fxml"));
            final Pane root = ldr.load();
            
            final PaaikkunaGUIController paaikkunaCtrl = (PaaikkunaGUIController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("paaikkuna.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Paaikkuna");
            primaryStage.show();
            
            Pankki pankki = new Pankki();
            paaikkunaCtrl.setPankki(pankki); 
            
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