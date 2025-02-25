package KorttiRekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * @author OMISTAJA
 * @version 9.2.2025
 *
 */
public class PaaikkunaGUIController implements ModalControllerInterface <String> {
    @FXML private void TestiPesti() {
        Dialogs.showMessageDialog("Ei osata viel :D");
    }
    
    @FXML private void Apua() {
        Dialogs.showMessageDialog("You're on your own lil bro.");
    }
    
    @FXML private void Tietoja() {
        Dialogs.showMessageDialog("Kunpa tietäisin itekki.");
    }
    
    @FXML private void Lopeta() {
        Platform.exit();
    }
    
    //Jäsen muokkaukset
    @FXML private void handleMuokkaJasen() {
        MuokkaJasenGUIController.alkuNaytto(null, "Muokkaa jäsenen tietoja"); //toimii // ei itseasiassa toimi // nyt toimii, oli ongelma FXML tiedostossa, joka oli bin kansiossa
    }
    
    @FXML private void handleLisaaJasen() {
        LisaaJasenGUIController.alkuNaytto(null, "Lisää uusi jäsen"); //toimii 
    }
    
    @FXML private void handlePoistaJasen() {
        PoistaJasenGUIController.alkuNaytto(null, "Poista jäsen"); //toimii // ei toimi
    }
    
    //Pankkikortti muokkaukset
    @FXML private void handleLisaaPankkiKortti() {
        LisaaKorttiGUIController.alkuNaytto(null, "Lisää uusi pankkikortti"); //toimii
    }
    
    @FXML private void handleMuokkaaPankkiKorttiVali() {
        MuokkaaGUIController.alkuNaytto(null, "Muokkaa pankkikortin tietoja"); //toimii
    }
    
    @FXML private void handlePoistaPankkiKorttiVali() {
        PoistaPankkiKorttiGUIController.alkuNaytto(null, "Poista pankkikortti"); //toimii
    }
    
    public static String alkuNaytto(Stage modalityStage, String oletus) {
        return ModalController.showModal(MuokkaJasenGUIController.class.getResource("PaaikkunaGUIView.fxml"), "Tervetuloa AgoBank:iin!", modalityStage, oletus);
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
}