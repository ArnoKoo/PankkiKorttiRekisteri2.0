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
     * Lisää pankkiin uuden asiakkaan, Sailoexception varmistaa ettei liikaa asiakkaita
     * @param asiakas on asiakas
     * @throws SailoException jos liikaa
     */    
    public void lisaa(Asiakas asiakas) throws SailoException {
    	asiakkaat.lisaa(asiakas);
    }
    
    /**
     * lisää pankkiin uuden debit kortin
     * @param debit on Debit-luokasta napattu.
     */
    public void lisaaDebit(Debit debit) { //mietin tätä miten esim kreditti ja yhdistelmä huomenna, moi huomisen minä //Moi
        pankkikortti.lisaaDebitti(debit);
    }
    
    /**
     * lisää pankkiin uuden luottokortin
     * @param credit on Credit-luokasta napattu.    
     */
    public void lisaaCredit(Credit credit) {
        pankkikortti.lisaaKreditti(credit);
    }
    
    /**
     * lisää pankkiin uuden yhditelmäkortin
     * @param yhdistelma on Yhdistelmä-luokasta napattu.
     */
    public void lisaaYhdistelma(Yhdistelmä yhdistelma) {
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
    
    /**
     * Palauttaa asiakkaan tietyssä indeksissä
     * @param i indeksi
     * @return asiakkaan tietystä indeksistä
     * @throws IndexOutOfBoundsException jos i on asiakas listan ulkopuolella
     */
    public Asiakas annaAsiakas(int i) throws IndexOutOfBoundsException {
    	return asiakkaat.anna(i);
    }
    
    /**
     * Lukee pankin tiedot tiedostosta KESKEN
     * @param nimi tiedoston nimi
     * @throws SailoException jos liikaa (Dani?)
     */    
    public void lueTiedostosta(String nimi) throws SailoException {
    	asiakkaat.lueTiedostosta(nimi);
    }
    
    /**
     * Tallettaa pankin tiedot tiedostoon KESKEN
     * @throws SailoException jos liikaa (Dani?)
     */
    public void talleta() throws SailoException {
    	asiakkaat.talleta();
    }
    
    /**
     * Testaan, toimiiko aliohjelmat ennen kun laitan GUIControlleriin Pääikkunaan.
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
            Pankkikortti pankkikortti = new Pankkikortti();
            Debit debit = new Debit();
            debit.vastaaDebit(jaskaJokunen1.getTunnusNro());
            
            pankkikortti.lisaaDebitti(debit);
            
            
            for (int i = 0; i < pankki.getAsiakkaat(); i++) {
                Asiakas asiakas = pankki.annaAsiakas(i);
                System.out.println("Jäsen paikassa: " + i);
                asiakas.tulosta(System.out);
            }
            
            System.out.println("============= Pankkikortti testi =================");
            
            List<Debit> debitit = pankkikortti.annaDebitti(jaskaJokunen1.getTunnusNro());
            
            for (Debit deb : debitit) {
                System.out.print(deb.getAsiakasNro() + " ");
                deb.tulosta(System.out);
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
