package KorttiRekisteri;

 /* AgoBankin asiakkaat, joka osaa mm. lisätä uuden jäsenen (ei vielä)
  * 
  * 
  * 
  */

public class Pankkikortti {
	
	private static final int 	MAX_KORTIT 					= 40;
	private int 				lkm 						= 0;
	private String 				tiedostonNimi 				= "";
	private Debit				debit[] 					= new Debit[MAX_KORTIT];
	private Credit				credit[] 					= new Credit[MAX_KORTIT];
	private Yhdistelmä			yhdistelmä[] 				= new Yhdistelmä[MAX_KORTIT];
	
	public Pankkikortti() {
		// Jää tyhjäksi
	}
	
	public void lisaaDebit(Debit kortti) throws SailoException{
		if (lkm >= debit.length) throw new SailoException("Liikaa alkioita");
		debit[lkm] = kortti;
		lkm++;
	}
	
	public void lisaa(Credit kortti) throws SailoException{
		if (lkm >= credit.length) throw new SailoException("Liikaa alkioita");
		credit[lkm] = kortti;
		lkm++;
	}
	
	public void lisaa(Yhdistelmä kortti) throws SailoException{
		if (lkm >= yhdistelmä.length) throw new SailoException("Liikaa alkioita");
		yhdistelmä[lkm] = kortti;
		lkm++;
	}
	
	public Debit annaDebit(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return debit[i];
	}
	
	public Credit annaCredit(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return credit[i];
	}
	
	public Yhdistelmä annaYhdistelmä(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return yhdistelmä[i];
	}
	
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/kortit.dat";
		throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
	}
	
	public void talleta() throws SailoException {
		throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
	}
	
	public int getLkm() {
		return lkm;
	}
	
}