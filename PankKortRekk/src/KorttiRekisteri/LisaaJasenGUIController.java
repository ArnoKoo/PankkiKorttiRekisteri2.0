package KorttiRekisteri;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author OMISTAJA
 * @version 9.2.2025
 *
 */
public class LisaaJasenGUIController implements ModalControllerInterface <String> {
    
    @FXML private void TestiPesti() {
        Dialogs.showMessageDialog("Ei toimi viel :D");
    }
    
    @FXML private Button suljeNappi;
    
    @FXML
    public void Lopeta() {
        ModalController.closeStage(suljeNappi);
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
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * @param modalityStage aa
     * @param oletus aa
     * @return aa
     */
    public static String alkuNaytto(Stage modalityStage, String oletus) {
        return ModalController.showModal(MuokkaJasenGUIController.class.getResource("LisaaJasenGUIView.fxml"), "Lisää jäsen", modalityStage, oletus);
    }
}