package KorttiRekisteri;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

//import fi.jyu.mit.fxgui.Dialogs;

/**
 * @author OMISTAJA
 * @version 9.2.2025
 *
 */ 
public class AlkuIkkunaGUIController implements ModalControllerInterface <String>{
    @FXML private void SiirryPaaSivulle() {
        PaaikkunaGUIController.alkuNaytto(null, "Muokkaa jäsenen tietoja");
    }
    
    @FXML private Button suljeNappi;
    
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
}