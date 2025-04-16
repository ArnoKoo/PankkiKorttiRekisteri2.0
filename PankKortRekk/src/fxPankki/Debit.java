package fxPankki;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import KorttiRekisteri.Tietue;
import fi.jyu.mit.ohj2.Mjonot;

 /**
  * Luokka debit sijaitsee "Pankkikortit" luokan alla. Luokka vastaanottaa kahdeksan parametria, jotka näkyvät alla.
 * @author OMISTAJA
 * @version 10 Mar 2025
 *
 */
public class Debit implements Cloneable, Tietue {
    private int         tunnusNro;
    private String      korttityyppi                = "Debit";
    private int         asiakasNro;
    private String      pvm                         = "";
    private String      korttinumero                = "";
    private String      PIN                         = "";
    private String      CVC                         = "";
    private boolean     lähimaksu;
    private String      VPT                         = ""; // Verkkopankkitunnus
    private String      salasana                    = "";
    
    private static int  seuraavaNro             = 1;

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
     * Tehdään identtinen klooni asiakkaasta
     * @return Object kloonattu asiakas
     * @example
     * <pre name="test">
     *   #THROWS CloneNotSupportedException
     *   Debit deb = new Debit();
     *   deb.vastaaDebit(1);
     *   Debit kopio = deb.clone();
     *   kopio.toString() === deb.toString();
     *   deb.vastaaDebit(2);
     *   kopio.toString().equals(deb.toString()) === false;
     * </pre>
     */
    @Override
    public Debit clone() throws CloneNotSupportedException {
        Debit uusi;
        uusi = (Debit) super.clone();
        return uusi;
    }
     

    /**
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
    
    /**
     * @return Debit tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Debit deb = new Debit();
     *   deb.vastaaDebit(1);
     *   deb.toString() === "0|1|Debit|1.1.2025|4000 0000 0000 0001|1234|420|true|FI41 0000 0000 0000 00|2023";
     * </pre>
     */
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

    /**
     * @param rivi josta harrastuksen tiedot otetaan
     * @example
     * <pre name="test">
     *   Debit deb = new Debit();
     *   deb.parse(" 2 | 10 | Debit | 1.1.2025 | 4000 0000 0000 0001 | 1234 | 420 | true | FI41 0000 0000 0000 00 | 2023");
     *   deb.getAsiakasNro() === 10;
     *   deb.toString() === "2|10|Debit|1.1.2025|4000 0000 0000 0001|1234|420|true|FI41 0000 0000 0000 00|2023";
     *   deb.rekisteroi();
     *   int n = deb.getTunnusNro();
     *   deb.parse("" + (n+20));
     *   deb.rekisteroi();
     *   deb.getTunnusNro() === n+20+1;
     * </pre>
     */
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
     * Antaa debitille seuraavan rekisterinumeron.
     * @return harrastuksen uusi tunnusNro
     * @example
     * <pre name="test">
     *   Debit pitsi1 = new Debit();
     *   pitsi1.getTunnusNro() === 0;
     *   pitsi1.rekisteroi();
     *   Debit pitsi2 = new Debit();
     *   pitsi2.rekisteroi();
     *   int n1 = pitsi1.getTunnusNro();
     *   int n2 = pitsi2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
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

    /**
     * @return null, ei tullu kai kunnol käytettyy
     */
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
    
    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Debit deb = new Debit();
     * deb.tunnusNro = 2; 
     * deb.asiakasNro = 10; 
     * deb.korttityyppi = "Debit";
     * deb.pvm = "1.1.2025"; 
     * deb.korttinumero = "4000 0000 0000 0001";
     * deb.PIN = "1234"; 
     * deb.CVC = "420"; 
     * deb.lähimaksu = true;
     * deb.VPT = "FI41 0000 0000 0000 00"; 
     * deb.salasana = "2023";
     * 
     * deb.anna(0) === "2";
     * deb.anna(1) === "10";
     * deb.anna(2) === "Debit";
     * deb.anna(3) === "1.1.2025";
     * deb.anna(4) === "4000 0000 0000 0001";
     * deb.anna(5) === "1234";
     * deb.anna(6) === "420";
     * deb.anna(7) === "true";
     * deb.anna(8) === "FI41 0000 0000 0000 00";
     * deb.anna(9) === "2023";
     * </pre>
     */
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
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param jono asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     * Debit deb = new Debit();
     * deb.aseta(3, "1.1.2025") === null;
     * deb.aseta(3, "kissa") !== null;
     * deb.aseta(2, "Mikä tahansa") === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String jono) {
     String tjono = jono.trim();
     //StringBuffer sb = new StringBuffer(tjono);
     switch ( k ) {
        case 2:
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
