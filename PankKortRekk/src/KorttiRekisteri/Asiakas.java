package KorttiRekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
* Kerhon jäsen joka osaa mm. itse huolehtia tunnusNro:staan.
* Pohjana on käytetty Vesan esimerkkiä
*/

import static KorttiRekisteri.HetuTarkistus.*;
@SuppressWarnings("javadoc")
public class Asiakas {
 
	private int			tunnusNro;
	private String		nimi					= "";
	private String		hetu					= "";
	private String		katuosoite				= "";
	private String		postinumero				= "";
	private String		postitoimipaikka		= "";
	private String		puhelinnumero			= "";
	private String		sähköposti				= "";
	
	private static int  seuraavaNro				= 1;
	
	// Palauttaa jäsenen nimen
	
	public String getNimi() {
	    return nimi;
	}
	
	public int getTunnusNro( ) {
	    return tunnusNro;
	}

	public String getHetu() {
	    return hetu;
	}

	public String getKatuosoite() {
	    return katuosoite;
	}

   public String getPostinumero() {
       return postinumero;
   }
   
   public String getPostiToimipaikka() {
       return postitoimipaikka;
   }
   
   public String getPuhelinnumero() {
       return puhelinnumero;
   }
   
   public String getSahkoposti() {
       return sähköposti;
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
	
	   private void setTunnusNro(int nr) {
	        tunnusNro = nr;
	        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
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
	 
	 public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
	 }
}
