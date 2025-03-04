package KorttiRekisteri;

import java.io.*;

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
	
	public String getNimi() {
		return nimi;
	}
	
	public void vastaaErik(String apuhetu) {
		nimi = "Erik Sjöholm" + rand(1000, 9999);
		hetu = apuhetu;
		katuosoite = "Rantatie 6";
		postinumero = "00500";
		postitoimipaikka = "Helsinki";
		puhelinnumero = "040678123";
		sähköposti = "erik@yahoo.com";
	}
	
	public void vastaaErik() {
         String apuhetu = arvoHetu();
         vastaaErik(apuhetu);
     }
	
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
	 
	 public int getTunnusNro( ) {
		 return tunnusNro;
	 }
}
