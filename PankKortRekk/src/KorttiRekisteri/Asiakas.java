package KorttiRekisteri;

import java.io.*;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
* Kerhon jäsen joka osaa mm. itse huolehtia tunnusNro:staan.
* Pohjana on käytetty Vesan esimerkkiä
*/

import static KorttiRekisteri.HetuTarkistus.*;
@SuppressWarnings("javadoc")
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
	
	public String getNimi() {
	    return nimi;
	}
	
	public String setNimi(String s) {
	    nimi = s;
	    return null;
	}
	
	public int getTunnusNro( ) {
	    return tunnusNro;
	}
	
	private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }

	public String getHetu() {
	    return hetu;
	}
	
	private HetuTarkistus hetut = new HetuTarkistus();
	
	public String setHetu(String s) {
	    String virhe = hetut.tarkista(s);
	    if (s != null) return virhe;
	    hetu = s;
	    return null;
	}

	public String getKatuosoite() {
	    return katuosoite;
	}
	
	public String setKatuosoite(String s) {
	    katuosoite = s;
	    return null;
	}

   public String getPostinumero() {
       return postinumero;
   }
   
   public String setPostinumero(String s) {
       if (!s.matches("[0-9]*")) return "Postinumeron on oltava numeerinen!";
       postinumero = s;
       return null;
   }
   
   public String getPostiToimipaikka() {
       return postitoimipaikka;
   }
   
   public String setPostiToimipaikka(String s) {
       postitoimipaikka = s;
       return null;
   }
   
   public String getPuhelinnumero() {
       return puhelinnumero;
   }
   
   public String setPuhelinnumero(String s) {
       if (!s.matches("[0-12]*")) return "Puhelinnumeron on oltava numeerinen!";
       puhelinnumero = s;
       return null;
   }
   
   public String getSahkoposti() {
       return sähköposti;
   }
   
   public String setSahkoposti(String s) {
       sähköposti = s;
       return null;
   }
   
   @Override
   public Asiakas clone() throws CloneNotSupportedException {
       Asiakas uusi;
       uusi = (Asiakas) super.clone();
       return uusi;
   }
	
	// Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
	
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
	 * @param rivi aa
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
	
	// Tulostetaan henkilön tiedot
	
	 public void tulosta(PrintStream out) {
          out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi + "  " + hetu);
          out.println("  " + katuosoite + "  " + postinumero + " " + postitoimipaikka);
          out.println("  puhelinnumero: " + puhelinnumero);
          out.println("  sähköposti: " + sähköposti);
      }
	 
	 public void tulosta(OutputStream os) {
          tulosta(new PrintStream(os));
     }
	 
	 public static class Vertailija implements Comparator<Asiakas> { 
	        private int k;  
	         
 
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
	 
	 public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
	 }
}
