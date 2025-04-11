package KorttiRekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author OMISTAJA
 * @version 11 Apr 2025
 * Kysytään asiakkaan tiedot luomalla sinne uusi dialogi
 */
public class MuokkaJasenGUIController implements ModalControllerInterface<Asiakas>, Initializable {
    
    @FXML private Label labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editHetu;
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinumero;
    @FXML private TextField editPostiToimipaikka;
    @FXML private TextField editPuhelinnumero;
    @FXML private TextField editSahkoposti;
    
    /**
     * @param modalityStage mille ollaan modaalisia, null meinaa sovellukselle
     * @param oletus mitä dataa näytetään oletuksena
     * @return null jos painetaan peruuta/cancel, muuten täytetty tietue
     */
    public static Asiakas kysyAsiakas(Stage modalityStage, Asiakas oletus) {
        return ModalController.showModal(MuokkaJasenGUIController.class.getResource("MuokkaJasenGUIView.fxml"), "AgoBank", modalityStage, oletus);
    }
    
    @FXML private void handleOK() {
        if (asiakasKohdalla != null && asiakasKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleCancel() {
        asiakasKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
        
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }

    @Override
    public Asiakas getResult() {
        return asiakasKohdalla;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub 
    }
    
    
    
    /**
     * Näytetään asiakkaan tiedot TextField komponentteihin
     * @param edits taulukko, jossa
     * @param asiakas näytettävä asiakas
     */
    public static void naytaAsiakas (TextField[] edits, Asiakas asiakas) {
        if (asiakas == null) return;
        edits[0].setText(asiakas.getNimi());
        edits[1].setText(asiakas.getHetu());
        edits[2].setText(asiakas.getKatuosoite());
        edits[3].setText(asiakas.getPostinumero());
        edits[4].setText(asiakas.getPostiToimipaikka());
        edits[5].setText(asiakas.getPuhelinnumero());
        edits[6].setText(asiakas.getSahkoposti());
    }
    
    private Asiakas asiakasKohdalla;
    private TextField[] edits;
    
    
    protected void alusta() {
        edits = new TextField[] {editNimi, editHetu, editKatuosoite, editPostinumero, editPostiToimipaikka, editPuhelinnumero, editSahkoposti};
        
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource())));
        }
    }
    
    private void kasitteleMuutosJaseneen(int k, TextField edit) {
        if (asiakasKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1 : virhe = asiakasKohdalla.setNimi(s); break;
            case 2 : virhe = asiakasKohdalla.setHetu(s); break;
            case 3 : virhe = asiakasKohdalla.setKatuosoite(s); break;
            case 4 : virhe = asiakasKohdalla.setPostinumero(s); break;
            case 5 : virhe = asiakasKohdalla.setPostiToimipaikka(s); break;
            case 6 : virhe = asiakasKohdalla.setPuhelinnumero(s); break;
            case 7 : virhe = asiakasKohdalla.setSahkoposti(s); break;
            default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }

    /**
     * Näytetään asiakkaan tiedot TextField komponentteihin
     * @param asiakas näytettävä asiakas
     */
    public void naytaAsiakas (Asiakas asiakas) {
        naytaAsiakas(edits, asiakas);
    }

    @Override
    public void setDefault(Asiakas oletus) {
        asiakasKohdalla = oletus;
        naytaAsiakas(asiakasKohdalla);
        
    }
}