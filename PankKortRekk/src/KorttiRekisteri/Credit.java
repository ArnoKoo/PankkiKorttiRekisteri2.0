package KorttiRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import fi.jyu.mit.ohj2.Mjonot;

/*  Luokka credit sijaitsee "Pankkikortit" luokan alla. Luokka vastaanottaa kahdeksan parametria, jotka näkyvät alla.
 * 	Pohja on otettu Vesan esimerkeistä
 */

public class Credit implements Iterable <Pankkikortti> {
	
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

	public String getKorttityyppi() {
		return korttityyppi;
	}
	
	public int getAsiakasNro() {
	    return tunnusNro;
	}
	
    /**
     * Palataan asiaan ht seiskassa
     */
    public Credit() {
         //
     }
     
     /**
      * UUSI JUTTU ----------------------------------------------------------------
      * Alustetaan tietyn asiakkaan kortti 
     * @param asiakasNro asiakkaan viitenumero
     */
    public Credit(int asiakasNro) {
        this.asiakasNro = asiakasNro;
     }
	
	/**
	 * @param nro apujuttu Asiakasnumeroa varten
	 */
	public void vastaaCredit(int nro) {
	    asiakasNro = nro;
		korttityyppi = "Credit";
		pvm = "1.1.2025";
		korttinumero = "4000 0000 0000 0002";
		PIN = "1234";
		CVC = "420";
		lähimaksu = true;
		VPT = "FI42 0000 0000 0000 00";
		salasana = "2024";
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
	
	public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + "  " + korttityyppi + " " + pvm);
        out.println("  " + korttinumero + "  " + PIN + " " + CVC);
        out.println("  Onko lähimaksua: " + lähimaksu);
        out.println("  Verkkopankkitunnus: " + VPT + " Salasana: " + salasana);
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
	 
	 /**
     * UUSI JUTTU ----------------------------------------------------------------
     * Seurasin Vesan luentoa ja huomasin meiltä puuttuvan main aliohjelma. 
     * @param args ei tee mitään
     */
    public static void main(String[] args) {
         Credit cre = new Credit();
         cre.vastaaCredit(2);
         cre.tulosta(System.out);
     }

    @Override
    public Iterator<Pankkikortti> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
}
