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
	
	private static final int 	MAX_KORTIT 					= 20; //ei implementoitu ainakaan viel
	private int 				lkm 						= 3; //tää olennaisempi
	private String 				tiedostonNimi 				= "";
	private Debit				debit[] 					= new Debit[MAX_KORTIT];
	private Credit				credit[] 					= new Credit[MAX_KORTIT];
	private Yhdistelmä			yhdistelmä[] 				= new Yhdistelmä[MAX_KORTIT];
	
	/**
    * alustaa olion
    */
	
    public Pankkikortti() {
        // Jää tyhjäksi toistaiseksi
    }
    

    private Collection<Debit> debitLista = new ArrayList<Debit>(); //UUSI JUTTU: saa nähdä, miten tää soveltuu sinne taulukkoon muiden kanssa
    
    /**
     * @param deb lisättävä debitkortti
     */
    public void lisaaDebitti(Debit deb) { //Debitkorttien logiikka, lisää uuden pankkikortin kokoelmaan
        debitLista.add(deb);
    }
    
    /**
     * Palauttaa listan tietylle asiakkaalle kuuluvista korteista
     * @param tunnusNro tunnusnumero
     * @return löydetyt numerot
     */
    public List<Debit> annaDebitti(int tunnusNro) { 
        List<Debit> loydetyt = new ArrayList<Debit>();
        for (Debit deb : debitLista) { //Käy läpi kaikki kortit ja...
            if (deb.getAsiakasNro() == tunnusNro) { //...vertailee asiakkaan tunnusnumeroa
                loydetyt.add(deb); //lisää löydetyn
            }
        }
        return loydetyt; //palauttaa löydetyn
    }

	private Collection<Credit> creditLista = new ArrayList<Credit>(); //UUSI JUTTU: saa nähdä, miten tää soveltuu sinne taulukkoon muiden kanssa
	
	/**
	 * @param cred lisättävä luottokortti
	 */
	public void lisaaKreditti(Credit cred) { //Luottokorttien logiikka, lisää uuden pankkikortin kokoelmaan
	    creditLista.add(cred);
	}
	
	/**
	 * Palauttaa listan tietylle asiakkaalle kuuluvista korteista
	 * @param tunnusNro tunnusnumero
	 * @return löydetyt numerot
	 */
	public List<Credit> annaKreditti(int tunnusNro) {
	    List<Credit> loydetyt = new ArrayList<Credit>();
	    for (Credit cred : creditLista) { //Käy läpi kaikki kortit ja...
	        if (cred.getAsiakasNro() == tunnusNro) { //...vertailee asiakkaan tunnusnumeroa
	            loydetyt.add(cred); //lisää löydetyn
	        }
	    }
	    return loydetyt; //palauttaa löydetyn
	}
	

    private Collection<Yhdistelmä> yhdistelmaLista = new ArrayList<Yhdistelmä>();
    
    /**
     * @param yhd lisättävä kredittikortti
     */
    public void lisaaYhdistelma(Yhdistelmä yhd) { //Yhdistelmäkorttien logiikka, lisää uuden pankkikortin kokoelmaan
        yhdistelmaLista.add(yhd);
    }
    
    /**
     * Palauttaa listan tietylle asiakkaalle kuuluvista korteista
     * @param tunnusNro tunnusnumero
     * @return löydetyt numerot
     */
    public List<Yhdistelmä> annaYhdistelma(int tunnusNro) {
        List<Yhdistelmä> loydetyt = new ArrayList<Yhdistelmä>();
        for (Yhdistelmä yhd : yhdistelmaLista) { //Käy läpi kaikki kortit ja...
            if (yhd.getAsiakasNro() == tunnusNro) { //...vertailee asiakkaan tunnusnumeroa
                loydetyt.add(yhd); //lisää löydetyn
            }
        }
        return loydetyt; //palauttaa löydetyn
    }
    
    
    // Toistaiseksi SailoException ei vielä toimi -> Korjattavaksi vaiheessa HT6
    // Pitäisi toimia niin, että per asiakas max 10 korttia, eikä yhteensä koko pankissa max 10 korttia
	
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