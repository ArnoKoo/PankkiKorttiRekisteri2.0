package KorttiRekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import fxPankki.Asiakas;
import fxPankki.Pankki;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    
    @FXML private void handleOK() {
        if (asiakasKohdalla != null && asiakasKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        if ( pankki != null && pankki.tarkistaOnkoHetu(asiakasKohdalla) ) {
            naytaVirhe("Hetu on jo!");
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
        kentta = Math.max(apuAsiakas.ekaKentta(), Math.min(kentta, apuAsiakas.getKenttia()-1));
        edits[kentta].requestFocus();
    }
    
    
    
    /**
     * Näytetään asiakkaan tiedot TextField komponentteihin
     * @param edits taulukko, jossa
     * @param asiakas näytettävä asiakas
     */
    public static void naytaAsiakas (TextField[] edits, Asiakas asiakas) {
        if (asiakas == null) return;
        for (int k = asiakas.ekaKentta(); k < asiakas.getKenttia(); k++) {
            edits[k].setText(asiakas.anna(k));
        }
    }
    
    private Asiakas asiakasKohdalla;
    private Pankki pankki;
    private TextField[] edits;
    private static Asiakas apuAsiakas = new Asiakas();
    private int kentta = 0;
    @FXML private ScrollPane panelAsiakas;
    @FXML private GridPane gridAsiakas;
    
    private void setKerho(Pankki pankki) {
        this.pankki = pankki;
    }
    
    public static TextField[] luoKentat(GridPane gridJasen) {
        gridJasen.getChildren().clear();
        TextField[] edits = new TextField[apuAsiakas.getKenttia()];
        
        for (int i=0, k = apuAsiakas.ekaKentta(); k < apuAsiakas.getKenttia(); k++, i++) {
            Label label = new Label(apuAsiakas.getKysymys(k));
            gridJasen.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridJasen.add(edit, 1, i);
        }
        return edits;
    }
    
    
    protected void alusta() {
        edits = luoKentat(gridAsiakas);
        for (TextField edit : edits)
            if ( edit != null )
                edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen((TextField)(e.getSource())));
        panelAsiakas.setFitToHeight(true);
    }
    
    protected void kasitteleMuutosJaseneen(TextField edit) {
        if (asiakasKohdalla == null) return;
        int k = getFieldId(edit,apuAsiakas.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = asiakasKohdalla.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
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
    
    
    
    public static Asiakas kysyAsiakas(Stage modalityStage, Asiakas oletus, int kentta) {
        return ModalController.<Asiakas, MuokkaJasenGUIController>showModal(
                MuokkaJasenGUIController.class.getResource("MuokkaJasenGUIView.fxml"),
                    "AgoBank",
                    modalityStage, oletus,
                    ctrl -> ctrl.setKentta(kentta)
                );
    }
    
    private void setKentta(int kentta) {
        this.kentta = kentta;
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