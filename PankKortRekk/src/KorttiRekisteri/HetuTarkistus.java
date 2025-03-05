package KorttiRekisteri;

public class HetuTarkistus {
	
    public static final String TARKISTUSMERKIT = "0123456789ABCDEFHJKLMNPRSTUVWXY";
    
    /*
     * Palauttaa mikä olisi hetun tarkistumerkki. Tuotava parametrinä
     * laillista muotoa oleva hetu, josta mahdollisesti tarkistumerkki 
     * puuttuu.
     * @param hetu tutkittava hetu
     * @return hetun tarkistusmerkki
     */

    public static char hetunTarkistusMerkki(String hetu) {
        String pvm = hetu.substring(0,6);
        String yksilo = hetu.substring(7,10);
        long luku = Long.parseLong(pvm+yksilo);
        int jakojaannos = (int)(luku % 31L);
        return TARKISTUSMERKIT.charAt(jakojaannos);
    }
    
    /*
    * Arvotaan satunnainen kokonaisluku välille [ala,yla]
    * @param ala arvonnan alaraja
    * @param yla arvonnan yläraja
    * @return satunnainen luku väliltä [ala,yla]
    */
    
    public static int rand(int ala, int yla) {
      double n = (yla-ala)*Math.random() + ala;
      return (int)Math.round(n);
    }
    
    /**
    * Arvotaan satunnainen henkilötunnus, joka täyttää hetun ehdot    
    * @return satunnainen laillinen henkilötunnus
    */
    public static String arvoHetu() {
        String apuhetu = String.format("%02d",rand(1,28))   +
        String.format("%02d",rand(1,12))   +
        String.format("%02d",rand(0,99))   + "-" +
        String.format("%03d",rand(1,999));
        return apuhetu + hetunTarkistusMerkki(apuhetu);        
    }
    
}
