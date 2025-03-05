package KorttiRekisteri;

import java.io.PrintStream;
import java.net.URL;
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
public class PaaikkunaGUIController implements Initializable {
    
    @FXML private ListChooser<Asiakas> chooserAsiakkaat; //jäsenien/asiakkaiden käsittely
    @FXML private ScrollPane panelAsiakas;
    
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
    
    
    //Arskan Vesa video seikkailut alkakoon----------------------------------
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    @FXML
    public void handleLisaaJasen() {
        //LisaaJasenGUIController.alkuNaytto(null, "Lisää uusi jäsen"); //toimii 
        System.out.println("Nappia painettu!");
        lisaaJasen();
    }
    
    private void hae(int jasenNumero) {
        chooserAsiakkaat.clear();
        
        int indeksi = 0; //auttaa kattoo mistä kohti meil löytyy jäsennumero
        for(int i = 0; i < pankki.getAsiakkaat(); i++) {
            Asiakas asiakas = pankki.annaAsiakas(i);
            if (asiakas.getTunnusNro() == jasenNumero) {
                indeksi = i;
            }
            chooserAsiakkaat.add("" + asiakas.getNimi(), asiakas); //Ongelmana on se, ettei olla määritelty nimiä kunnolla, niin käytetään tunnusNro väliaikaisena ratkaisuna //Update: holy fucking shit
        }
        chooserAsiakkaat.setSelectedIndex(indeksi); //laitetaan indeksiksi jasenNumero
    }
    
    private void lisaaJasen() { //itsestäänselvä
        Asiakas uusi = new Asiakas(); //luodaan oma muuttuja johon heitetään Asiakkaasta tietoa
        uusi.rekisteroi();
        uusi.vastaaErik();
        try {
            pankki.lisaa(uusi);
        } catch (SailoException e) { //Jos tulee SailoException...
            Dialogs.showMessageDialog("Ongelma uuden lisäämisessä: " + e.getMessage());
        }    
        hae(uusi.getTunnusNro());
    }
    
    
    private Pankki pankki;
    private TextArea areaAsiakas = new TextArea(); //Rakennusteline
    
    public void setPankki(Pankki pankki) {
        this.pankki = pankki;
    }
    
    private void alusta() {               
        panelAsiakas.setContent(areaAsiakas);
        areaAsiakas.setFont(new Font("Courier New", 12));
        panelAsiakas.setFitToHeight(true);
        chooserAsiakkaat.clear();
        chooserAsiakkaat.addSelectionListener(e -> naytaAsiakas()); //Jos joku vaihtaa valintaa niin tulee kutsunto metodi (lambda)
        
    }
    
    private void naytaAsiakas() {
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        areaAsiakas.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAsiakas)) {
            asiakasKohdalla.tulosta(os);
        }            
    }
    
    //seikkailu loppuu-------------------------------------
    
    
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
    /*
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
    */
}