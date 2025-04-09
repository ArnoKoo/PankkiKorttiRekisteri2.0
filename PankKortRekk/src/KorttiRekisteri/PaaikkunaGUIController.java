package KorttiRekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author OMISTAJA
 * @version 9.2.2025
 *
 */
public class PaaikkunaGUIController implements Initializable, ModalControllerInterface<String> {
    
    @FXML private ListChooser<Asiakas> chooserAsiakkaat; //jäsenten/asiakkaiden käsittely
    @FXML private ScrollPane panelAsiakas;
    @FXML private ScrollPane panelKortti;
    
    @FXML private void TestiPesti() {
        try {
            pankki.setTiedosto("AgoBank");
            pankki.tallenna();
        } catch (SailoException e) {
            // Parannettu virheenkäsittely:
            Dialogs.showMessageDialog("Tallennuksessa ongelmia: " + e.getMessage());
        }
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
        MuokkaJasenGUIController.alkuNaytto(null, "Muokkaa jäsenen tietoja");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        try {
            pankki.setTiedosto("AgoBank");
            pankki.lueTiedostosta("AgoBank");  // vain tämä
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tiedoston lukemisessa ongelmia: " + e.getMessage());
        }

        alusta(); // ilman tiedostonlukua
        hae(0);
    }

    @FXML
    public void handleLisaaAsiakas() {
        lisaaAsiakas();
    }
    
    private void hae(int asiakasNumero) {
        chooserAsiakkaat.clear();
        int indeksi = 0;
        System.out.println("Asiakkaita yhteensä: " + pankki.getAsiakkaat());
        System.out.println(" ");
        for (int i = 0; i < pankki.getAsiakkaat(); i++) {
            Asiakas asiakas = pankki.annaAsiakas(i); 
            if (asiakas.getTunnusNro() == asiakasNumero) {
                indeksi = i;
            }
            chooserAsiakkaat.add("" + asiakas.getNimi(), asiakas);
        }
        chooserAsiakkaat.setSelectedIndex(indeksi);
    }
    
    private void lisaaAsiakas() {
        Asiakas uusi = new Asiakas();
        uusi.rekisteroi();
        uusi.vastaaErik();
        try {
            pankki.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelma uuden lisäämisessä: " + e.getMessage());
        }
        hae(uusi.getTunnusNro());
    }
    
    private Pankki pankki = new Pankki();
    private TextArea areaAsiakas = new TextArea();
    private TextArea areaKortti = new TextArea();
    
    public void setPankki(Pankki pankki) {
        this.pankki = pankki;
    }
    
    private void alusta() {
        panelAsiakas.setContent(areaAsiakas);
        areaAsiakas.setFont(new Font("Courier New", 12));
        panelAsiakas.setFitToHeight(true);
        
        panelKortti.setContent(areaKortti);
        areaKortti.setFont(new Font("Courier New", 12));
        panelKortti.setFitToHeight(true);
        
        chooserAsiakkaat.addSelectionListener(e -> {
            System.out.println("Asiakas valittu: " + chooserAsiakkaat.getSelectedObject());
            naytaAsiakas();
            System.out.println(" ");
            naytaKortti();
            System.out.println(" ");
        });
    }

    
    private void naytaAsiakas() {
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        areaAsiakas.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAsiakas)) {
            asiakasKohdalla.tulosta(os);
        }            
    }
    
    private void naytaKortti() {
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        areaKortti.setText("");
        
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKortti)) {
            List<Debit> debitit = pankki.annaDebit(asiakasKohdalla);
            System.out.println("Debit kortteja löytyi: " + debitit.size());
            if (debitit.isEmpty()) {
                os.println("Ei debit-kortteja.");
            }
            for (Debit deb : debitit) {
                deb.tulosta(os);
                os.println(" ");
            }

            List<Credit> creditit = pankki.annaCredit(asiakasKohdalla);
            System.out.println("Credit kortteja löytyi: " + creditit.size());
            if (creditit.isEmpty()) {
                os.println("Ei luottokortteja.");
            }
            for (Credit cred : creditit) {
                cred.tulosta(os);
                os.println(" ");
            }

            List<Yhdistelmä> yhdistelmat = pankki.annaYhdistelma(asiakasKohdalla);
            System.out.println("Yhdistelmä kortteja löytyi: " + yhdistelmat.size());
            if (yhdistelmat.isEmpty()) {
                os.println("Ei yhdistelmäkortteja.");
            }
            for (Yhdistelmä yhd : yhdistelmat) {
                yhd.tulosta(os);
                os.println(" ");
            }
        }            
    }

    
    @FXML private void handlePoistaJasen() {
        PoistaJasenGUIController.alkuNaytto(null, "Poista jäsen");
    }
    
    @FXML private void handleLisaaDebitKortti() { 
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        Debit deb = new Debit();
        deb.rekisteroi();
        deb.vastaaDebit(asiakasKohdalla.getTunnusNro());
        pankki.lisaaDebit(deb);
        hae(asiakasKohdalla.getTunnusNro());
    }
    
    @FXML private void handleLisaaLuottoKortti() {
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        Credit cre = new Credit();
        cre.rekisteroi();
        cre.vastaaCredit(asiakasKohdalla.getTunnusNro());
        pankki.lisaaCredit(cre);
        hae(asiakasKohdalla.getTunnusNro());
    }
    
    @FXML private void handleLisaaYhdistelmaKortti() {
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        Yhdistelmä yhd = new Yhdistelmä();
        yhd.rekisteroi();
        yhd.vastaaYhdistelmä(asiakasKohdalla.getTunnusNro());
        pankki.lisaaYhdistelma(yhd);
        hae(asiakasKohdalla.getTunnusNro());
    }
    
    @FXML private void handleMuokkaaPankkiKorttiVali() {
        MuokkaaGUIController.alkuNaytto(null, "Muokkaa pankkikortin tietoja");
    }
    
    @FXML private void handlePoistaPankkiKorttiVali() {
        PoistaPankkiKorttiGUIController.alkuNaytto(null, "Poista pankkikortti");
    }
    
    /**
     * @param modalityStage Stage-olio
     * @param oletus välitetään ikkunaan
     * @return syöttämä arvo tai null
     */
    public static String alkuNaytto(Stage modalityStage, String oletus) {
        return ModalController.showModal(MuokkaJasenGUIController.class.getResource("PaaikkunaGUIView.fxml"), "Tervetuloa AgoBank:iin!", modalityStage, oletus);
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void handleShown() { 
        //
    }

    @Override
    public void setDefault(String arg0) { 
        //
    }
}