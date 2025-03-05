package KorttiRekisteri;


/*
* Kerho-luokka, joka huolehtii jäsenistöstä.  Pääosin kaikki metodit
* ovat vain "välittäjämetodeja" jäsenistöön.
*/

public class Pankki {
	private final Asiakkaat asiakkaat = new Asiakkaat();
	
	// Palauttaa asiakkaiden lukumäärän
	
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
 
    // Lisää pankkiin uuden asiakkaan
    
    public void lisaa(Asiakas asiakas) throws SailoException {
    	asiakkaat.lisaa(asiakas);
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
}