package fxPankki;

import java.io.File;
import java.util.Collection;
import java.util.List;

import KorttiRekisteri.SailoException;

/**
 * Kerho-luokka, joka huolehtii jäsenistöstä. Pääosin kaikki metodit
 * ovat vain "välittäjämetodeja" jäsenistöön.
 * 
 * Tämä versio käyttää samaa tiedostonimi- ja oletustiedostojen luontilogiikkaa
 * kuin Asiakkaat- ja Pankkikortti-luokissanne, jolloin sekä "nimet" että "asiakkaat"
 * -tiedostot (ja niiden varakopiot) luodaan ja päivitetään oikein.
 * 
 * @author OMISTAJA
 * @version 10 Mar 2025
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import kerho.SailoException;
 *  private Pankki pankki;
 *  private Asiakas aku1;
 *  private Asiakas aku2;
 *  private int jid1;
 *  private int jid2;
 *  private Debit pitsi21;
 *  private Credit pitsi11;
 *  private Yhdistelmä pitsi22; 
 * </pre>
*/
public class Pankki {
    private Asiakkaat asiakkaat = new Asiakkaat();
    private Pankkikortti pankkikortti = new Pankkikortti();
        
    /**
     * @return pankin asiakasmäärän
     */
    public int getAsiakkaat() {
        return asiakkaat.getLkm();
    }
    
    /**
     * @param asiakas jokapoistetaan
     * @return montako asiakasta poistettiin
     * @example
     * <pre name="test">
     *   pankki.etsi("*",0).size() === 2;
     *   pankki.annaDebit(aku1).size() === 2;
     *   pankki.poista(aku1) === 1;
     *   pankki.etsi("*",0).size() === 1;
     *   pankki.annaDebit(aku1).size() === 0;
     *   pankki.annaDebit(aku2).size() === 3;
     * </pre>
     */
    public int poista(Asiakas asiakas) {
        if ( asiakas == null ) return 0;
        int ret = asiakkaat.poista(asiakas.getTunnusNro()); 
        pankkikortti.poistaAsiakkaanDebit(asiakas.getTunnusNro()); 
        return ret; 
    }
    
    
    /** 
     * @param debit poistettava Debit 
     * @example
     * <pre name="test">
     *   pankki.annaDebit(aku1).size() === 2;
     *   pankki.poistaDebit(pitsi21);
     *   pankki.annaDebit(aku1).size() === 1;
     */ 
    public void poistaDebit(Debit debit) { 
        pankkikortti.poistaDebit(debit); 
    } 
    
    /** 
     * @param credit poistettava Credit 
     * @example
     * <pre name="test">
     *   pankki.annaCredit(aku1).size() === 2;
     *   pankki.poistaCredit(pitsi11);
     *   pankki.annaCredit(aku1).size() === 1;
     */ 
    public void poistaCredit(Credit credit) { 
        pankkikortti.poistaCredit(credit); 
    }
    
    /** 
     * @param yhdistelma poistettava Yhdistelmä 
     * @example
     * <pre name="test">
     *   pankki.annaYhdistelma(aku1).size() === 2;
     *   pankki.poistaYhdistelma(pitsi22);
     *   pankki.annaYhdistelma(aku1).size() === 1;
     */ 
    public void poistaYhdistelma(Yhdistelmä yhdistelma) { 
        pankkikortti.poistaYhdistelma(yhdistelma); 
    }
    
    /** 
     * @param asiakas lisätäävän asiakkaan viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  pankki.etsi("*",0).size() === 2;
     *  pankki.korvaaTaiLisaa(aku1);
     *  pankki.etsi("*",0).size() === 2;
     * </pre>
     */ 

    public void korvaaTaiLisaa(Asiakas asiakas) throws SailoException {
        asiakkaat.korvaaTaiLisaa(asiakas);
    }
    
    /**
     * Korvaa debitin tietokannassa, ottaa debitin omistukseensa.
     * Etitään samalla tunnusNro:lla oleva debit.
     * Jos ei löydy niin lisätään uutena debittinä.
     * @param deb lisättävän debitin viite
     * @throws SailoException aa
     */
    public void korvaaTaiLisaaDebit(Debit deb) throws SailoException {
        pankkikortti.korvaaTaiLisaaDebit(deb);
        
    }

    /**
     * Korvaa creditin tietokannassa, ottaa creditin omistukseensa.
     * Etitään samalla tunnusNro:lla oleva credit.
     * Jos ei löydy niin lisätään uutena credittinä. 
     * @param cred lisättävän creditin viite
     * @throws SailoException aa 
     */
    public void korvaaTaiLisaaCredit(Credit cred) throws SailoException {
        pankkikortti.korvaaTaiLisaaCredit(cred);       
    }

    /**
     * Korvaa yhdistelmäkortin tietokannassa, ottaa yhdistelmäkortin omistukseensa.
     * Etitään samalla tunnusNro:lla oleva yhdistelmäkortti.
     * Jos ei löydy niin lisätään uutena yhdistelmäkorttina.  
     * @param yhd lisättävän yhdistelmäkortin viite
     * @throws SailoException aa 
     */
    public void korvaaTaiLisaaYhdistelma(Yhdistelmä yhd) throws SailoException {
        pankkikortti.korvaaTaiLisaaYhdistelma(yhd);
    }
 
    /**
     * @param asiakas lisättävä asiakas
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  pankki.etsi("*",0).size() === 2;
     *  pankki.lisaa(aku1);
     *  pankki.etsi("*",0).size() === 3;
     */  
    public void lisaa(Asiakas asiakas) throws SailoException {
        asiakkaat.lisaa(asiakas);
    }
    
    /**
     * Lisää pankkiin uuden debit-kortin.
     * @param debit Debit-luokasta napattu kortti.
     */
    public void lisaaDebit(Debit debit) {
        pankkikortti.lisaaDebitti(debit);
    }
    
    /**
     * Lisää pankkiin uuden luottokortin.
     * @param credit Credit-luokasta napattu kortti.
     */
    public void lisaaCredit(Credit credit) {
        pankkikortti.lisaaKreditti(credit);
    }
    
    /**
     * Lisää pankkiin uuden yhdistelmäkortin.
     * @param yhdistelma Yhdistelmä-luokasta napattu kortti.
     */
    public void lisaaYhdistelma(Yhdistelmä yhdistelma) {
        pankkikortti.lisaaYhdistelma(yhdistelma);
    }
    
    /**
     * @param asiakas asiakas jolle debit haetaan
     * @return tietorakenne jossa viiteet löydetteyihin kortteihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     *  Asiakas aku3 = new Asiakas();
     *  aku3.rekisteroi();
     *  pankki.lisaa(aku3);
     *  
     *  List<Debit> loytyneet;
     *  loytyneet = pankki.annaDebit(aku3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kerho.annaDebit(aku1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi21 === true;
     *  loytyneet.get(1) == pitsi21 === true;
     *  loytyneet = kerho.annaDebit(aku2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == pitsi21 === true;
     * </pre> 
     */
    public List<Debit> annaDebit(Asiakas asiakas) {
        return pankkikortti.annaDebitti(asiakas.getTunnusNro());
    }
    
    /**
     * @param asiakas asiakas jolle debit haetaan
     * @return tietorakenne jossa viiteet löydetteyihin kortteihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     *  Asiakas aku3 = new Asiakas();
     *  aku3.rekisteroi();
     *  pankki.lisaa(aku3);
     *  
     *  List<Credit> loytyneet;
     *  loytyneet = pankki.annaCredit(aku3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kerho.annaCredit(aku1);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet = kerho.annaCredit(aku2);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     * </pre>
     */
    public List<Credit> annaCredit(Asiakas asiakas) {
        return pankkikortti.annaKreditti(asiakas.getTunnusNro());
    }
    
    /**
     * @param asiakas asiakas
     * @return tietorakenne, jossa viiteet löydettyihin yhdistelmäkortteihin
     * <pre name="test">
     * #import java.util.*;
     *  Asiakas aku3 = new Asiakas();
     *  aku3.rekisteroi();
     *  pankki.lisaa(aku3);
     *  
     *  List<Yhdistelmä> loytyneet;
     *  loytyneet = pankki.annaYhdistelma(aku3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kerho.annaYhdistelma(aku1);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == pitsi22 === true;
     *  loytyneet = kerho.annaYhdistelma(aku2);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(1) == pitsi22 === true;
     * </pre>
     */
    public List<Yhdistelmä> annaYhdistelma(Asiakas asiakas) {
        return pankkikortti.annaYhdistelma(asiakas.getTunnusNro());
    }

    /**
     * Palauttaa asiakkaan tietyssä indeksissä.
     * @param i indeksi
     * @return asiakkaan tietystä indeksistä
     * @throws IndexOutOfBoundsException jos indeksi on listan ulkopuolella
     */
    public Asiakas annaAsiakas(int i) throws IndexOutOfBoundsException {
        return asiakkaat.anna(i);
    }
    
    /**
     * Lukee pankin tiedot tiedostosta
     * @param nimi jota käytetään lukemisessa (hakemiston nimi)
     * @throws SailoException jos lukeminen epäonnistuu
     *
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     * String hakemisto = "testipankki";
     * File dir = new File(hakemisto);
     * File fAsiakkaat  = new File(hakemisto+"/asiakkaat.dat");
     * File fKortit = new File(hakemisto+"/kortit.dat");
     * dir.mkdir();  
     * fAsiakkaat.delete();
     * fKortit.delete();
     * 
     * pankki = new Pankki(); // tiedostoja ei ole, tulee poikkeus 
     * pankki.lueTiedostosta(hakemisto); #THROWS SailoException
     * 
     * alustaPankki();
     * pankki.setTiedosto(hakemisto); // nimi annettava koska uusi poisti sen
     * pankki.tallenna(); 
     * 
     * pankki = new Pankki();
     * pankki.lueTiedostosta(hakemisto);
     * Collection<Asiakas> kaikki = pankki.etsi("", -1); 
     * Iterator<Asiakas> it = kaikki.iterator();
     * it.next() === aku1;
     * it.next() === aku2;
     * it.hasNext() === false;
     * List<Debit> loytyneet = pankki.annaDebit(aku1);
     * Iterator<Debit> ih = loytyneet.iterator();
     * ih.next() === pitsi11;
     * ih.next() === pitsi12;
     * ih.hasNext() === false;
     * 
     * pankki.lisaa(aku2);
     * pankki.lisaa(pitsi21);
     * pankki.tallenna(); // tekee molemmista .bak
     * fAsiakkaat.delete()  === true;
     * fKortit.delete() === true;
     * File fbak = new File(hakemisto+"/asiakkaat.bak");
     * File fcbak = new File(hakemisto+"/kortit.bak");
     * fbak.delete() === true;
     * fcbak.delete() === true;
     * dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        // Luo uudet tietorakenteet tyhjentääkseen mahdollisen edellisen datan
        asiakkaat = new Asiakkaat();
        pankkikortti = new Pankkikortti();

        // Aseta tiedostopolut
        setTiedosto(nimi);
        try {
            asiakkaat.lueTiedostosta();
        } catch (Exception e) {
            System.out.println("Error reading asiakkaat file: " + e.getMessage());
        }

        try {
            pankkikortti.lueTiedostosta();
        } catch (Exception e) {
            System.out.println("Error reading pankkikortti file: " + e.getMessage());
        }
    }
    
    /**
     * @param asiakas asiakas
     * @return true jos hetu, muuten ei
     */
    public boolean tarkistaOnkoHetu(Asiakas asiakas) {
        if ( asiakas == null ) return false;
        
        int k = asiakas.ekaKentta() + 1;
        String hetu = asiakas.anna(k);
        if ( hetu.length() <= 6 ) return false;
        
        Collection<Asiakas> loytyneet = etsi(hetu, k);
        if ( loytyneet.size() > 1 ) return true;
        
        if ( loytyneet.size() < 1 ) return false;
        
        Asiakas asiakas2 = loytyneet.iterator().next();
        
        if ( asiakas.getTunnusNro() != asiakas2.getTunnusNro() ) return true;
        return false;
    }

    /**
     * Tallentaa pankin tiedot tiedostoon.
     * Ensiksi tallennetaan asiakkaat, sitten pankkikortit.
     * @throws SailoException jos tallentamisessa tulee ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            asiakkaat.tallenna();
            pankkikortti.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }
        if (!"".equals(virhe))
            throw new SailoException(virhe);
    }

    /**
     * Asettaa tiedostopolut molemmille osioille.
     * Luo tarvittaessa hakemiston.
     * @param nimi Hakemiston nimi, esim. "AgoBank"
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty())
            hakemistonNimi = nimi + "/";
        // Asiakkaiden tiedostonimi: "hakemistonNimi" + "nimet"
        asiakkaat.setTiedostonPerusNimi(hakemistonNimi + "asiakkaat");
        // Pankkikorttien tiedostonimi: "hakemistonNimi" + "asiakkaat"
        pankkikortti.setTiedostonPerusNimi(hakemistonNimi + "kortit");
    }

    /** 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @example 
     * <pre name="test">
     *   alustaKerho();
     *   Asiakas asiakas3 = new Asiakas(); asiakas3.rekisteroi();
     *   asiakas3.aseta(1,"Susi Sepe");
     *   pankki.lisaa(asiakas3);
     *   Collection<Asiakas> loytyneet = pankki.etsi("*Susi*", 1);
     *   loytyneet.size() === 1;
     *   Iterator<Asiakas> it = loytyneet.iterator();
     *   it.next() == asiakas3 === true; 
     * </pre>
     */
    public Collection<Asiakas> etsi(String hakuehto, int k) { 
        return asiakkaat.etsi(hakuehto, k); 
    }

    /**
     * Testiohjelma.
     * Luodaan pankki, lisätään asiakkaita ja kortteja, ja tallennetaan tiedostoon.
     * @param args aa
     */
    public static void main(String[] args) {
        Pankki pankki = new Pankki();

        try {
            pankki.lueTiedostosta("AgoBank");

            Asiakas aku1 = new Asiakas(), aku2 = new Asiakas();
            aku1.rekisteroi();
            aku1.vastaaErik();
            aku2.rekisteroi();
            aku2.vastaaErik();

            pankki.lisaa(aku1);
            pankki.lisaa(aku2);
            int id1 = aku1.getTunnusNro();
            int id2 = aku2.getTunnusNro();
            Debit pitsi11 = new Debit(id1);
            pitsi11.vastaaDebit(id1);
            pankki.lisaaDebit(pitsi11);
            Debit pitsi21 = new Debit(id2);
            pitsi21.vastaaDebit(id2);
            pankki.lisaaDebit(pitsi21);

            System.out.println("============= Kerhon testi =================");
            Collection<Asiakas> asiakkaat = pankki.etsi("", -1);
            int i = 0;
            for (Asiakas asiakas : asiakkaat) {
                System.out.println("Jäsen paikassa: " + i);
                asiakas.tulosta(System.out);
                List<Debit> loytyneet = pankki.annaDebit(asiakas);
                for (Debit deb : loytyneet) {
                    deb.tulosta(System.out);
                }
                i++;
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            pankki.tallenna();
        } catch (SailoException e) {
            e.printStackTrace();
        }
    }
}
