package KorttiRekisteri;
import static KorttiRekisteri.TietueDialogGUIController.getFieldId; 

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
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
    
    private static Debit apuDebit = new Debit(); 
    private static Credit apuCredit = new Credit(); 
    private static Yhdistelmä apuYhdistelma = new Yhdistelmä(); 
    
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
        muokkaa(kentta);
    }
    
    @FXML private void handlePoistaJasen() {
        PoistaJasenGUIController.alkuNaytto(null, "Poista jäsen");
    }
    
    @FXML private void handleLisaaDebitKortti() { 
        if ( asiakasKohdalla == null ) return;
        Debit uusi = new Debit(asiakasKohdalla.getTunnusNro());
        uusi = TietueDialogGUIController.kysyTietue(null, uusi, 0);
        if ( uusi == null ) return;
        uusi.rekisteroi();
        pankki.lisaaDebit(uusi);
        naytaKortti(asiakasKohdalla); 
        tableDebit.selectRow(1000);  // järjestetään viimeinen rivi valituksi
    }
    
    @FXML private void handleLisaaLuottoKortti() {
        if ( asiakasKohdalla == null ) return;
        Credit uusi = new Credit(asiakasKohdalla.getTunnusNro());
        uusi = TietueDialogGUIController.kysyTietue(null, uusi, 0);
        if ( uusi == null ) return;
        uusi.rekisteroi();
        pankki.lisaaCredit(uusi);
        naytaKortti(asiakasKohdalla); 
        tableCredit.selectRow(1000);  // järjestetään viimeinen rivi valituksi
    }
    
    @FXML private void handleLisaaYhdistelmaKortti() {
        if ( asiakasKohdalla == null ) return;
        Yhdistelmä uusi = new Yhdistelmä(asiakasKohdalla.getTunnusNro());
        uusi = TietueDialogGUIController.kysyTietue(null, uusi, 0);
        if ( uusi == null ) return;
        uusi.rekisteroi();
        pankki.lisaaYhdistelma(uusi);
        naytaKortti(asiakasKohdalla); 
        tableYhdistelma.selectRow(1000);  // järjestetään viimeinen rivi valituksi
    }
    
    @FXML private void handleMuokkaaPankkiKorttiVali() {
        MuokkaaGUIController.alkuNaytto(null, "Muokkaa pankkikortin tietoja");
    }
    
    @FXML private void handlePoistaPankkiKorttiVali() {
        PoistaPankkiKorttiGUIController.alkuNaytto(null, "Poista pankkikortti");
    }
    
    /**
     * 
     */
    @FXML
    public void handleLisaaAsiakas() {
        lisaaAsiakas();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        try {
            pankki.setTiedosto("AgoBank");
            pankki.lueTiedostosta("AgoBank");
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        alusta(); // ilman tiedostonlukua
        hae(0);
    }
    
    private void muokkaa(int k) { 
        if ( asiakasKohdalla == null ) return; 
        try { 
            Asiakas asiakas; 
            asiakas = TietueDialogGUIController.kysyTietue(null, asiakasKohdalla.clone(), k);   
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
     * @param asiakasNumero aa
     */
    protected void hae(int asiakasNumero) {
        chooserAsiakkaat.clear();
        int index = 0;
        Collection<Asiakas> asiakkaat;
        asiakkaat = pankki.etsi("", -1);
        int i = 0;
        for (Asiakas asiakas: asiakkaat) {
            if (asiakas.getTunnusNro() == asiakasNumero) index = i;
            chooserAsiakkaat.add(asiakas.getNimi(), asiakas);
            i++;
        }
        chooserAsiakkaat.setSelectedIndex(index);
    }
    
    /**
     * hienompi
     */
    protected void lisaaAsiakas() {
        try {
            Asiakas uusi = new Asiakas();
            uusi = TietueDialogGUIController.kysyTietue(null, uusi, 1); 
            if ( uusi == null ) return;
            uusi.rekisteroi();
            pankki.lisaa(uusi);
            hae(uusi.getTunnusNro());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }      
    }
    
    private Pankki pankki = new Pankki();
    //private TextArea areaAsiakas = new TextArea();
    //private TextArea areaKortti = new TextArea();
    private Asiakas asiakasKohdalla;
    @FXML private GridPane gridAsiakas;
    private int kentta = 0; 
    
    
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

    
    @SuppressWarnings("deprecation")
    private void alusta() {
        chooserAsiakkaat.clear();
        chooserAsiakkaat.addSelectionListener(e -> naytaAsiakas());
        edits = TietueDialogGUIController.luoKentat(gridAsiakas, new Asiakas());
        for (TextField edit: edits)  
            if ( edit != null ) {  
                edit.setEditable(false);  
                tableDebit.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaDebit(); } );
                tableDebit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaDebit();});  
                
                tableCredit.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaCredit(); } );
                tableCredit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaCredit();}); 
                
                tableYhdistelma.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaYhdistelma(); } );
                tableYhdistelma.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaYhdistelma();}); 
            }
        
        int eka = apuDebit.ekaKentta(); 
        int lkm = apuDebit.getKenttia(); 
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apuDebit.getKysymys(k); 
        tableDebit.initTable(headings); 
        tableDebit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        tableDebit.setEditable(false); 
        tableDebit.setPlaceholder(new Label("Ei kortteja")); 
         
        // Tämä on vielä huono, ei automaattisesti muutu jos kenttiä muutetaan. 
        tableDebit.setColumnSortOrderNumber(1); 
        tableDebit.setColumnSortOrderNumber(2); 
        tableDebit.setColumnWidth(1, 60);
        
        int eka1 = apuCredit.ekaKentta(); 
        int lkm1 = apuCredit.getKenttia(); 
        String[] headings1 = new String[lkm1-eka1]; 
        for (int i=0, k=eka1; k<lkm1; i++, k++) headings1[i] = apuCredit.getKysymys(k); 
        tableCredit.initTable(headings1); 
        tableCredit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        tableCredit.setEditable(false); 
        tableCredit.setPlaceholder(new Label("Ei kortteja")); 
         
        // Tämä on vielä huono, ei automaattisesti muutu jos kenttiä muutetaan. 
        tableCredit.setColumnSortOrderNumber(1); 
        tableCredit.setColumnSortOrderNumber(2); 
        tableCredit.setColumnWidth(1, 60);
        
        int eka2 = apuYhdistelma.ekaKentta(); 
        int lkm2 = apuYhdistelma.getKenttia(); 
        String[] headings2 = new String[lkm2-eka2]; 
        for (int i=0, k=eka2; k<lkm2; i++, k++) headings2[i] = apuYhdistelma.getKysymys(k); 
        tableYhdistelma.initTable(headings2); 
        tableYhdistelma.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        tableYhdistelma.setEditable(false); 
        tableYhdistelma.setPlaceholder(new Label("Ei kortteja")); 
         
        // Tämä on vielä huono, ei automaattisesti muutu jos kenttiä muutetaan. 
        tableYhdistelma.setColumnSortOrderNumber(1); 
        tableYhdistelma.setColumnSortOrderNumber(2); 
        tableYhdistelma.setColumnWidth(1, 60);
    }
    
    private void muokkaaDebit() {
        int r = tableDebit.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Debit deb = tableDebit.getObject();
        if ( deb == null ) return;
        int k = tableDebit.getColumnNr()+deb.ekaKentta();
        try {
            deb = TietueDialogGUIController.kysyTietue(null, deb.clone(), k);
            if ( deb == null ) return;
            pankki.korvaaTaiLisaaDebit(deb); 
            naytaKortti(asiakasKohdalla); 
            tableDebit.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }
    
    private void muokkaaCredit() {
        int r = tableCredit.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Credit cred = tableCredit.getObject();
        if ( cred == null ) return;
        int k = tableCredit.getColumnNr()+cred.ekaKentta();
        try {
            cred = TietueDialogGUIController.kysyTietue(null, cred.clone(), k);
            if ( cred == null ) return;
            pankki.korvaaTaiLisaaCredit(cred); 
            naytaKortti(asiakasKohdalla); 
            tableCredit.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }
    
    private void muokkaaYhdistelma() {
        int r = tableYhdistelma.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Yhdistelmä yhd = tableYhdistelma.getObject();
        if ( yhd == null ) return;
        int k = tableYhdistelma.getColumnNr()+yhd.ekaKentta();
        try {
            yhd = TietueDialogGUIController.kysyTietue(null, yhd.clone(), k);
            if ( yhd == null ) return;
            pankki.korvaaTaiLisaaYhdistelma(yhd); 
            naytaKortti(asiakasKohdalla); 
            tableYhdistelma.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }
    }
    
    
    
    protected void naytaAsiakas() {
        asiakasKohdalla = chooserAsiakkaat.getSelectedObject();
        if (asiakasKohdalla == null) return;
        
        TietueDialogGUIController.naytaTietue(edits, asiakasKohdalla);
        naytaKortti(asiakasKohdalla);
    }
    
    private void naytaKortti(Asiakas asiakas) {
        tableDebit.clear();
        tableCredit.clear();
        tableYhdistelma.clear();
        if (asiakasKohdalla == null) return;
        
            List<Debit> debitit = pankki.annaDebit(asiakas);
            if ( debitit.size() == 0 ) return;
                for (Debit deb: debitit) {
                    naytaDebit(deb);
            }
            
            List<Credit> creditit = pankki.annaCredit(asiakas);
            if ( creditit.size() == 0 ) return;
            for (Credit cred: creditit) {
                naytaCredit(cred);
            }   

            List<Yhdistelmä> yhdistelmat = pankki.annaYhdistelma(asiakas);
            if ( yhdistelmat.size() == 0 ) return;
            for (Yhdistelmä yhd : yhdistelmat) {
                naytaYhdistelma(yhd);
            }
                      
    }
    
    private void naytaDebit(Debit debit) {
        int kenttia = debit.getKenttia(); 
        String[] rivi = new String[kenttia-debit.ekaKentta()]; 
        for (int i=0, k = debit.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = debit.anna(k); 
        tableDebit.add(debit,rivi);
    }
    
    private void naytaCredit(Credit credit) {
        int kenttia = credit.getKenttia(); 
        String[] rivi = new String[kenttia-credit.ekaKentta()]; 
        for (int i=0, k = credit.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = credit.anna(k); 
        tableCredit.add(credit,rivi);
    }
    
    private void naytaYhdistelma(Yhdistelmä yhdistelma) {
        int kenttia = yhdistelma.getKenttia(); 
        String[] rivi = new String[kenttia-yhdistelma.ekaKentta()]; 
        for (int i=0, k = yhdistelma.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = yhdistelma.anna(k); 
        tableYhdistelma.add(yhdistelma,rivi);
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