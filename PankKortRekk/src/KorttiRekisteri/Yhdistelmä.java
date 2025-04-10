package KorttiRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import KorttiRekisteri.HetuTarkistus;
import fi.jyu.mit.ohj2.Mjonot;


/*  Luokka "yhdistelmä" sijaitsee "Pankkikortit" luokan alla. Luokka vastaanottaa kahdeksan parametria, jotka näkyvät alla.
 * 	Pohja on otettu Vesan esimerkeistä
 */

public class Yhdistelmä implements Iterable <Pankkikortti> {
	
	private int			tunnusNro;
	private String		korttityyppi				= "";
	private int         asiakasNro;
	private String		pvm							= "";
	private String		korttinumero				= "";
	private String		PIN							= "";
	private String		CVC							= "";
	private boolean		lähimaksu;
	private String		VPT							= ""; // Verkkopankkitunnus
	private String		salasana					= "";
	
	private static int  seuraavaNro				= 1;

    /**
     * @return korttityypin
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
   public Yhdistelmä() {
        //
    }
    
    /**
     * UUSI JUTTU ----------------------------------------------------------------
     * Alustetaan tietyn asiakkaan kortti 
    * @param asiakasNro asiakkaan viitenumero
    */
   public Yhdistelmä(int asiakasNro) {
        this.asiakasNro = asiakasNro;
    }
	
	/*
	 * Tämän tarkoituksena on palauttaa esimerkki yhdistelmä kortista
	 */
	
	public void vastaaYhdistelmä(int nro) {
	    asiakasNro = nro;
		korttityyppi = "Yhdistelmä";
		pvm = "1.1.2025";
		korttinumero = "4000 0000 0000 0003";
		PIN = "1234";
		CVC = "420";
		lähimaksu = true;
		VPT = "FI43 0000 0000 0000 00";
		salasana = "2025";
	}
	
	@Override
    public String toString() {
		return String.join("|", 
		        String.valueOf(tunnusNro),
		        String.valueOf(asiakasNro),
		        korttityyppi,
		        pvm,
		        korttinumero,
		        PIN,
		        CVC,
		        String.valueOf(lähimaksu),
		        VPT,
		        salasana
		    );
    }
	
	public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        asiakasNro = Mjonot.erota(sb, '|', asiakasNro);
        korttityyppi = Mjonot.erota(sb, '|', korttityyppi);
        pvm = Mjonot.erota(sb, '|', pvm);
        korttinumero = Mjonot.erota(sb, '|', korttinumero);
        PIN = Mjonot.erota(sb, '|', PIN);
        CVC = Mjonot.erota(sb, '|', CVC);
        lähimaksu = Mjonot.erota(sb, '|', lähimaksu).equals("true")? true : false;
        VPT = Mjonot.erota(sb, '|', VPT);
        salasana = Mjonot.erota(sb, '|', salasana);
    }
	
	private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
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
	 
	 /**
	  * UUSI JUTTU ----------------------------------------------------------------
	  * Seurasin Vesan luentoa ja huomasin meiltä puuttuvan main aliohjelma. 
	 * @param args ei tee mitään
	 */
	public static void main(String[] args) {
	     Yhdistelmä yhd = new Yhdistelmä();
	     yhd.vastaaYhdistelmä(2);
	     yhd.tulosta(System.out);
	 }

    @Override
    public Iterator<Pankkikortti> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
}
