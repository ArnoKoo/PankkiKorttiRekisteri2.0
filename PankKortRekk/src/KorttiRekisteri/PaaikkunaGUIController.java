package KorttiRekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    
    @FXML private TextField editNimi;
    @FXML private TextField editHetu;
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinumero;
    @FXML private TextField editPostiToimipaikka;
    @FXML private TextField editPuhelinnumero;
    @FXML private TextField editSahkoposti;
    
    @FXML private StringGrid<Debit> tableDebit;
    @FXML private StringGrid<Credit> tableCredit;
    @FXML private StringGrid<Yhdistelmä> tableYhdistelma;
    
    private TextField[] edits;
    
    private String pankinNimi = "AgoBank";
    
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
        muokkaa();
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
    
    private void muokkaa() { 
        if ( asiakasKohdalla == null ) return; 
        try { 
            Asiakas asiakas; 
            asiakas = MuokkaJasenGUIController.kysyAsiakas(null, asiakasKohdalla.clone()); 
            if ( asiakas == null ) return; 
            pankki.korvaaTaiLisaa(asiakas); 
            hae(asiakas.getTunnusNro()); 
            } catch (CloneNotSupportedException e) { 
                // 
            } catch (SailoException e) { 
                Dialogs.showMessageDialog(e.getMessage()); 
            } 
        }

    /**
     * 
     */
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
    
    /**
     * hienompi
     */
    protected void lisaaAsiakas() {
        try {
            Asiakas uusi = new Asiakas();
            uusi = MuokkaJasenGUIController.kysyAsiakas(null, uusi);
            if (uusi == null) return;
            uusi.rekisteroi();
            uusi.vastaaErik();
            pankki.lisaa(uusi);
            hae(uusi.getTunnusNro());
            pankki.lisaa(uusi); 
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa: " + ex.getMessage());
        }
             
    }
    
    private Pankki pankki = new Pankki();
    //private TextArea areaAsiakas = new TextArea();
    //private TextArea areaKortti = new TextArea();
    private Asiakas asiakasKohdalla;
    
    /**
     * @param pankki setteri
     */
    public void setPankki(Pankki pankki) {
        this.pankki = pankki;
    }
    
    /**
     * @return true jos voi
     */
    public boolean voikoSulkea() {
        try {
            pankki.tallenna();
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    
    private void alusta() {
        //panelAsiakas.setContent(areaAsiakas);
        //areaAsiakas.setFont(new Font("Courier New", 12));
        panelAsiakas.setFitToHeight(true);
        
        //panelKortti.setContent(areaKortti);
        //areaKortti.setFont(new Font("Courier New", 12));
        panelKortti.setFitToHeight(true);
        
        chooserAsiakkaat.addSelectionListener(e -> {
            System.out.println("Asiakas valittu: " + chooserAsiakkaat.getSelectedObject());
            naytaAsiakas();
            System.out.println(" ");
            naytaKortti(asiakasKohdalla);
            System.out.println(" ");
        });
        
        edits = new TextField[] {editNimi, editHetu, editKatuosoite, editPostinumero, editPostiToimipaikka, editPuhelinnumero, editSahkoposti};
    }

    
    private void naytaAsiakas() {
        asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        //areaAsiakas.setText("");
        //try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAsiakas)) {
        //    asiakasKohdalla.tulosta(os);
        //}
        
        MuokkaJasenGUIController.naytaAsiakas(edits, asiakasKohdalla);
        naytaKortti(asiakasKohdalla);
    }
    
    private void naytaKortti(Asiakas asiakas) {
        tableDebit.clear();
        tableCredit.clear();
        tableYhdistelma.clear();
        if (asiakasKohdalla == null) return;
        
            List<Debit> debitit = pankki.annaDebit(asiakas);
            for (Debit deb : debitit) {
                naytaDebit(deb);
            }
            
            List<Credit> creditit = pankki.annaCredit(asiakas);
            for (Credit cred : creditit) {
                naytaCredit(cred);
            }

            List<Yhdistelmä> yhdistelmat = pankki.annaYhdistelma(asiakas);
            for (Yhdistelmä yhd : yhdistelmat) {
                naytaYhdistelma(yhd);
            }
                      
    }
    
    private void naytaDebit(Debit debit) {
        System.out.println("Debit toString(): " + debit.toString());
        String[] rivi = debit.toString().split("\\|");
        tableDebit.add(debit,rivi[2],rivi[3],rivi[4],rivi[5],rivi[6],rivi[7],rivi[8],rivi[9]);
    }
    
    private void naytaCredit(Credit credit) {
        String[] rivi = credit.toString().split("\\|");
        tableCredit.add(credit,rivi[2],rivi[3],rivi[4],rivi[5],rivi[6],rivi[7],rivi[8],rivi[9]);
    }
    
    private void naytaYhdistelma(Yhdistelmä yhdistelma) {
        String[] rivi = yhdistelma.toString().split("\\|");
        tableYhdistelma.add(yhdistelma,rivi[2],rivi[3],rivi[4],rivi[5],rivi[6],rivi[7],rivi[8],rivi[9]);
    }

    
    /**
     * @param nimi aa
     * @return aa
     */
    protected String lueTiedosto(String nimi) {
        pankinNimi = nimi;
        try {
            pankki.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }
    
    /**
     * @return true jos pygee avaa
     */
    public boolean avaa() {
        String uusinimi = AlkuIkkunaGUIController.kysyNimi(null, pankinNimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }



    
    @FXML private void handlePoistaJasen() {
        PoistaJasenGUIController.alkuNaytto(null, "Poista jäsen");
    }
    
    @FXML private void handleLisaaDebitKortti() { 
        asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        Debit deb = new Debit();
        deb.rekisteroi();
        deb.vastaaDebit(asiakasKohdalla.getTunnusNro());
        pankki.lisaaDebit(deb);
        hae(asiakasKohdalla.getTunnusNro());
    }
    
    @FXML private void handleLisaaLuottoKortti() {
        asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        Credit cre = new Credit();
        cre.rekisteroi();
        cre.vastaaCredit(asiakasKohdalla.getTunnusNro());
        pankki.lisaaCredit(cre);
        hae(asiakasKohdalla.getTunnusNro());
    }
    
    @FXML private void handleLisaaYhdistelmaKortti() {
        asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
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