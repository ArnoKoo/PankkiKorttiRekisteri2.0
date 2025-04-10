package KorttiRekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Dani vittu katso ens kerralla et keltaset viivat katoavat.
 * Pankkikortti toimii samalla tavalla kuin asiakkaat
 * @author OMISTAJA
 * @version 10 Mar 2025
 *
 */
public class Pankkikortti {
    
    private static final int    MAX_KORTIT                  = 40; //ei implementoitu ainakaan viel
    private int                 lkm                         = 3; //tää olennaisempi
    private String              tiedostonNimi               = "kortit";
    //private Debit               debit[]                     = new Debit[MAX_KORTIT];
    //private Credit              credit[]                    = new Credit[MAX_KORTIT];
    //private Yhdistelmä          yhdistelmä[]                = new Yhdistelmä[MAX_KORTIT];
    private boolean             muutettu                    = false;
    

    
    //-------------------------------------------------------------------------------------------------------------------------------------- Arskan juttuja
    
    /**
    * alustaa olion
    */
    public Pankkikortti() {
        // Jää tyhjäksi toistaiseksi
    }
    

    private Collection<Debit> debitLista = new ArrayList<Debit>(); //UUSI JUTTU: saa nähdä, miten tää soveltuu sinne taulukkoon muiden kanssa
    
    /**
     * @param deb lisättävä debitkortti
     */
    public void lisaaDebitti(Debit deb) { //Debitkorttien logiikka, lisää uuden pankkikortin kokoelmaan
        debitLista.add(deb);
        muutettu = true;
    }
    
    /**
     * Palauttaa listan tietylle asiakkaalle kuuluvista korteista
     * @param tunnusNro tunnusnumero
     * @return löydetyt numerot
     */
    public List<Debit> annaDebitti(int tunnusNro) { 
        List<Debit> loydetyt = new ArrayList<Debit>();
        for (Debit deb : debitLista) { //Käy läpi kaikki kortit ja...
            if (deb.getAsiakasNro() == tunnusNro) { //...vertailee asiakkaan tunnusnumeroa
                loydetyt.add(deb); //lisää löydetyn
            }
        }
        return loydetyt; //palauttaa löydetyn
    }
    
    

    private Collection<Credit> creditLista = new ArrayList<Credit>(); //UUSI JUTTU: saa nähdä, miten tää soveltuu sinne taulukkoon muiden kanssa
    
    /**
     * @param cred lisättävä luottokortti
     */
    public void lisaaKreditti(Credit cred) { //Luottokorttien logiikka, lisää uuden pankkikortin kokoelmaan
        creditLista.add(cred);
        muutettu = true;
    }
    
    /**
     * Palauttaa listan tietylle asiakkaalle kuuluvista korteista
     * @param tunnusNro tunnusnumero
     * @return löydetyt numerot
     */
    public List<Credit> annaKreditti(int tunnusNro) {
        List<Credit> loydetyt = new ArrayList<Credit>();
        for (Credit cred : creditLista) { //Käy läpi kaikki kortit ja...
            if (cred.getAsiakasNro() == tunnusNro) { //...vertailee asiakkaan tunnusnumeroa
                loydetyt.add(cred); //lisää löydetyn
            }
        }
        return loydetyt; //palauttaa löydetyn
    }
    
    

    private Collection<Yhdistelmä> yhdistelmaLista = new ArrayList<Yhdistelmä>();
    
    /**
     * @param yhd lisättävä kredittikortti
     */
    public void lisaaYhdistelma(Yhdistelmä yhd) { //Yhdistelmäkorttien logiikka, lisää uuden pankkikortin kokoelmaan
        yhdistelmaLista.add(yhd);
        muutettu = true;
    }
    
    /**
     * Palauttaa listan tietylle asiakkaalle kuuluvista korteista
     * @param tunnusNro tunnusnumero
     * @return löydetyt numerot
     */
    public List<Yhdistelmä> annaYhdistelma(int tunnusNro) {
        List<Yhdistelmä> loydetyt = new ArrayList<Yhdistelmä>();
        for (Yhdistelmä yhd : yhdistelmaLista) { //Käy läpi kaikki kortit ja...
            if (yhd.getAsiakasNro() == tunnusNro) { //...vertailee asiakkaan tunnusnumeroa
                loydetyt.add(yhd); //lisää löydetyn
            }
        }
        return loydetyt; //palauttaa löydetyn
    }
    
    //-------------------------------------------------------------------------------------------------------------------------------------- Arskan jutut loppuvat
    
    /**
     * Lisää debit kortin, jos on liikaa kortteja niin heittää virheen
     * @param kortti kortti debittiä varten
     * @throws SailoException jos liikaa kortteja
     */
    /*public void lisaaDebit(Debit kortti) throws SailoException{
        if (lkm >= debit.length) throw new SailoException("Liikaa alkioita");
        debit[lkm] = kortti;
        lkm++;
        muutettu = true;
    }
    */
    
    /**
     * Lisää credit kortin, jos on liikaa kortteja niin heittää virheen
     * @param kortti kortti credittiä varten
     * @throws SailoException jos liikaa kortteja
     
    public void lisaaCredit(Credit kortti) throws SailoException{
        if (lkm >= credit.length) throw new SailoException("Liikaa alkioita");
        credit[lkm] = kortti;
        lkm++;
        muutettu = true;
    }
    */
    
    /**
     * Lisää yhdistelmä kortin, jos on liikaa kortteja niin heittää virheen
     * @param kortti kortti yhdistelmäkorttia varten
     * @throws SailoException jos liikaa kortteja
     
    public void lisaaYhdistelmä(Yhdistelmä kortti) throws SailoException{
        if (lkm >= yhdistelmä.length) throw new SailoException("Liikaa alkioita");
        yhdistelmä[lkm] = kortti;
        lkm++;
        muutettu = true;
    }
    */
    
    /**
     * lukee tiedostosta (TODO)
     * @param tied tiedostonNimi kiinni tässä (Dani, täydennä)
     * @throws SailoException ei kye lukemaan (Dani, täydennä)
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        
        //Debuggausta
        String fileName = getTiedostonNimi();
        File file = new File(fileName);
        System.out.println("DEBUG: Luetaan tiedostoa: " + file.getAbsolutePath());
        System.out.println("DEBUG: Olemassa? " + file.exists());
        
        try ( BufferedReader fi = new BufferedReader(new FileReader(file)) ) {
        	
            String rivi;
                        
            while ( (rivi = fi.readLine()) != null ) {
            	System.out.println("DEBUG: Luettu rivi: " + rivi);
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                
                if (rivi.startsWith("DEBIT|")) {
                    Debit deb = new Debit();
                    deb.parse(rivi.substring(6)); // Skip "DEBIT|"
                    lisaaDebitti(deb);
                } 
                else if (rivi.startsWith("CREDIT|")) {
                    Credit cre = new Credit();
                    cre.parse(rivi.substring(7)); // Skip "CREDIT|"
                    lisaaKreditti(cre);
                } 
                else if (rivi.startsWith("YHDISTELMA|")) {
                    Yhdistelmä yhd = new Yhdistelmä();
                    yhd.parse(rivi.substring(11)); // Skip "YHDISTELMA|"
                    lisaaYhdistelma(yhd);
                }
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }

    
    /**
     * Talentaa tiedostoon (TODO)
     * @throws SailoException ei kye tallentamaan (Dani, täydennä)
     */ 
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
        	        	
        	for (Debit deb : debitLista) {
                fo.println("DEBIT|" + deb.toString());
            }
            for (Credit cre : creditLista) {
                fo.println("CREDIT|" + cre.toString());
            }
            for (Yhdistelmä yhd : yhdistelmaLista) {
                fo.println("YHDISTELMA|" + yhd.toString());
            }
            
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    public int getLkm() {
        return lkm;
    }


    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonNimi + ".bak";
    }

    
    
    /**
     * Testiohjelma harrastuksille UUSI JUTTU: Testataan, tulostuuko oikein. Lainattu Vesan esimerkkiohjelmasta ja muunneltu.
     * @param args ei käytössä
     */
    public static void main(String[] args) { //säilytän muuttujien nimet vaan siksi, että hahmotan mikä on mikä
        //Debitti
        Pankkikortti harrasteet1 = new Pankkikortti();
        Debit pitsi1 = new Debit(1);
        pitsi1.vastaaDebit(2);
        
        //Luottokortti
        Pankkikortti harrasteet2 = new Pankkikortti();
        Credit pitsi2 = new Credit(1);
        pitsi2.vastaaCredit(2);
        
        //Yhdistelmäkortti
        Pankkikortti harrasteet3 = new Pankkikortti();
        Yhdistelmä pitsi3 = new Yhdistelmä(1);
        pitsi3.vastaaYhdistelmä(2);

        
        harrasteet1.lisaaDebitti(pitsi1);
        harrasteet2.lisaaKreditti(pitsi2);
        harrasteet3.lisaaYhdistelma(pitsi3);

        System.out.println("============= Pankkikortti testi =================");
        
        List<Debit> debitit = harrasteet1.annaDebitti(1);
        for (Debit deb : debitit) {
            System.out.print(deb.getAsiakasNro() + " ");
            deb.tulosta(System.out);
        }

        List<Credit> kreditit = harrasteet2.annaKreditti(1);
        for (Credit cred : kreditit) {
            System.out.print(cred.getAsiakasNro() + " ");
            cred.tulosta(System.out);
        }
        
        List<Yhdistelmä> yhdistelmat = harrasteet3.annaYhdistelma(1);
        for (Yhdistelmä yhd : yhdistelmat) {
            System.out.print(yhd.getAsiakasNro() + " ");
            yhd.tulosta(System.out);
        }

    }
    
}