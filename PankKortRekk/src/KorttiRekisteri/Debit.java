package KorttiRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

 /**
  * Luokka debit sijaitsee "Pankkikortit" luokan alla. Luokka vastaanottaa kahdeksan parametria, jotka näkyvät alla.
 * @author OMISTAJA
 * @version 10 Mar 2025
 *
 */
public class Debit implements Iterable <Pankkikortti> {
	    
	private int			tunnusNro;
	private String		korttityyppi				= "";
	private int         asiakasNro;
	private String		pvm							= "";
	private String		korttinumero				= "";
	private String		PIN							= "";
	private String		CVC							= "";
	private Boolean		lähimaksu;
	private String		VPT							= ""; // Verkkopankkitunnus
	private String		salasana					= "";
	
	private static int  seuraavaNro				= 1;

	/**
	 * @return korttityyppi
	 */
	public String getKorttityyppi() {
		return korttityyppi;
	}
	
    /**
     * @return asiakkaan numero
     */
    public int getAsiakasNro() {
        return asiakasNro;
    }	
    
    /**
     * Palataan asiaan ht seiskassa
     */
    public Debit() {
         //
     }
     
     /**
      * UUSI JUTTU ----------------------------------------------------------------
      * Alustetaan tietyn asiakkaan kortti 
     * @param asiakasNro asiakkaan viitenumero
     */
    public Debit(int asiakasNro) {
         this.asiakasNro = asiakasNro;
     }
	
	/**
	 * Tämän tarkoituksena on palauttaa esimerkki debit kortista
	 * @param nro numero
	 */
	public void vastaaDebit(int nro) {
	    asiakasNro = nro;
		korttityyppi = "Debit";
		pvm = "1.1.2025";
		korttinumero = "4000 0000 0000 0001";
		PIN = "1234";
		CVC = "420";
		lähimaksu = true;
		VPT = "FI41 0000 0000 0000 00";
		salasana = "2023";
	}
	
	/**
	 * @param out tietoa ulos näkyville
	 */
	public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + korttityyppi + " " + pvm);
        out.println("  " + korttinumero + "  " + PIN + " " + CVC);
        out.println("  Onko lähimaksua: " + lähimaksu);
        out.println("  Verkkopankkitunnus: " + VPT + " Salasana: " + salasana);
    }
	 
	 /**
	 * @param os avustaa tulosta -aliohjelmaa
	 */
	public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
	 }
	 
	 /**
	  * rekisteroi() antaa tunnusnumeron pankkikortille ja antaa seuraavalle kortille numeron + 1 
	 * @return tunnusnumero
	 */
	public int rekisteroi() {
      tunnusNro = seuraavaNro;
      seuraavaNro++;
      return tunnusNro;
	 }
	 
	 /**
	 * @return tunnusnumeron
	 */
	 public int getTunnusNro( ) {
		 return tunnusNro;
	 }
	 
	 /**
     * UUSI JUTTU ----------------------------------------------------------------
     * Seurasin Vesan luentoa ja huomasin meiltä puuttuvan main aliohjelma. 
     * @param args ei tee mitään
     */
    public static void main(String[] args) {
        Debit yhd = new Debit();
         yhd.vastaaDebit(1);
         yhd.tulosta(System.out);
     }

    @Override
    public Iterator<Pankkikortti> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
}
