package KorttiRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import KorttiRekisteri.HetuTarkistus;
import fi.jyu.mit.ohj2.Mjonot;


/*  Luokka "yhdistelmä" sijaitsee "Pankkikortit" luokan alla. Luokka vastaanottaa kahdeksan parametria, jotka näkyvät alla.
 * 	Pohja on otettu Vesan esimerkeistä
 */

public class Yhdistelmä implements Cloneable, Tietue {
	
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
   
   @Override
   public Yhdistelmä clone() throws CloneNotSupportedException {
       Yhdistelmä uusi;
       uusi = (Yhdistelmä) super.clone();
       return uusi;
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

    public Iterator<Pankkikortti> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public int getKenttia() {
        return 10;
    }
    
    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 3;
    }
    
    @Override
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + tunnusNro;
            case 1:
                return "" + asiakasNro;
            case 2:
                return "" + korttityyppi;
            case 3:
                return "" + pvm;
            case 4:
                return "" + korttinumero;
            case 5:
                return "" + PIN;
            case 6:
                return "" + CVC;
            case 7:
                return "" + lähimaksu;
            case 8:
                return "" + VPT;
            case 9:
                return "" + salasana;
            default:
                return "???";
        }
    }
    
    /**
     * @param k a
     * @return a
     */
    @Override
    public String getKysymys(int k) {
        switch (k) {
        case 0:
            return "tunnusNro";
        case 1:
            return "asiakasNro";
        case 2:
            return "korttityyppi";
        case 3:
            return "pvm";
        case 4:
            return "korttinumero";
        case 5:
            return "PIN";
        case 6:
            return "CVC";
        case 7:
            return "lähimaksu";
        case 8:
            return "VPT";
        case 9:
            return "salasana";
        default:
            return "???";
        }
    }
    String tekstilähimaksu = String.valueOf(lähimaksu);
    @Override
    public String aseta(int k, String jono) {
     String tjono = jono.trim();
     //StringBuffer sb = new StringBuffer(tjono);
     switch ( k ) {
        case 2:
            korttityyppi = tjono;
            return null;
        case 3:
            pvm = tjono;
            return null;
        case 4:
            korttinumero = tjono;
            return null;
        case 5:
            PIN = tjono;
            return null;
        case 6:
            CVC = tjono;
            return null;
        case 7:
        	tekstilähimaksu = tjono;
        	return null;
        case 8:
            VPT = tjono;
            return null;
        case 9:
            salasana = tjono;
            return null;
        default:
            return "";
     }
 }
}
