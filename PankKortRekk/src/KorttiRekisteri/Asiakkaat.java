package KorttiRekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/* AgoBankin asiakkaat, joka osaa mm. lisätä uuden jäsenen (ei vielä)
  * 
  * 
  * 
  */

public class Asiakkaat implements Iterable <Asiakas> {
    
    private static final int    MAX_ASIAKKAAT = 20;
    private boolean             muutettu = false;
    private int                 lkm = 0;
    private String              kokoNimi = "";
    private String              tiedostonPerusNimi = "asiakkaat";
    private Asiakas             alkiot[] = new Asiakas[MAX_ASIAKKAAT];
    
    public Asiakkaat() {
        // Jää tyhjäksi
    }
    
    /*
     * Lisää uuden asiakkaan rekisteriin
     * Heittää virheen jos asiakkaita on liikaa
     */
    
    public void lisaa(Asiakas asiakas) throws SailoException {
        if ( lkm >= alkiot.length ) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = asiakas;
        lkm++;
        muutettu = true;
    }

    
    // Palauttaa jäsenen indeksin perusteella
    
    public Asiakas anna(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    
    /**
     * @param tied aa
     * @throws SailoException aa
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
     * Tallentaa asiakkaan tiedostoon
     * @throws SailoException aa
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


    
    public String getKokoNimi() {
        return kokoNimi;
    }

    
    public int getLkm() {
        return lkm;
    }
    
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
            
            try {
                asiakkaat.lisaa(erik);
                asiakkaat.lisaa(erik2);
                
                System.out.println("=================== Asiakkaat testi =======================");
                
                for (int i = 0; i < asiakkaat.getLkm(); i++) {
                    Asiakas asiakas = asiakkaat.anna(i);
                    System.out.println("Asiakasnumero: " + i);
                    asiakas.tulosta(System.out);
                }
            } catch (SailoException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public Iterator<Asiakas> iterator() {
        return new AsiakasIterator();
    }

    public Collection<Asiakas> etsi(String hakuehto, int k) {
        Collection<Asiakas> loytyneet = new ArrayList<Asiakas>(); 
        for (int i = 0; i < lkm; i++) {  // lkm keeps track of the number of customers
            Asiakas asiakas = alkiot[i];  // Get the current customer
            loytyneet.add(asiakas);  // Add to the result collection
        }
        
        return loytyneet; 
    }

    /**
     * Selitin tän paskan jo pankissa.
     * @param asiakas aa
     * @throws SailoException aa
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
}