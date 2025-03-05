package KorttiRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/*  Luokka credit sijaitsee "Pankkikortit" luokan alla. Luokka vastaanottaa kahdeksan parametria, jotka näkyvät alla.
 * 	Pohja on otettu Vesan esimerkeistä
 */

public class Credit {
	
	private int			tunnusNro;
	private String		korttityyppi				= "";
	private String		pvm							= "";
	private String		korttinumero				= "";
	private String		PIN							= "";
	private String		CVC							= "";
	private Boolean		lähimaksu;
	private String		VPT							= ""; // Verkkopankkitunnus
	private String		salasana					= "";
	
	private static int  seuraavaNro				= 1;

	public String getKorttityyppi() {
		return korttityyppi;
	}
	
	/*
	 * Tämän tarkoituksena on palauttaa esimerkki credit kortista
	 */
	
	public void vastaaCredit() {
		korttityyppi = "Credit";
		pvm = "1.1.2025";
		korttinumero = "4000 0000 0000 0002";
		PIN = "1234";
		CVC = "420";
		lähimaksu = true;
		VPT = "FI42 0000 0000 0000 00";
		salasana = "2025";
	}
	
	/*
	 * Tulosta() tulostaa (no shit) kaikki tiedot ulos näkyville
	 */
	
	public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + korttityyppi + " " + pvm);
        out.println("  " + korttinumero + "  " + PIN + " " + CVC);
        out.println("  Onko lähimaksua: " + lähimaksu);
        out.println("  Verkkopankkitunnus: " + VPT + " Salasana: " + salasana);
    }
	
	
	 public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
	 }
	 
	 /*
	  * rekisteroi() antaa tunnusnumeron pankkikortille ja antaa seuraavalle kortille numeron + 1 
	  */
	 
	 public int rekisteroi() {
      tunnusNro = seuraavaNro;
      seuraavaNro++;
      return tunnusNro;
	 }
	 
	 /*
	  * Palauttaa tunnusnumeron
	  */
	 
	 public int getTunnusNro( ) {
		 return tunnusNro;
	 }
}
