package KorttiRekisteri;

import java.io.*;

/**
* Kerhon jäsen joka osaa mm. itse huolehtia tunnusNro:staan.
* Pohjana on käytetty Vesan esimerkkiä
*/

import static KorttiRekisteri.HetuTarkistus.*;

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
	
	/*	Apumetodi, jolla saadaan täytettyä testiarvot jäsenelle.
	* 	Henkilötunnus arvotaan, jotta kahdella jäsenellä ei olisi
	* 	samoja tietoja.
	*/
	
	public void vastaaErik() {
         String apuhetu = arvoHetu();
         vastaaErik(apuhetu);
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
	 
	 // Antaa jäsenelle tunnusnumeron
	 
	 public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
	 }
	 
	 // Palauttaa tunnusnumeron
	 
	 public int getTunnusNro( ) {
		 return tunnusNro;
	 }
}
