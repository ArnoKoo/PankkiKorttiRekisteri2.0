package KorttiRekisteri;

public class HetuTarkistus {
	
    public static final String TARKISTUSMERKIT = "0123456789ABCDEFHJKLMNPRSTUVWXY";

    public static char hetunTarkistusMerkki(String hetu) {
        String pvm = hetu.substring(0,6);
        String yksilo = hetu.substring(7,10);
        long luku = Long.parseLong(pvm+yksilo);
        int jakojaannos = (int)(luku % 31L);
        return TARKISTUSMERKIT.charAt(jakojaannos);
    }
    
    public static int rand(int ala, int yla) {
      double n = (yla-ala)*Math.random() + ala;
      return (int)Math.round(n);
    }

    public static String arvoHetu() {
        String apuhetu = String.format("%02d",rand(1,28))   +
        String.format("%02d",rand(1,12))   +
        String.format("%02d",rand(0,99))   + "-" +
        String.format("%03d",rand(1,999));
        return apuhetu + hetunTarkistusMerkki(apuhetu);        
    }
    
}
