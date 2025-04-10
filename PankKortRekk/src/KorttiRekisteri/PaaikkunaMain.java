package KorttiRekisteri;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

public class PaaikkunaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaikkunaGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final PaaikkunaGUIController pankkiCtrl = (PaaikkunaGUIController)ldr.getController();

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("paaikkuna.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("AgoBank");
            
            Platform.setImplicitExit(false); // tätä ei kai saa laittaa

            primaryStage.setOnCloseRequest((event) -> {
                    if ( !pankkiCtrl.voikoSulkea() ) event.consume();
                });
            
            Pankki pankki = new Pankki();  
            pankkiCtrl.setPankki(pankki); 
            
            primaryStage.show();
            
            Application.Parameters params = getParameters(); 
            if ( params.getRaw().size() > 0 ) 
                pankkiCtrl.lueTiedosto(params.getRaw().get(0));  
            else
                if (!pankkiCtrl.avaa() ) Platform.exit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Käynnistetään käyttöliittymä 
     * @param args komentorivin parametrit
     */
    public static void main(String[] args) {
        launch(args);
    }
}
