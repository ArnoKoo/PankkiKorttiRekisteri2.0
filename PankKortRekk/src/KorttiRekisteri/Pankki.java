package KorttiRekisteri;

import java.io.File;
import java.util.Collection;
import java.util.List;

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
     * Poistaa asiakkaan ja korteista ne joilla on nro. Kesken.
     * @param nro viitenumero, jonka mukaan poistetaan
     * @return montako jäsentä poistettiin
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }
 
    /**
     * Lisää pankkiin uuden asiakkaan, SailoException varmistaa ettei liikaa asiakkaita
     * @param asiakas on asiakas
     * @throws SailoException jos liikaa
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
     * @param asiakas asiakas
     * @return tietorakenne, jossa viiteet löydettyihin debit-kortteihin
     */
    public List<Debit> annaDebit(Asiakas asiakas) {
        return pankkikortti.annaDebitti(asiakas.getTunnusNro());
    }
    
    /**
     * @param asiakas asiakas
     * @return tietorakenne, jossa viiteet löydettyihin luottokortteihin
     */
    public List<Credit> annaCredit(Asiakas asiakas) {
        return pankkikortti.annaKreditti(asiakas.getTunnusNro());
    }
    
    /**
     * @param asiakas asiakas
     * @return tietorakenne, jossa viiteet löydettyihin yhdistelmäkortteihin
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
     * Lukee pankin tiedot tiedostosta.
     * Luodaan uudet Asiakkaat- ja Pankkikortti-oliot, asetetaan tiedostopolut,
     * ja yritetään lukea molemmat tiedostot. Jos tiedostoja ei ole, luodaan oletustiedostot.
     * @param nimi tiedoston nimi (hakemisto, esim. "AgoBank")
     * @throws SailoException jos tiedostojen lukemisessa tulee ongelmia
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
     * Etsii asiakkaita annetun hakuehdon perusteella.
     * @param hakuehto hakuehto
     * @param k kentän indeksi, jota verrataan (esim. nimi tai puhelinnumero)
     * @return löydetyt asiakkaat
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
