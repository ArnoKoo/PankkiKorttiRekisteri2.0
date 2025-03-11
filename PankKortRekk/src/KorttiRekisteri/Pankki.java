package KorttiRekisteri;

import java.util.List;

/**
* Kerho-luokka, joka huolehtii jäsenistöstä.  Pääosin kaikki metodit
* ovat vain "välittäjämetodeja" jäsenistöön.
* @author OMISTAJA
* @version 10 Mar 2025
*
*/
public class Pankki {
	private final Asiakkaat asiakkaat = new Asiakkaat();
	private final Pankkikortti pankkikortti = new Pankkikortti();
		
	/**
	 * @return pankin asiakasmäärän
	 */
	public int getAsiakkaat() {
		return asiakkaat.getLkm();
	}
	
	/**
	* Poistaa asiakkaan ja korteista ne joilla on nro. Kesken.
	* @param nro viitenumero, jonka mukaan poistetaan
	* @return montako jäsentä poistettiin
	*/
    public int poista(@SuppressWarnings("unused") int nro) {
          return 0;
    }
 
    /**
     * Lisää pankkiin uuden asiakkaan
     * @param asiakas on asiakas
     * @throws SailoException jos liikaa
     */    
    public void lisaa(Asiakas asiakas) throws SailoException {
    	asiakkaat.lisaa(asiakas);
    }
    
    /**
     * @param debit on Debit-luokasta napattu.
     */
    public void lisaaDebit(Debit debit) { //mietin tätä miten esim kreditti ja yhdistelmä huomenna, moi huomisen minä //Moi
        pankkikortti.lisaaDebitti(debit);
    }
    
    /**
     * @param credit on Credit-luokasta napattu.
     */
    public void lisaaCredit(Credit credit) { //mietin tätä miten esim kreditti ja yhdistelmä huomenna, moi huomisen minä //Moi
        pankkikortti.lisaaKreditti(credit);
    }
    
    /**
     * @param yhdistelma on Yhdistelmä-luokasta napattu.
     */
    public void lisaaYhdistelma(Yhdistelmä yhdistelma) { //mietin tätä miten esim kreditti ja yhdistelmä huomenna, moi huomisen minä //Moi
        pankkikortti.lisaaYhdistelma(yhdistelma);
    }
    
    
    
    /**
     * @param asiakas asiakas
     * @return tietorakenne jossa viiteet löydetteyihin debitteihin
     */
    public List<Debit> annaDebit(Asiakas asiakas) {
        return pankkikortti.annaDebitti(asiakas.getTunnusNro());
    }
    
    /**
     * @param asiakas asiakas
     * @return tietorakenne jossa viiteet löydetteyihin luottokortteihi
     */
    public List<Credit> annaCredit(Asiakas asiakas) {
        return pankkikortti.annaKreditti(asiakas.getTunnusNro());
    }
    
    /**
     * @param asiakas asiakas
     * @return tietorakenne jossa viiteet löydetteyihin yhdistelmäkortteihin
     */
    public List<Yhdistelmä> annaYhdistelma(Asiakas asiakas) {
        return pankkikortti.annaYhdistelma(asiakas.getTunnusNro());
    }
    
    // Palauttaa asiakkaan tietyssä indeksissä
    
    public Asiakas annaAsiakas(int i) throws IndexOutOfBoundsException {
    	return asiakkaat.anna(i);
    }
    
    // Lukee pankin tiedot tiedostosta
    
    public void lueTiedostosta(String nimi) throws SailoException {
    	asiakkaat.lueTiedostosta(nimi);
    }
    
    // Tallettaa pankin tiedot tiedostoon
    
    public void talleta() throws SailoException {
    	asiakkaat.talleta();
    }
    
    /**
     * @param args ei tee mitään
     */
    public static void main(String[] args) {
        Pankki pankki = new Pankki();
        
        try {
            //Asiakas
            Asiakas jaskaJokunen1 = new Asiakas();
            jaskaJokunen1.rekisteroi();
            jaskaJokunen1.vastaaErik();
            pankki.lisaa(jaskaJokunen1);
            
            //Pankkikortti
            Pankkikortti harrasteet1 = new Pankkikortti();
            Debit pitsi1 = new Debit();
            pitsi1.vastaaDebit(jaskaJokunen1.getTunnusNro());
            
            harrasteet1.lisaaDebitti(pitsi1);
            
            
            for (int i = 0; i < pankki.getAsiakkaat(); i++) {
                Asiakas asiakas = pankki.annaAsiakas(i);
                System.out.println("Jäsen paikassa: " + i);
                asiakas.tulosta(System.out);
            }
            
            System.out.println("============= Pankkikortti testi =================");
            
            List<Debit> debitit = harrasteet1.annaDebitti(jaskaJokunen1.getTunnusNro());
            
            for (Debit deb : debitit) {
                System.out.print(deb.getAsiakasNro() + " ");
                deb.tulosta(System.out);
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
