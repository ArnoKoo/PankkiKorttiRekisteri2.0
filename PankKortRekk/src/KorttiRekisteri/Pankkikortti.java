package KorttiRekisteri;

 /* Tämän osion voi vielä korjata, ehkä noi metodit voisi tehdä, ettei jokaiselle kortille ole eri metodi
  * 
  * Pankkikortti toimii samalla tavalla kuin asiakkaat
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
	
	// Lisää debit kortin, jos on liikaa kortteja niin heittää virheen
	
	public void lisaaDebit(Debit kortti) throws SailoException{
		if (lkm >= debit.length) throw new SailoException("Liikaa alkioita");
		debit[lkm] = kortti;
		lkm++;
	}
	
	// Lisää credit kortin, jos on liikaa kortteja niin heittää virheen
	
	public void lisaaCredit(Credit kortti) throws SailoException{
		if (lkm >= credit.length) throw new SailoException("Liikaa alkioita");
		credit[lkm] = kortti;
		lkm++;
	}
	
	// Lisää yhdistelmä kortin, jos on liikaa kortteja niin heittää virheen
	
	public void lisaaYhdistelmä(Yhdistelmä kortti) throws SailoException{
		if (lkm >= yhdistelmä.length) throw new SailoException("Liikaa alkioita");
		yhdistelmä[lkm] = kortti;
		lkm++;
	}
	
	// Antaa debit kortin tietyssä indeksissä, ellei indeksiä ole olemassa
	
	public Debit annaDebit(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return debit[i];
	}
	
	// Antaa credit kortin tietyssä indeksissä, ellei indeksiä ole olemassa
	
	public Credit annaCredit(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return credit[i];
	}
	
	// Antaa yhdistelmä kortin tietyssä indeksissä, ellei indeksiä ole olemassa
	
	public Yhdistelmä annaYhdistelmä(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return yhdistelmä[i];
	}
	
	// lukee tiedostosta (TODO)
	
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/kortit.dat";
		throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
	}
	
	// Talentaa tiedostoon (TODO)
	
	public void talleta() throws SailoException {
		throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
	}
	
	// Palauttaa lukumäärän
	
	public int getLkm() {
		return lkm;
	}
	
}