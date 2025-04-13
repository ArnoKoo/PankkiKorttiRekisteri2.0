package KorttiRekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import fi.jyu.mit.ohj2.Mjonot;

/*  Luokka credit sijaitsee "Pankkikortit" luokan alla. Luokka vastaanottaa kahdeksan parametria, jotka näkyvät alla.
 * 	Pohja on otettu Vesan esimerkeistä
 */
public class Credit implements Cloneable, Tietue {
	
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

	public String getKorttityyppi() {
		return korttityyppi;
	}
	
	public int getAsiakasNro() {
	    return asiakasNro;
	}
	
    /**
     * Palataan asiaan ht seiskassa
     */
    public Credit() {
         //
     }
    
    @Override
    public Credit clone() throws CloneNotSupportedException {
        Credit uusi;
        uusi = (Credit) super.clone();
        return uusi;
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
        return 2;
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

    @Override
    public String aseta(int k, String s) {
        // TODO Auto-generated method stub
        return null;
    }
}
