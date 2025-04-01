package KorttiRekisteri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

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
	
	/*
	 * Lisää uuden asiakkaan rekisteriin
	 * Heittää virheen jos asiakkaita on liikaa
	 */
	
	public void lisaa(Asiakas asiakas) throws SailoException{
		if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
		alkiot[lkm] = asiakas;
		lkm++;
	}
	
	// Palauttaa jäsenen indeksin perusteella
	
	public Asiakas anna(int i) throws IndexOutOfBoundsException{
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return alkiot[i];
	}
	
	// Lukee asiakkaan tiedostosta (Kesken)
	
	/**
	 * @param hakemisto aa
	 * @throws SailoException aa
	 */
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/asiakkaat.dat";
		File ftied = new File(tiedostonNimi);
		try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
		    String s = "";
		    s = fi.nextLine();
		    Asiakas asiakas = new Asiakas();
		    asiakas.parse(s);
		    lisaa(asiakas);
		} catch (FileNotFoundException e) {
		    throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
		//} catch (IOException e) {
		//    throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage())
		}
	}
	
	/**
	 * Tallentaa asiakkaan tiedostoon
	 * @param tiednimi aa
	 * @throws SailoException aa
	 */
	public void tallenna(String tiednimi) throws SailoException {
		File ftied = new File(tiednimi + "/asiakkaat.dat");
		try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
		    for (int i = 0; i < getLkm(); i++) {
		        Asiakas asiakas = anna(i);
		        fo.println(asiakas.toString());
		    }
		} catch (FileNotFoundException ex) {
		    throw new SailoException("Tiedosto " + ftied.getAbsolutePath() + "ei toimi");
		}
	}
	
	// Palauttaa asiakkaiden määrää
	
	public int getLkm() {
		return lkm;
	}
	
	/**
	 * @param args ei tee mitään
	 */
	public static void main(String[] args) {
	    Asiakkaat asiakkaat = new Asiakkaat();
	    
	    try {
            asiakkaat.lueTiedostosta("korhonen");
        } catch (SailoException e) {
            System.err.println("Ei voi lukea " + e.getMessage());
        }
	    
	    Asiakas erik = new Asiakas(), erik2 = new Asiakas();
	    
	    erik.rekisteroi();
	    erik.vastaaErik();
	    
	    erik2.rekisteroi();
	    erik2.vastaaErik();
	    
	    try {
	        asiakkaat.lisaa(erik);
	        asiakkaat.lisaa(erik2);
	        
	        System.out.println("=================== Asiakkaat testi =======================");
	        
	        for (int i = 0; i < asiakkaat.getLkm(); i++) {
	            Asiakas asiakas = asiakkaat.anna(i);
	            System.out.println("Asiakasnumero: " + i);
	            asiakas.tulosta(System.out);
	        }
	    } catch (SailoException ex) {
	        System.out.println(ex.getMessage());
	    }
	    try {
	        asiakkaat.tallenna("korhonen");
	    } catch (SailoException e) {
	        e.printStackTrace();
	    }
	}
	
}