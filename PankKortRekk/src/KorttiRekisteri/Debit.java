package KorttiRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import fi.jyu.mit.ohj2.Mjonot;

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
     * Alustetaan kortti tietylle asiakkaalle
     * @return asiakkaan numero
     */
    public int getAsiakasNro() {
        return asiakasNro;
    }	
    
    /**
     * Oletusrakentaja
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
	 * Tämän tarkoituksena on palauttaa esimerkki debit kortin tiedoista
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
	
	@Override
    public String toString() {
        return "" + getTunnusNro() + "|" + asiakasNro + "|" + korttityyppi + "|" + pvm + "|" + korttinumero + "|" + PIN + "|" + CVC + "|" + VPT + "|" + salasana;
    }

	
	public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        korttityyppi = Mjonot.erota(sb, '|', korttityyppi);
        asiakasNro = Mjonot.erota(sb, '|', asiakasNro);
        pvm = Mjonot.erota(sb, '|', pvm);
        korttinumero = Mjonot.erota(sb, '|', korttinumero);
        PIN = Mjonot.erota(sb, '|', PIN);
        CVC = Mjonot.erota(sb, '|', CVC);
        VPT = Mjonot.erota(sb, '|', VPT);
        salasana = Mjonot.erota(sb, '|', salasana);
    }
	
	private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }

	
	/**
	 * Tulostetaan kortin tiedot järjestettyyn muotoon.
	 * @param out tietoa ulos näkyville
	 */
	public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + korttityyppi + " " + pvm);
        out.println("  " + korttinumero + "  " + PIN + " " + CVC);
        out.println("  Onko lähimaksua: " + lähimaksu);
        out.println("  Verkkopankkitunnus: " + VPT + " Salasana: " + salasana);
    }
	 
	 /**
	  * Ohjataan tulostuksen printstreamin kautta
	 * @param os avustaa tulosta -aliohjelmaa
	 */
	public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
	 }
	 
	 /**
	  * rekisteroi() antaa tunnusnumeron pankkikortille ja antaa seuraavalle kortille tunnusnumeron + 1 
	 * @return tunnusnumero
	 */
	public int rekisteroi() {
      tunnusNro = seuraavaNro;
      seuraavaNro++;
      return tunnusNro;
	 }
	 
	 /**
	  * Palauttaa kortin tunnusnumeron
	 * @return tunnusnumeron
	 */
	 public int getTunnusNro( ) {
		 return tunnusNro;
	 }
	 
	 /**
     * UUSI JUTTU ----------------------------------------------------------------
     * Seurasin Vesan luentoa ja huomasin meiltä puuttuvan main aliohjelma. UPDATE: ei pakollinen
     * @param args ei tee mitään
     */
    public static void main(String[] args) {
        Debit deb = new Debit();
        deb.vastaaDebit(1);
        deb.tulosta(System.out);
     }

    @Override
    public Iterator<Pankkikortti> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
}
