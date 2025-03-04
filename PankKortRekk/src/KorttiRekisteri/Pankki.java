package KorttiRekisteri;

public class Pankki {
	private final Asiakkaat asiakkaat = new Asiakkaat();
	
	public int getAsiakkaat() {
		return asiakkaat.getLkm();
	}
	
    public int poista(@SuppressWarnings("unused") int nro) {
          return 0;
    }
 
    public void lisaa(Asiakas asiakas) throws SailoException {
    	asiakkaat.lisaa(asiakas);
    }
    
    public Asiakas annaAsiakas(int i) throws IndexOutOfBoundsException {
    	return asiakkaat.anna(i);
    }
    
    public void lueTiedostosta(String nimi) throws SailoException {
    	asiakkaat.lueTiedostosta(nimi);
    }
    
    public void talleta() throws SailoException {
    	asiakkaat.talleta();
    }
}