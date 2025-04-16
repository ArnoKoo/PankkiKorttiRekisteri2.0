package fxPankki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import KorttiRekisteri.SailoException;
import fi.jyu.mit.ohj2.WildChars;

/**
 * AgoBankin asiakkaat, joka osaa mm. lisätä uuden jäsenen
 * @author OMISTAJA
 * @version 16 Apr 2025
 *
 */
public class Asiakkaat implements Iterable <Asiakas> {
    
    private static final int    MAX_ASIAKKAAT = 20;
    private boolean             muutettu = false;
    private int                 lkm = 0;
    private String              kokoNimi = "";
    private String              tiedostonPerusNimi = "asiakkaat";
    private Asiakas             alkiot[] = new Asiakas[MAX_ASIAKKAAT];
    
    /**
     * no täl ei tehty sit mitää
     */
    public Asiakkaat() {
        // Jää tyhjäksi
    }
    
    /**
     * Lisää uuden jäsenen tietorakenteeseen.  Ottaa jäsenen omistukseensa.
     * @param asiakas lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Asiakkaat asiakkaat = new Asiakkaat();
     * Asiakas aku1 = new Asiakas(), aku2 = new Asiakas();
     * asiakkaat.getLkm() === 0;
     * asiakkaat.lisaa(aku1); asiakkaat.getLkm() === 1;
     * asiakkaat.lisaa(aku2); asiakkaat.getLkm() === 2;
     * asiakkaat.lisaa(aku1); asiakkaat.getLkm() === 3;
     * Iterator<Asiakas> it = asiakkaat.iterator(); 
     * it.next() === aku1;
     * it.next() === aku2; 
     * it.next() === aku1;  
     * asiakkaat.lisaa(aku1); asiakkaat.getLkm() === 4;
     * asiakkaat.lisaa(aku1); asiakkaat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Asiakas asiakas) {
    	if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
        alkiot[lkm] = asiakas;
        lkm++;
        muutettu = true;
    }

    

    /**
     * Palauttaa asiakkaan indeksin perusteella
     * @param i indeksi
     * @return asiakkaan indeksin perusteella
     * @throws IndexOutOfBoundsException jos laiton
     */
    public Asiakas anna(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    
    /**
     * Lukee asiakkaan tiedostosta. 
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Asiakkaat asiakkaat = new Asiakkaat();
     *  Asiakas aku1 = new Asiakas(), aku2 = new Asiakas();
     *  aku1.vastaaErik();
     *  aku2.vastaaErik();
     *  String hakemisto = "testiPankki";
     *  String tiedNimi = hakemisto+"/kortit";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  asiakkaat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  asiakkaat.lisaa(aku1);
     *  asiakkaat.lisaa(aku2);
     *  asiakkaat.tallenna();
     *  asiakkaat = new Asiakkaat();
     *  asiakkaat.lueTiedostosta(tiedNimi);
     *  Iterator<Asiakas> i = asiakkaat.iterator();
     *  i.next() === aku1;
     *  i.next() === aku2;
     *  i.hasNext() === false;
     *  asiakkaat.lisaa(aku2);
     *  asiakkaat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);

        //Debuggausta
        String fileName = getTiedostonNimi();
        File file = new File(fileName);
        System.out.println("DEBUG: Luetaan tiedostoa: " + file.getAbsolutePath());
        System.out.println("DEBUG: Olemassa? " + file.exists());

        try (BufferedReader fi = new BufferedReader(new FileReader(file))) {
            kokoNimi = fi.readLine();
            if (kokoNimi == null) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if (rivi == null) throw new SailoException("Maksimikoko puuttuu");

            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Asiakas asiakas = new Asiakas();
                asiakas.parse(rivi);
                lisaa(asiakas);
            }
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e) {
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
     * Tallentaa asiakkaan tiedostoon.  
     * Tiedoston muoto:
     * <pre>
     * 20
     * 1|Arska Korhonen|090805-123H|Taitoniekantie 9J|40740|Jyväskylä|0445566667|arska@gmail.com
     * 2|Dani Lobko|010101-890D|Jokujokutie|49850|Jyväskylä|1234567891|Dani@gmail.com
     * </pre>
     * @throws SailoException jos talletus epäonnistuu
     */

    public void tallenna() throws SailoException {
        if (!muutettu) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Asiakas asiakas : this) {
                fo.println(asiakas.toString());
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }


    
    /**
     * @return kokoNimi
     */
    public String getKokoNimi() {
        return kokoNimi;
    }

    
    /**
     * @return lkm
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @return tiedostonPerusNimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    
    /**
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Asiakkaat asiakkaat = new Asiakkaat();
     * Asiakas aku1 = new Asiakas(), aku2 = new Asiakas();
     * aku1.rekisteroi(); aku2.rekisteroi();
     *
     * asiakkaat.lisaa(aku1); 
     * asiakkaat.lisaa(aku2); 
     * asiakkaat.lisaa(aku1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Asiakas asiakas:asiakkaat)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+asiakas.getTunnusNro());           
     * 
     * String tulos = " " + aku1.getTunnusNro() + " " + aku2.getTunnusNro() + " " + aku1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Asiakas>  i=asiakkaat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Asiakas asiakas = i.next();
     *   ids.append(" "+ asiakas.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Asiakas>  i=asiakkaat.iterator();
     * i.next() == aku1  === true;
     * i.next() == aku2  === true;
     * i.next() == aku1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */

    public class AsiakasIterator implements Iterator<Asiakas> {
        private int kohdalla = 0;
        
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        
        @Override
        public Asiakas next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }
        
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
        
        /**
         * @return asiakasIterator
         */
        public Iterator<Asiakas> iterator() {
            return new AsiakasIterator();
        }


        
        /**
         * @param args ei tee mitään
         */
        public static void main(String[] args) {
            Asiakkaat asiakkaat = new Asiakkaat();      
            Asiakas erik = new Asiakas(), erik2 = new Asiakas();
            
            erik.rekisteroi();
            erik.vastaaErik();
            
            erik2.rekisteroi();
            erik2.vastaaErik();
            
            asiakkaat.lisaa(erik);
            asiakkaat.lisaa(erik2);
            
            System.out.println("=================== Asiakkaat testi =======================");
            
            for (int i = 0; i < asiakkaat.getLkm(); i++) {
                Asiakas asiakas = asiakkaat.anna(i);
                System.out.println("Asiakasnumero: " + i);
                asiakas.tulosta(System.out);
            }
        }
    }

    @Override
    public Iterator<Asiakas> iterator() {
        return new AsiakasIterator();
    }
    
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Jasenet jasenet = new Jasenet(); 
     *   Jasen jasen1 = new Jasen(); jasen1.parse("1|Ankka Aku|030201-115H|Paratiisitie 13|"); 
     *   Jasen jasen2 = new Jasen(); jasen2.parse("2|Ankka Tupu||030552-123B|"); 
     *   Jasen jasen3 = new Jasen(); jasen3.parse("3|Susi Sepe|121237-121V||131313|Perämetsä"); 
     *   Jasen jasen4 = new Jasen(); jasen4.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
     *   Jasen jasen5 = new Jasen(); jasen5.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
     *   jasenet.lisaa(jasen1); jasenet.lisaa(jasen2); jasenet.lisaa(jasen3); jasenet.lisaa(jasen4); jasenet.lisaa(jasen5);
     *   List<Jasen> loytyneet;  
     *   loytyneet = (List<Jasen>)jasenet.etsi("*s*",1);  
     *   loytyneet.size() === 2;  
     *   loytyneet.get(0) == jasen4 === true;  
     *   loytyneet.get(1) == jasen3 === true;  
     *     
     *   loytyneet = (List<Jasen>)jasenet.etsi("*7-*",2);  
     *   loytyneet.size() === 2;  
     *   loytyneet.get(0) == jasen5 === true;  
     *   loytyneet.get(1) == jasen3 === true; 
     *     
     *   loytyneet = (List<Jasen>)jasenet.etsi(null,-1);  
     *   loytyneet.size() === 5;  
     * </pre> 
     */
    public Collection<Asiakas> etsi(String hakuehto, int k) {
    	String ehto = "*"; 
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
        if ( hk < 0 ) hk = 1;
    	
        List<Asiakas> loytyneet = new ArrayList<Asiakas>(); 
        for (Asiakas asiakas : this) { 
            if (WildChars.onkoSamat(asiakas.anna(hk), ehto)) loytyneet.add(asiakas);   
        } 
        Collections.sort(loytyneet, new Asiakas.Vertailija(hk)); 
        return loytyneet; 
    }

    /**
     * @param asiakas lisätäävän asiakas viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Asiakkaat asiakkaat = new Asiakkaat();
     * Asiakas aku1 = new Asiakas(), aku2 = new Asiakas();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * asiakkaat.getLkm() === 0;
     * asiakkaat.korvaaTaiLisaa(aku1); asiakkaat.getLkm() === 1;
     * asiakkaat.korvaaTaiLisaa(aku2); asiakkaat.getLkm() === 2;
     * Asiakas aku3 = aku1.clone();
     * aku3.aseta(3,"kkk");
     * Iterator<Asiakas> it = asiakkaat.iterator();
     * it.next() == aku1 === true;
     * asiakkaat.korvaaTaiLisaa(aku3); asiakkaat.getLkm() === 2;
     * it = asiakkaat.iterator();
     * Asiakas j0 = it.next();
     * j0 === aku3;
     * j0 == aku3 === true;
     * j0 == aku1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Asiakas asiakas) throws SailoException {
        int id = asiakas.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getTunnusNro() == id) {
                alkiot[i] = asiakas;
                muutettu = true;
                return;
            }
        }
        lisaa(asiakas);
    }

    /** 
     * @param id poistettavan asiakkaan tunnusnumero 
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Asiakkaat asiakkaat = new Asiakkaat(); 
     * Asiakas aku1 = new Asiakas(), aku2 = new Asiakas(), aku3 = new Asiakas(); 
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
     * int id1 = aku1.getTunnusNro(); 
     * asiakkaat.lisaa(aku1); asiakkaat.lisaa(aku2); asiakkaat.lisaa(aku3); 
     * asiakkaat.poista(id1+1) === 1; 
     * asiakkaat.annaId(id1+1) === null; asiakkaat.getLkm() === 2; 
     * asiakkaat.poista(id1) === 1; asiakkaat.getLkm() === 1; 
     * asiakkaat.poista(id1+3) === 0; asiakkaat.getLkm() === 1; 
     * </pre> 
     *  
     */ 
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    }
    
    /** 
     * Etsii jäsenen id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return löytyneen jäsenen indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Asiakkaat asiakkaat = new Asiakkaat(); 
     * Asiakas aku1 = new Jasen(), aku2 = new Asiakas(), aku3 = new Asiakas(); 
     * aku1.rekisteroi(); aku2.rekisteroi(); aku3.rekisteroi(); 
     * int id1 = aku1.getTunnusNro(); 
     * asiakkaat.lisaa(aku1); asiakkaat.lisaa(aku2); asiakkaat.lisaa(aku3); 
     * asiakkaat.etsiId(id1+1) === 1; 
     * asiakkaat.etsiId(id1+2) === 2; 
     * </pre> 
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    }
}