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
public class PaaikkunaGUIController implements Initializable {
    
    @FXML private ListChooser<Asiakas> chooserAsiakkaat; //jäsenien/asiakkaiden käsittely
    @FXML private ScrollPane panelAsiakas;
    @FXML private ScrollPane panelKortti;
    
    @FXML private void TestiPesti() {
        try {
            pankki.tallenna();
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        MuokkaJasenGUIController.alkuNaytto(null, "Muokkaa jäsenen tietoja"); //toimii // ei itseasiassa toimi // nyt toimii, oli ongelma FXML tiedostossa, joka oli bin kansiossa
    }
    
        
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        pankki = new Pankki();
        alusta();
    }
    
    /**
     * Manu is the party king
     */
    @FXML
    public void handleLisaaAsiakas() { //kun käyttäjä painaa "Lisää Asiakas" nappia.
        //LisaaJasenGUIController.alkuNaytto(null, "Lisää uusi jäsen"); //toimii 
        System.out.println("Nappia painettu!"); //mun debug juttu
        lisaaAsiakas();
    }
    
    private void hae(int asiakasNumero) {
        chooserAsiakkaat.clear(); //tyhjennetään lista incase dispareita on olemassa
        int indeksi = 0; //auttaa kattoo mistä kohti meil löytyy jäsennumero
        for(int i = 0; i < pankki.getAsiakkaat(); i++) { //käydään läpi kaikki pankin asiakkaat ja lisätään ne valintalistaan
            Asiakas asiakas = pankki.annaAsiakas(i); 
            if (asiakas.getTunnusNro() == asiakasNumero) {
                indeksi = i;
            }
            chooserAsiakkaat.add("" + asiakas.getNimi(), asiakas); //Lisätään valittu asiakas //Update: Videota katsoessa ongelmana on se, ettei olla määritelty nimiä kunnolla, niin käytetään tunnusNro väliaikaisena ratkaisuna //Update: holy fucking shit
        }
        chooserAsiakkaat.setSelectedIndex(indeksi); //valitsee listasta sen asiakkaan, joka vastaa annettua asiakasNumeroa
    }
    
    private void lisaaAsiakas() { //itsestäänselvä
        Asiakas uusi = new Asiakas(); //luodaan oma muuttuja johon heitetään Asiakkaasta tietoa
        uusi.rekisteroi(); //rekisteröidään (= annetaan tunnusNro)
        uusi.vastaaErik(); //täytetään oletustiedoilla  TODO: random tietoja ht6 varten
        try {
            pankki.lisaa(uusi); //lisää asiakkaan pankin rekisteriin
        } catch (SailoException e) { //Jos tulee SailoException / on liikaa...
            Dialogs.showMessageDialog("Ongelma uuden lisäämisessä: " + e.getMessage());
        }
        hae(uusi.getTunnusNro()); //päivitetään käyttöliittymä, jotta näkyisi listassa.
    }
    
    private Pankki pankki;
    private TextArea areaAsiakas = new TextArea(); //Rakennusteline
    private TextArea areaKortti = new TextArea(); // Rakennusteline kortteja varten
    
    public void setPankki(Pankki pankki) {
        this.pankki = pankki;
    }
    
    private void alusta() {
    	
        panelAsiakas.setContent(areaAsiakas); //asetetaan panelAsiakas kunnolla TextAreaan
        areaAsiakas.setFont(new Font("Courier New", 12)); //uudistetaan fontit ja sen koko
        panelAsiakas.setFitToHeight(true); //tekstialue mukautuu panelin korkeuteen.
        chooserAsiakkaat.clear(); //jos sielä on disparina jo jotain tietoa, poistetaan kaikki
        chooserAsiakkaat.addSelectionListener(e -> naytaAsiakas()); //Kun käyttäjä valitsee asiakkaan listalta, naytaAsiakas metodia kutsutaan ja valitun asiakkaan tiedot tulee näkyville.
        
        panelKortti.setContent(areaKortti); //asetetaan panelAsiakas kunnolla TextAreaan
        areaKortti.setFont(new Font("Courier New", 12)); //uudistetaan fontit ja sen koko
        panelKortti.setFitToHeight(true); //tekstialue mukautuu panelin korkeuteen.
        chooserAsiakkaat.clear(); //jos sielä on disparina jo jotain tietoa, poistetaan kaikki
        chooserAsiakkaat.addSelectionListener(k -> naytaKortti()); //Kun käyttäjä valitsee asiakkaan listalta, naytaAsiakas metodia kutsutaan ja valitun asiakkaan tiedot tulee näkyville.
       
    }
    
    private void naytaAsiakas() {
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject(); //hakee valitun asiakkaan
        if (asiakasKohdalla == null) return; //varmistetaan vaan
        areaAsiakas.setText(""); //tyhjennetään tekstialue
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAsiakas)) {
            asiakasKohdalla.tulosta(os);  //tulostetaan asiakkaan perustiedot
        }            
    }
    
    private void naytaKortti() {
    	Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject(); //hakee valitun asiakkaan
        if (asiakasKohdalla == null) return; //varmistetaan vaan
        areaKortti.setText(""); //tyhjennetään tekstialue
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKortti)) {
            
            //Debit
            List<Debit> debitit = pankki.annaDebit(asiakasKohdalla); //haetaan, onko debit korttia lisätty         
            for (Debit deb : debitit) { //jos on, tulostetaan
                deb.tulosta(os);
                os.println(" ");
            }
              
            List<Credit> creditit = pankki.annaCredit(asiakasKohdalla);         
            for (Credit cred : creditit) { //jos on, tulostetaan
                cred.tulosta(os); 
                os.println(" ");
            } 
            
            List<Yhdistelmä> yhdistelmat = pankki.annaYhdistelma(asiakasKohdalla);         
            for (Yhdistelmä yhd : yhdistelmat) { //jos on, tulostetaan
                yhd.tulosta(os);
                os.println(" ");
            }
        }            
    }
    
    
    @FXML private void handlePoistaJasen() {
        PoistaJasenGUIController.alkuNaytto(null, "Poista jäsen"); //toimii // ei toimi
    }
    
    //Debit kortin logiikka UUSI JUTTU -----------------------------------------------------
    @FXML private void handleLisaaDebitKortti() { 
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject(); //hakee valitun asiakkaan chooserasiakkaat komponentista
        if (asiakasKohdalla == null) return;
        Debit deb = new Debit(); //luo debit kortin
        deb.rekisteroi(); //rekisteröi kortin kutsumalla sitä
        deb.vastaaDebit(asiakasKohdalla.getTunnusNro()); //täyttää kortin tiedot    TODO: posauta oikea dialogi, HT7
        pankki.lisaaDebit(deb); //lisää rekisteröidyn kortin pankin rekisteriin.
        hae(asiakasKohdalla.getTunnusNro()); //päivittää näkymän kutsumalla tunnusNro, jotta lisätty kortti näkyy käyttöliittymäs
    }
    
    //Luottokortin logiikka UUSI JUTTU -----------------------------------------------------
    @FXML private void handleLisaaLuottoKortti() {
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        Credit cre = new Credit();
        cre.rekisteroi();
        cre.vastaaCredit(asiakasKohdalla.getTunnusNro()); //TODO: posauta oikea dialogi, HT7
        pankki.lisaaCredit(cre);
        hae(asiakasKohdalla.getTunnusNro());
    }
    
    //Yhdistelmäkortin logiikka UUSI JUTTU -----------------------------------------------------
    @FXML private void handleLisaaYhdistelmaKortti() {
        Asiakas asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        Yhdistelmä yhd = new Yhdistelmä();
        yhd.rekisteroi();
        yhd.vastaaYhdistelmä(asiakasKohdalla.getTunnusNro()); //TODO: posauta oikea dialogi, HT7
        pankki.lisaaYhdistelma(yhd);
        hae(asiakasKohdalla.getTunnusNro());
    }
    
    @FXML private void handleMuokkaaPankkiKorttiVali() {
        MuokkaaGUIController.alkuNaytto(null, "Muokkaa pankkikortin tietoja"); //toimii
    }
    
    @FXML private void handlePoistaPankkiKorttiVali() {
        PoistaPankkiKorttiGUIController.alkuNaytto(null, "Poista pankkikortti"); //toimii
    }
    
    /**
     * @param modalityStage Stage-olio
     * @param oletus välitetään ikkunaan
     * @return syöttämä arvo tai `null`
     */
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