package KorttiRekisteri;

 /* AgoBankin asiakkaat, joka osaa mm. lisätä uuden jäsenen (ei vielä)
  * 
  * 
  * 
  */

public class Asiakkaat {
	
	private static final int 	MAX_ASIAKKAAT = 20;
	private int 				lkm = 0;
	private String 				tiedostonNimi = "";
	private Asiakas				alkiot[] = new Asiakas[MAX_ASIAKKAAT];
	
	public Asiakkaat() {
		// Jää tyhjäksi
	}
	
	public void lisaa(Asiakas asiakas) throws SailoException{
		if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
		alkiot[lkm] = asiakas;
		lkm++;
	}
	
	public Asiakas anna(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return alkiot[i];
	}
	
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/nimet.dat";
		throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
	}
	
	public void talleta() throws SailoException {
		throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
	}
	
	public int getLkm() {
		return lkm;
	}
	
}