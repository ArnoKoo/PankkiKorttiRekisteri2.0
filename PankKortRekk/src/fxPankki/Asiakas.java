package fxPankki;

import java.io.*;
import java.util.Comparator;

import KorttiRekisteri.HetuTarkistus;
import KorttiRekisteri.Tietue;
import fi.jyu.mit.ohj2.Mjonot;

/**
* Kerhon jäsen joka osaa mm. itse huolehtia tunnusNro:staan.
* Pohjana on käytetty Vesan esimerkkiä
*/

import static KorttiRekisteri.HetuTarkistus.*;

public class Asiakas implements Cloneable, Tietue {
 
	private int			tunnusNro;
	private String		nimi					= "";
	private String		hetu					= "";
	private String		katuosoite				= "";
	private String		postinumero				= "";
	private String		postitoimipaikka		= "";
	private String		puhelinnumero			= "";
	private String		sähköposti				= "";
	
	private static int  seuraavaNro				= 1;
	
	/**
     * @return asiakkaan nimi
     * @example
     * <pre name="test">
     *   Asiakas aku = new Asiakas();
     *   aku.vastaaErik();
     *   aku.getNimi() =R= "Ankka Aku .*";
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
	public int getTunnusNro( ) {
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
	 * @return null
	 */
	public String setHetu(String s) {
	    String virhe = hetut.tarkista(s);
	    if (s != null) return virhe;
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
 * @return null
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
 * @return null
 */
public String setPuhelinnumero(String s) {
       if (!s.matches("[0-12]*")) return "Puhelinnumeron on oltava numeerinen!";
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
 * @return Object kloonattu jäsen
 * @example
 * <pre name="test">
 * #THROWS CloneNotSupportedException 
 *   Asiakas asiakas = new Asiakas();
 *   asiakas.parse("   3  |  Ankka Aku   | 123");
 *   Jasen kopio = asiakas.clone();
 *   kopio.toString() === asiakas.toString();
 *   asiakas.parse("   4  |  Ankka Tupu   | 123");
 * </pre>
 */
   @Override
   public Asiakas clone() throws CloneNotSupportedException {
       Asiakas uusi;
       uusi = (Asiakas) super.clone();
       return uusi;
   }
	
	/**
	 * @param apuhetu hetu joka annetaan
	 */
	public void vastaaErik(String apuhetu) {
		nimi = "Erik Sandren" + rand(1000, 9999);
		hetu = apuhetu;
		katuosoite = "Rantatie 6";
		postinumero = "00500";
		postitoimipaikka = "Helsinki";
		puhelinnumero = "040678123";
		sähköposti = "erik@yahoo.com";
	}
	
	/**	Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
	* 	Henkilötunnus arvotaan, jotta kahdella jäsenellä ei olisi
	* 	samoja tietoja.
	*/
	public void vastaaErik() {
         String apuhetu = arvoHetu();
         vastaaErik(apuhetu);
     }
	
	/**
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Asiakas asiakas = new Asiakas();
     *   asiakas.parse("   3  |  Ankka Aku   | 030201-111C");
     *   asiakas.toString().startsWith("3|Ankka Aku|030201-111C|") === true;
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
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Asiakas asiakas = new Asiakas();
     *   asiakas.parse("   3  |  Ankka Aku   | 030201-111C");
     *   asiakas.getTunnusNro() === 3;
     *   asiakas.toString().startsWith("3|Ankka Aku|030201-111C|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   asiakas.rekisteroi();
     *   int n = asiakas.getTunnusNro();
     *   asiakas.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   asiakas.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   asiakas.getTunnusNro() === n+20+1;
     *     
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
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
	 public void tulosta(PrintStream out) {
          out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi + "  " + hetu);
          out.println("  " + katuosoite + "  " + postinumero + " " + postitoimipaikka);
          out.println("  puhelinnumero: " + puhelinnumero);
          out.println("  sähköposti: " + sähköposti);
      }
	 
	 /**
	 * @param os printti
	 */
	public void tulosta(OutputStream os) {
          tulosta(new PrintStream(os));
     }
	 
	 /**
	 * @author OMISTAJA
	 * @version 16 Apr 2025
	 * Vertailija
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
	     * Antaa k:n kentän sisällön merkkijonona 
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
	 * Antaa k:n kentän sisällön merkkijonona
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
	     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
	     * @param k kuinka monennen kentän arvo asetetaan
	     * @param jono jonoa joka asetetaan kentän arvoksi
	     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
	     * @example
	     * <pre name="test">
	     *   Asiakas asiakas = new Asiakas();
	     *   asiakas.aseta(1,"Ankka Aku") === null;
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
     * @return asiakkaan uusi tunnusNro
     * @example
     * <pre name="test">
     *   Asiakas aku1 = new Asiakas();
     *   aku1.getTunnusNro() === 0;
     *   aku1.rekisteroi();
     *   Asiakas aku2 = new Asiakas();
     *   aku2.rekisteroi();
     *   int n1 = aku1.getTunnusNro();
     *   int n2 = aku2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
	 public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
	 }
}
