package fxPankki;

import java.io.*;
import java.util.Comparator;

import KorttiRekisteri.HetuTarkistus;
import KorttiRekisteri.Tietue;
import fi.jyu.mit.ohj2.Mjonot;

import static KorttiRekisteri.HetuTarkistus.*;

/**
 * Kerhon jäsen joka osaa mm. itse huolehtia tunnusNro:staan.
 * Pohjana on käytetty Vesan esimerkkiä
 */
public class Asiakas implements Cloneable, Tietue {
 
    private int         tunnusNro;
    private String      nimi                    = "";
    private String      hetu                    = "";
    private String      katuosoite              = "";
    private String      postinumero             = "";
    private String      postitoimipaikka        = "";
    private String      puhelinnumero           = "";
    private String      sähköposti              = "";
    
    private static int  seuraavaNro             = 1;
    
    /**
     * @return asiakkaan nimi
     * @example
     * <pre name="test">
     *   Asiakas asiakas = new Asiakas();
     *   asiakas.vastaaErik();
     *   asiakas.getNimi() =R= "Tino Harjumäki.*";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * @param s nimi
     * @return null
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }
    
    /**
     * @return tunnusNro
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }

    /**
     * @return hetu
     */
    public String getHetu() {
        return hetu;
    }
    
    private HetuTarkistus hetut = new HetuTarkistus();
    
    /**
     * @param s hetu
     * @return null, jos syöte ok, muuten virheilmoitus
     */
    public String setHetu(String s) {
        String virhe = hetut.tarkista(s);
        if (virhe != null) return virhe;
        hetu = s;
        return null;
    }

    /**
     * @return katuosoite
     */
    public String getKatuosoite() {
        return katuosoite;
    }
    
    /**
     * @param s katuosoite
     * @return null
     */
    public String setKatuosoite(String s) {
        katuosoite = s;
        return null;
    }

   /**
    * @return postinumero
    */
   public String getPostinumero() {
       return postinumero;
   }
   
   /**
    * @param s postinumero
    * @return null, jos syöte ok, muuten virheilmoitus
    */
   public String setPostinumero(String s) {
       if (!s.matches("[0-9]*")) return "Postinumeron on oltava numeerinen!";
       postinumero = s;
       return null;
   }
   
   /**
    * @return postitoimipaikka
    */
   public String getPostiToimipaikka() {
       return postitoimipaikka;
   }
   
   /**
    * @param s postitoimipaikka
    * @return null
    */
   public String setPostiToimipaikka(String s) {
       postitoimipaikka = s;
       return null;
   }
   
   /**
    * @return puhelinnumero
    */
   public String getPuhelinnumero() {
       return puhelinnumero;
   }
   
   /**
    * @param s puhelinnumero
    * @return null, jos syöte ok, muuten virheilmoitus
    */
   public String setPuhelinnumero(String s) {
       if (!s.matches("[0-9]*")) return "Puhelinnumeron on oltava numeerinen!";
       puhelinnumero = s;
       return null;
   }
   
   /**
    * @return sähköposti
    */
   public String getSahkoposti() {
       return sähköposti;
   }
   
   /**
    * @param s sähköposti
    * @return null
    */
   public String setSahkoposti(String s) {
       sähköposti = s;
       return null;
   }
   
   /**
    * Palauttaa kloonatun jäsenen
    * @return kloonattu jäsen
    * @example
    * <pre name="test">
    * #THROWS CloneNotSupportedException 
    *   Asiakas asiakas = new Asiakas();
    *   asiakas.parse("3|Tino Harjumäki|040506-321H|jokutie|00000|Jyväskylä|123454321|tiantaha@jyu.fi");
    *   Asiakas kopio = asiakas.clone();
    *   kopio.toString() === asiakas.toString();
    *   asiakas.parse("4|Tino Tupu|040506-321H|jokutie|00000|Jyväskylä|123454321|tiantaha@jyu.fi");
    * </pre>
    */
   @Override
   public Asiakas clone() throws CloneNotSupportedException {
       Asiakas uusi;
       uusi = (Asiakas) super.clone();
       return uusi;
   }
    
    /**
     * Asettaa jäsenelle testiarvot käyttäen annettua hetua
     * @param apuhetu annettu hetu
     */
    public void vastaaErik(String apuhetu) {
        nimi = "Tino Harjumäki";
        hetu = apuhetu;
        katuosoite = "jokutie";
        postinumero = "00000";
        postitoimipaikka = "Jyväskylä";
        puhelinnumero = "123454321";
        sähköposti = "tiantaha@jyu.fi";
    }
    
    /**
     * Täyttää jäsenelle testiarvot, jossa hetu arvotaan
     */
    public void vastaaErik() {
         String apuhetu = arvoHetu();
         vastaaErik(apuhetu);
     }
    
    /**
     * Palauttaa jäsenen tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Asiakas asiakas = new Asiakas();
     *   asiakas.parse("3|Tino Harjumäki|040506-321H|jokutie|00000|Jyväskylä|123454321|tiantaha@jyu.fi");
     *   asiakas.getTunnusNro() === 3;
     *   asiakas.toString().startsWith("3|Tino Harjumäki|040506-321H|jokutie|00000|Jyväskylä|123454321|tiantaha@jyu.fi") === true;
     * </pre>  
     */
    @Override
    public String toString() {
        return "" + 
                getTunnusNro() + "|" +
                nimi + "|" +
                hetu + "|" +
                katuosoite + "|" +
                postinumero + "|" +
                postitoimipaikka + "|" +
                puhelinnumero + "|" +
                sähköposti;
    }
    
    /**
     * Selvittää jäsenen tiedot pilkulla erotellusta merkkijonosta.
     * Pitää huolen, että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * @example
     * <pre name="test">
     *   Asiakas asiakas = new Asiakas();
     *   asiakas.parse("3|Tino Harjumäki|040506-321H|jokutie|00000|Jyväskylä|123454321|tiantaha@jyu.fi");
     *   asiakas.getTunnusNro() === 3;
     *   asiakas.toString().startsWith("3|Tino Harjumäki|040506-321H|jokutie|00000|Jyväskylä|123454321|tiantaha@jyu.fi") === true;
     *
     *   asiakas.rekisteroi();
     *   int n = asiakas.getTunnusNro();
     *   asiakas.parse(""+(n+20));
     *   asiakas.rekisteroi();
     *   asiakas.getTunnusNro() === n+21;
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        hetu = Mjonot.erota(sb, '|', hetu);
        katuosoite = Mjonot.erota(sb, '|', katuosoite);
        postinumero = Mjonot.erota(sb, '|', postinumero);
        postitoimipaikka = Mjonot.erota(sb, '|', postitoimipaikka);
        puhelinnumero = Mjonot.erota(sb, '|', puhelinnumero);
        sähköposti = Mjonot.erota(sb, '|', sähköposti);
    }
    
    /**
     * Tulostaa henkilön tiedot.
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
          out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi + "  " + hetu);
          out.println("  " + katuosoite + "  " + postinumero + " " + postitoimipaikka);
          out.println("  puhelinnumero: " + puhelinnumero);
          out.println("  sähköposti: " + sähköposti);
    }
    
    /**
     * Tulostaa tiedot annettuun OutputStreamiin.
     * @param os tietovirta
     */
    public void tulosta(OutputStream os) {
          tulosta(new PrintStream(os));
    }
    
    /**
     * Vertailija
     * @author OMISTAJA
     * @version 16 Apr 2025
     */
    public static class Vertailija implements Comparator<Asiakas> { 
        private int k;  
         
        @SuppressWarnings("javadoc")
        public Vertailija(int k) { 
            this.k = k; 
        } 
         
        @Override 
        public int compare(Asiakas asiakas1, Asiakas asiakas2) { 
            return asiakas1.getAvain(k).compareToIgnoreCase(asiakas2.getAvain(k)); 
        }
    } 
         
    /** 
     * Antaa k:n kentän sisällön merkkijonona.
     * @param k monenenko kentän sisältö palautetaan 
     * @return kentän sisältö merkkijonona 
     */
    public String getAvain(int k) { 
        switch ( k ) { 
         case 0: return "" + tunnusNro;
         case 1: return "" + nimi;
         case 2: return "" + hetu;
         case 3: return "" + katuosoite;
         case 4: return "" + postinumero;
         case 5: return "" + postitoimipaikka;
         case 6: return "" + puhelinnumero;
         case 7: return "" + sähköposti;
         default: return "DUMBASS";
        }
    }
    
    @Override
    public int getKenttia() {
         return 8;
    }
    
    @Override
    public int ekaKentta() {
         return 1;
    }
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona.
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    @Override
    public String anna(int k) {
         switch ( k ) {
         case 0: return "" + tunnusNro;
         case 1: return "" + nimi;
         case 2: return "" + hetu;
         case 3: return "" + katuosoite;
         case 4: return "" + postinumero;
         case 5: return "" + postitoimipaikka;
         case 6: return "" + puhelinnumero;
         case 7: return "" + sähköposti;
         default: return "DUMBASS";
         }
    }
    
    /**
     * Palauttaa kysymyksen tietyn kentän osalta.
     * @param k minkä kentän kysymys palautetaan
     * @return kysymysteksti
     */
    @Override
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Tunnus nro";
        case 1: return "nimi";
        case 2: return "hetu";
        case 3: return "katuosoite";
        case 4: return "postinumero";
        case 5: return "postitoimipaikka";
        case 6: return "puhelinnumero";
        case 7: return "sähköposti";
        default: return "DUMBASS";
        }
    }
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon.
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono syöte, joka asetetaan kentän arvoksi
     * @return null, jos asettaminen onnistuu, muuten virheilmoitus.
     * @example
     * <pre name="test">
     *   Asiakas asiakas = new Asiakas();
     *   asiakas.aseta(1,"Tino Harjumäki") === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String jono) {
         String tjono = jono.trim();
         StringBuffer sb = new StringBuffer(tjono);
         switch ( k ) {
         case 0:
                setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
                return null;
            case 1:
                nimi = tjono;
                return null;
            case 2:
                HetuTarkistus hetut = new HetuTarkistus();
                String virhe = hetut.tarkista(tjono);
                if ( virhe != null ) return virhe;
                hetu = tjono;
                return null;
            case 3:
                katuosoite = tjono;
                return null;
            case 4:
                postinumero = tjono;
                return null;
            case 5:
                postitoimipaikka = tjono;
                return null;
            case 6:
                puhelinnumero = tjono;
                return null;
            case 7:
                sähköposti = tjono;
                return null;
            default:
                return "";
         }
    }
    
    /**
     * Palauttaa asiakkaan uuden tunnusnumeron.
     * @return uusi tunnusnumero
     * @example
     * <pre name="test">
     *   Asiakas asiakas1 = new Asiakas();
     *   asiakas1.getTunnusNro() === 0;
     *   asiakas1.rekisteroi();
     *   Asiakas asiakas2 = new Asiakas();
     *   asiakas2.rekisteroi();
     *   int n1 = asiakas1.getTunnusNro();
     *   int n2 = asiakas2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
}
