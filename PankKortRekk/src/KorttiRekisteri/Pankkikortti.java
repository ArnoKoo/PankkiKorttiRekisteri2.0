package KorttiRekisteri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Dani vittu katso ens kerralla et keltaset viivat katoavat.
 * Pankkikortti toimii samalla tavalla kuin asiakkaat
 * @author OMISTAJA
 * @version 10 Mar 2025
 *
 */
public class Pankkikortti {
	
	private static final int 	MAX_KORTIT 					= 40;
	private int 				lkm 						= 0;
	private String 				tiedostonNimi 				= "";
	private Debit				debit[] 					= new Debit[MAX_KORTIT];
	private Credit				credit[] 					= new Credit[MAX_KORTIT];
	private Yhdistelmä			yhdistelmä[] 				= new Yhdistelmä[MAX_KORTIT];
	

	
	//-------------------------------------------------------------------------------------------------------------------------------------- Arskan juttuja
	
	
	/**
    * 
    */
    public Pankkikortti() {
        // Jää tyhjäksi toistaiseksi
    }
    
    //Debitkorttien logiikka
    private Collection<Debit> debitLista = new ArrayList<Debit>(); //UUSI JUTTU: saa nähdä, miten tää soveltuu sinne taulukkoon muiden kanssa
    
    /**
     * @param deb lisättävä debitkortti
     */
    public void lisaaDebitti(Debit deb) {
        debitLista.add(deb);
    }
    
    /**
     * @param tunnusNro tunnusnumero
     * @return löydetyt numerot
     */
    public List<Debit> annaDebitti(int tunnusNro) {
        List<Debit> loydetyt = new ArrayList<Debit>();
        for (Debit deb : debitLista) { //edellyyttää iteraattorin olemassaoloa 
            if (deb.getAsiakasNro() == tunnusNro) {
                loydetyt.add(deb);
            }
        }
        return loydetyt;
    }
    
    
    //Luottokorttien logiikka
	private Collection<Credit> creditLista = new ArrayList<Credit>(); //UUSI JUTTU: saa nähdä, miten tää soveltuu sinne taulukkoon muiden kanssa
	
	/**
	 * @param cred lisättävä luottokortti
	 */
	public void lisaaKreditti(Credit cred) {
	    creditLista.add(cred);
	}
	
	/**
	 * @param tunnusNro tunnusnumero
	 * @return löydetyt numerot
	 */
	public List<Credit> annaKreditti(int tunnusNro) {
	    List<Credit> loydetyt = new ArrayList<Credit>();
	    for (Credit cred : creditLista) { //edellyyttää iteraattorin olemassaoloa 
	        if (cred.getAsiakasNro() == tunnusNro) {
	            loydetyt.add(cred);
	        }
	    }
	    return loydetyt;
	}
	
	
	//Yhdistelmäkorttien logiikka
    private Collection<Yhdistelmä> yhdistelmaLista = new ArrayList<Yhdistelmä>();
    
    /**
     * @param yhd lisättävä kredittikortti
     */
    public void lisaaYhdistelma(Yhdistelmä yhd) {
        yhdistelmaLista.add(yhd);
    }
    
    /**
     * @param tunnusNro tunnusnumero
     * @return löydetyt numerot
     */
    public List<Yhdistelmä> annaYhdistelma(int tunnusNro) {
        List<Yhdistelmä> loydetyt = new ArrayList<Yhdistelmä>();
        for (Yhdistelmä yhd : yhdistelmaLista) { //edellyyttää iteraattorin olemassaoloa 
            if (yhd.getAsiakasNro() == tunnusNro) {
                loydetyt.add(yhd);
            }
        }
        return loydetyt;
    }
	
	
	//-------------------------------------------------------------------------------------------------------------------------------------- Arskan jutut loppuvat
	
	/**
	 * Lisää debit kortin, jos on liikaa kortteja niin heittää virheen
	 * @param kortti kortti debittiä varten
	 * @throws SailoException jos liikaa kortteja
	 */
	public void lisaaDebit(Debit kortti) throws SailoException{
		if (lkm >= debit.length) throw new SailoException("Liikaa alkioita");
		debit[lkm] = kortti;
		lkm++;
	}
	
	/**
	 * Lisää credit kortin, jos on liikaa kortteja niin heittää virheen
	 * @param kortti kortti credittiä varten
	 * @throws SailoException jos liikaa kortteja
	 */
	public void lisaaCredit(Credit kortti) throws SailoException{
		if (lkm >= credit.length) throw new SailoException("Liikaa alkioita");
		credit[lkm] = kortti;
		lkm++;
	}
	
	/**
	 * Lisää yhdistelmä kortin, jos on liikaa kortteja niin heittää virheen
	 * @param kortti kortti yhdistelmäkorttia varten
	 * @throws SailoException jos liikaa kortteja
	 */
	public void lisaaYhdistelmä(Yhdistelmä kortti) throws SailoException{
		if (lkm >= yhdistelmä.length) throw new SailoException("Liikaa alkioita");
		yhdistelmä[lkm] = kortti;
		lkm++;
	}
		
	
	//Dani, mitä nää alla olevat tekevät?
	
	/**
	 * lukee tiedostosta (TODO)
	 * @param hakemisto tiedostonNimi kiinni tässä (Dani, täydennä)
	 * @throws SailoException ei kye lukemaan (Dani, täydennä)
	 */
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/kortit.dat";
		throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
	}
	
	/**
	 * Talentaa tiedostoon (TODO)
	 * @throws SailoException ei kye tallentamaan (Dani, täydennä)
	 */	
	public void talleta() throws SailoException {
		throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
	}
	
    /**
     * Testiohjelma harrastuksille UUSI JUTTU: Testataan, tulostuuko oikein. Lainattu Vesan esimerkkiohjelmasta ja muunneltu.
     * @param args ei käytössä
     */
    public static void main(String[] args) { //säilytän muuttujien nimet vaan siksi, että hahmotan mikä on mikä
        //Debitti
        Pankkikortti harrasteet1 = new Pankkikortti();
        Debit pitsi1 = new Debit(1);
        pitsi1.vastaaDebit(2);
        
        //Luottokortti
        Pankkikortti harrasteet2 = new Pankkikortti();
        Credit pitsi2 = new Credit(1);
        pitsi2.vastaaCredit(2);
        
        //Yhdistelmäkortti
        Pankkikortti harrasteet3 = new Pankkikortti();
        Yhdistelmä pitsi3 = new Yhdistelmä(1);
        pitsi3.vastaaYhdistelmä(2);

        
        harrasteet1.lisaaDebitti(pitsi1);
        harrasteet2.lisaaKreditti(pitsi2);
        harrasteet3.lisaaYhdistelma(pitsi3);

        System.out.println("============= Pankkikortti testi =================");
        
        List<Debit> debitit = harrasteet1.annaDebitti(1);
        for (Debit deb : debitit) {
            System.out.print(deb.getAsiakasNro() + " ");
            deb.tulosta(System.out);
        }

        List<Credit> kreditit = harrasteet2.annaKreditti(1);
        for (Credit cred : kreditit) {
            System.out.print(cred.getAsiakasNro() + " ");
            cred.tulosta(System.out);
        }
        
        List<Yhdistelmä> yhdistelmat = harrasteet3.annaYhdistelma(1);
        for (Yhdistelmä yhd : yhdistelmat) {
            System.out.print(yhd.getAsiakasNro() + " ");
            yhd.tulosta(System.out);
        }

    }
	
	/**
	 * @return Palauttaa lukumäärän
	 */	
	public int getLkm() {
		return lkm;
	}
	
}