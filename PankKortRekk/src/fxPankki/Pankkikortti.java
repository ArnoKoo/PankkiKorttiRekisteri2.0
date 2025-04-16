package fxPankki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import KorttiRekisteri.SailoException;

/**
 * Dani vittu katso ens kerralla et keltaset viivat katoavat.
 * Pankkikortti toimii samalla tavalla kuin asiakkaat
 * @author OMISTAJA
 * @version 10 Mar 2025
 *
 */
public class Pankkikortti {
    
    @SuppressWarnings("unused")
    private static final int    MAX_KORTIT                  = 40; //ei implementoitu ainakaan viel
    private int                 lkm                         = 3; //tää olennaisempi
    private String              tiedostonNimi               = "kortit";
    //private Debit               debit[]                     = new Debit[MAX_KORTIT];
    //private Credit              credit[]                    = new Credit[MAX_KORTIT];
    //private Yhdistelmä          yhdistelmä[]                = new Yhdistelmä[MAX_KORTIT];
    private boolean             muutettu                    = false;
    
    /**
    * alustaa olion
    */
    public Pankkikortti() {
        // Jää tyhjäksi toistaiseksi
    }
    

    private final List<Debit> debitLista = new ArrayList<Debit>(); //UUSI JUTTU: saa nähdä, miten tää soveltuu sinne taulukkoon muiden kanssa
    
    /**
     * @param deb lisättävä debitkortti
     */
    public void lisaaDebitti(Debit deb) { //Debitkorttien logiikka, lisää uuden pankkikortin kokoelmaan
        debitLista.add(deb);
        muutettu = true;
    }
    
    /**
     * @param tunnusNro asiakkaan tunnusnumero jolle harrastuksia haetaan
     * @return tietorakenne jossa viiteet löydetteyihin pankkikortteihi
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Pankkikortti pankkikortti = new Pankkikortti();
     *  Debit pitsi21 = new Debit(2); pankkikortti.lisaaDebitti(pitsi21);
     *  Debit pitsi11 = new Debit(1); pankkikortti.lisaaDebitti(pitsi11);
     *  Debit pitsi22 = new Debit(2); pankkikortti.lisaaDebitti(pitsi22);
     *  Debit pitsi12 = new Debit(1); pankkikortti.lisaaDebitti(pitsi12);
     *  Debit pitsi23 = new Debit(2); pankkikortti.lisaaDebitti(pitsi23);
     *  Debit pitsi51 = new Debit(5); pankkikortti.lisaaDebitti(pitsi51);
     *  
     *  List<Debit> loytyneet;
     *  loytyneet = pankkikortti.annaDebitti(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = harrasteet.annaDebitti(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = harrasteet.annaDebitti(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == pitsi51 === true;
     * </pre> 
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
    
    

    private final List<Credit> creditLista = new ArrayList<Credit>(); //UUSI JUTTU: saa nähdä, miten tää soveltuu sinne taulukkoon muiden kanssa
    
    /**
     * @param cred lisättävä luottokortti
     */
    public void lisaaKreditti(Credit cred) { //Luottokorttien logiikka, lisää uuden pankkikortin kokoelmaan
        creditLista.add(cred);
        muutettu = true;
    }
    
    /**
     * Palauttaa listan tietylle asiakkaalle kuuluvista luottokorteista.
     * 
     * @param tunnusNro Asiakkaan tunnusnumero, jonka luotto-kortit halutaan.
     * @return Lista löydetyistä luottokorteista.
     * @example
     * <pre name="test">
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Credit cred1 = new Credit(1); cred1.vastaaCredit(2);
     * Credit cred2 = new Credit(1); cred2.vastaaCredit(3);
     * pankkikortti.lisaaKreditti(cred1);
     * pankkikortti.lisaaKreditti(cred2);
     * List<Credit> loydetyt = pankkikortti.annaKreditti(1);
     * loydetyt.size() === 2;
     * loydetyt.get(0) == cred1 === true;
     * loydetyt.get(1) == cred2 === true;
     * </pre>
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
    
    

    private final List<Yhdistelmä> yhdistelmaLista = new ArrayList<Yhdistelmä>();
    
    /**
     * @param yhd lisättävä kredittikortti
     */
    public void lisaaYhdistelma(Yhdistelmä yhd) { //Yhdistelmäkorttien logiikka, lisää uuden pankkikortin kokoelmaan
        yhdistelmaLista.add(yhd);
        muutettu = true;
    }
    
    /**
     * Palauttaa listan tietylle asiakkaalle kuuluvista yhdistelmäkorteista.
     * 
     * @param tunnusNro Asiakkaan tunnusnumero, jonka yhdistelmä-kortit halutaan.
     * @return Lista löydetyistä yhdistelmäkorteista.
     * @example
     * <pre name="test">
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Yhdistelmä yhd1 = new Yhdistelmä(1); yhd1.vastaaYhdistelmä(2);
     * Yhdistelmä yhd2 = new Yhdistelmä(1); yhd2.vastaaYhdistelmä(3);
     * pankkikortti.lisaaYhdistelma(yhd1);
     * pankkikortti.lisaaYhdistelma(yhd2);
     * List<Yhdistelmä> loydetyt = pankkikortti.annaYhdistelma(1);
     * loydetyt.size() === 2;
     * loydetyt.get(0) == yhd1 === true;
     * loydetyt.get(1) == yhd2 === true;
     * </pre>
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
    
    /**
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Pankkikortti pankkikortti = new Pankkikortti();
     *  Debit pitsi21 = new Debit(); pitsi21.vastaaDebit(2);
     *  Debit pitsi11 = new Debit(); pitsi11.vastaaDebit(1);
     *  Debit pitsi22 = new Debit(); pitsi22.vastaaDebit(2); 
     *  Debit pitsi12 = new Debit(); pitsi12.vastaaDebit(1); 
     *  Debit pitsi23 = new Debit(); pitsi23.vastaaDebit(2); 
     *  String tiedNimi = "testiPankki";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  pankkikortti.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  pankkikortti.lisaaDebitti(pitsi21);
     *  pankkikortti.lisaaDebitti(pitsi11);
     *  pankkikortti.lisaaDebitti(pitsi22);
     *  pankkikortti.lisaaDebitti(pitsi12);
     *  pankkikortti.lisaaDebitti(pitsi23);
     *  pankkikortti.tallenna();
     *  pankkikortti = new Pankkikortti();
     *  pankkikortti.lueTiedostosta(tiedNimi);
     *  Iterator<Debit> i = pankkikortti.iterator();
     *  i.next().toString() === pitsi21.toString();
     *  i.next().toString() === pitsi11.toString();
     *  i.next().toString() === pitsi22.toString();
     *  i.next().toString() === pitsi12.toString();
     *  i.next().toString() === pitsi23.toString();
     *  i.hasNext() === false;
     *  pankkikortti.lisaaDebitti(pitsi23);
     *  pankkikortti.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
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
     * Talentaa tiedostoon
     * @throws SailoException ei kye tallentamaan 
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

    /**
     * antaa lukumäärän
     * @return lkm
     */
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
    
    /**
     * Poistaa valitun kortin
     * @param debit poistettava debit
     * @return tosi jos löytyi poistettava tietue 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Pankkikortti pankkikortti = new Pankkikortti();
     *  Debit pitsi21 = new Debit(); pitsi21.vastaaDebit(2);
     *  Debit pitsi11 = new Debit(); pitsi11.vastaaDebit(1);
     *  Debit pitsi22 = new Debit(); pitsi22.vastaaDebit(2); 
     *  Debit pitsi12 = new Debit(); pitsi12.vastaaDebit(1); 
     *  Debit pitsi23 = new Debit(); pitsi23.vastaaDebit(2); 
     *  pankkikortti.lisaaDebitti(pitsi21);
     *  pankkikortti.lisaaDebitti(pitsi11);
     *  pankkikortti.lisaaDebitti(pitsi22);
     *  pankkikortti.lisaaDebitti(pitsi12);
     *  pankkikortti.poistaDebit(pitsi23) === false ; pankkikortti.getLkm() === 4;
     *  pankkikortti.poistaDebit(pitsi11) === true;   pankkikortti.getLkm() === 3;
     *  List<Debit> h = pankkikortti.annaDebitti(1);
     *  h.size() === 1; 
     *  h.get(0) === pitsi12;
     * </pre>
     */
    public boolean poistaDebit(Debit debit) {
        boolean ret = debitLista.remove(debit);
        if (ret) muutettu = true;
        return ret;
    }
    
    /**
     * Poistaa kaikki tietyn tietyn jäsenen harrastukset
     * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
     * @return montako poistettiin 
     * @example
     * <pre name="test">
     *  Pankkikortti pankkikortti = new Pankkikortti();
     *  Debit pitsi21 = new Debit(); pitsi21.vastaaDebit(2);
     *  Debit pitsi11 = new Debit(); pitsi11.vastaaDebit(1);
     *  Debit pitsi22 = new Debit(); pitsi22.vastaaDebit(2); 
     *  Debit pitsi12 = new Debit(); pitsi12.vastaaDebit(1); 
     *  Debit pitsi23 = new Debit(); pitsi23.vastaaDebit(2); 
     *  harrasteet.lisaaDebitti(pitsi21);
     *  harrasteet.lisaaDebitti(pitsi11);
     *  harrasteet.lisaaDebitti(pitsi22);
     *  harrasteet.lisaaDebitti(pitsi12);
     *  harrasteet.lisaaDebitti(pitsi23);
     *  harrasteet.poistaAsiakkaanDebit(2) === 3;  pankkikortti.getLkm() === 2;
     *  harrasteet.poistaAsiakkaanDebit(3) === 0;  pankkikortti.getLkm() === 2;
     *  List<Harrastus> h = pankkikortti.annaDebitti(2);
     *  h.size() === 0; 
     *  h = pankkikortti.annaDebitti(1);
     *  h.get(0) === pitsi11;
     *  h.get(1) === pitsi12;
     * </pre>
     */
    public int poistaAsiakkaanDebit(int tunnusNro) {
        int n = 0;
        for (Iterator<Debit> it = debitLista.iterator(); it.hasNext();) {
            Debit deb = it.next();
            if ( deb.getAsiakasNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    /**
     * @param credit Poistettava luottokortti.
     * @return true, jos kortti löytyi ja poistettiin, muuten false.
     * @example
     * <pre name="test">
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Credit cred = new Credit(); cred.vastaaCredit(2);
     * pankkikortti.lisaaKreditti(cred);
     * pankkikortti.poistaCredit(cred) === true;
     * pankkikortti.annaKreditti(cred.getAsiakasNro()).size() === 0;
     * </pre>
     */
    public boolean poistaCredit(Credit credit) {
        boolean ret = creditLista.remove(credit);
        if (ret) muutettu = true;
        return ret;
    }
    
    /**
     * @param tunnusNro Asiakkaan tunnusnumero, jonka kortit poistetaan.
     * @return Palauttaa poistettujen luottokorttien lukumäärän.
     * @example
     * <pre name="test">
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Credit cred1 = new Credit(); cred1.vastaaCredit(2);
     * Credit cred2 = new Credit(); cred2.vastaaCredit(2);
     * pankkikortti.lisaaKreditti(cred1);
     * pankkikortti.lisaaKreditti(cred2);
     * pankkikortti.poistaAsiakkaanCredit(2) === 2;
     * pankkikortti.annaKreditti(2).size() === 0;
     * </pre>
     */
    public int poistaAsiakkaanCredit(int tunnusNro) {
        int n = 0;
        for (Iterator<Credit> it = creditLista.iterator(); it.hasNext();) {
            Credit cred = it.next();
            if ( cred.getAsiakasNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    /**
     * @param yhdistelma Poistettava yhdistelmäkortti.
     * @return true, jos kortti löytyi ja poistettiin, muuten false.
     * @example
     * <pre name="test">
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Yhdistelmä yhd = new Yhdistelmä(); yhd.vastaaYhdistelmä(2);
     * pankkikortti.lisaaYhdistelma(yhd);
     * pankkikortti.poistaYhdistelma(yhd) === true;
     * pankkikortti.annaYhdistelma(yhd.getAsiakasNro()).size() === 0;
     * </pre>
     */
    public boolean poistaYhdistelma(Yhdistelmä yhdistelma) {
        boolean ret = yhdistelmaLista.remove(yhdistelma);
        if (ret) muutettu = true;
        return ret;
    }
    
    /**
     * @param tunnusNro Asiakkaan tunnusnumero, jonka kortit poistetaan.
     * @return Palauttaa poistettujen yhdistelmäkorttien lukumäärän.
     * @example
     * <pre name="test">
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Yhdistelmä yhd1 = new Yhdistelmä(); yhd1.vastaaYhdistelmä(2);
     * Yhdistelmä yhd2 = new Yhdistelmä(); yhd2.vastaaYhdistelmä(2);
     * pankkikortti.lisaaYhdistelma(yhd1);
     * pankkikortti.lisaaYhdistelma(yhd2);
     * pankkikortti.poistaAsiakkaanYhdistelma(2) === 2;
     * pankkikortti.annaYhdistelma(2).size() === 0;
     * </pre>
     */
    public int poistaAsiakkaanYhdistelma(int tunnusNro) {
        int n = 0;
        for (Iterator<Yhdistelmä> it = yhdistelmaLista.iterator(); it.hasNext();) {
            Yhdistelmä yhd = it.next();
            if ( yhd.getAsiakasNro() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }

    /**
     * Korvaa kortin tietorakenteessa. Ottaa kortin omistukseensa.
     * @param deb lisättävän kortin viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Debit deb1 = new Debit(), deb2 = new Debit();
     * deb1.rekisteroi(); deb2.rekisteroi();
     * pankkikortti.getLkm() === 0;
     * pankkikortti.korvaaTaiLisaaDebit(har1); pankkikortti.getLkm() === 1;
     * pankkikortti.korvaaTaiLisaaDebit(har2); pankkikortti.getLkm() === 2;
     * Debit deb3 = deb1.clone();
     * deb3.aseta(2,"kkk");
     * Iterator<Debit> deb1=pankkikortti.iterator();
     * i2.next() === deb1;
     * pankkikortti.korvaaTaiLisaaDebut(har3); pankkikortti.getLkm() === 2;
     * i2=pankkikortti.iterator();
     * Pankkikortti h = i2.next();
     * h === deb3;
     * h == deb3 === true;
     * h == deb1 === false;
     * </pre>
     */
    public void korvaaTaiLisaaDebit(Debit deb) throws SailoException {
        int id = deb.getTunnusNro();
        for (int i = 0; i < getLkm(); i++) {
            if (debitLista.get(i).getTunnusNro() == id) {
                debitLista.set(i, deb);
                muutettu = true;
                return;
            }
        }
        lisaaDebitti(deb);
    }

    /**
     * @param cred Korvattava tai lisättävä luottokortti.
     * @throws SailoException Jos tietorakenne on jo täynnä.
     * @example
     * <pre name="test">
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Credit cred1 = new Credit(1); cred1.vastaaCredit(2);
     * Credit cred2 = new Credit(1); cred2.vastaaCredit(3);
     * pankkikortti.korvaaTaiLisaaCredit(cred1);
     * pankkikortti.korvaaTaiLisaaCredit(cred2);
     * // Jos luottokortti, jolla on sama tunnusNro, löytyy, se korvataan.
     * </pre>
     */
    public void korvaaTaiLisaaCredit(Credit cred) throws SailoException {
        int id = cred.getTunnusNro();
        for (int i = 0; i < getLkm(); i++) {
            if (creditLista.get(i).getTunnusNro() == id) {
                creditLista.set(i, cred);
                muutettu = true;
                return;
            }
        }
        lisaaKreditti(cred);        
    }

    /**
     * @param yhd Korvattava tai lisättävä yhdistelmäkortti.
     * @throws SailoException Jos tietorakenne on jo täynnä.
     * @example
     * <pre name="test">
     * Pankkikortti pankkikortti = new Pankkikortti();
     * Yhdistelmä yhd1 = new Yhdistelmä(1); yhd1.vastaaYhdistelmä(2);
     * Yhdistelmä yhd2 = new Yhdistelmä(1); yhd2.vastaaYhdistelmä(3);
     * pankkikortti.korvaaTaiLisaaYhdistelma(yhd1);
     * pankkikortti.korvaaTaiLisaaYhdistelma(yhd2);
     * // Jos yhdistelmäkortti, jolla on sama tunnusNro, löytyy, se korvataan.
     * </pre>
     */
    public void korvaaTaiLisaaYhdistelma(Yhdistelmä yhd) throws SailoException {
        int id = yhd.getTunnusNro();
        for (int i = 0; i < getLkm(); i++) {
            if (yhdistelmaLista.get(i).getTunnusNro() == id) {
                yhdistelmaLista.set(i, yhd);
                muutettu = true;
                return;
            }
        }
        lisaaYhdistelma(yhd);
    }
    
}