package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    @Test
    public void kortinSaldoAlussaOikein() {

	// Alussa määritelty saldo 10 on sentteinä

	assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
	kortti.lataaRahaa(1000); // Ladataan 10€
	assertEquals("saldo: 10.10", kortti.toString());
    }

    @Test
    public void saldoVaheneeOikein() {
	kortti.otaRahaa(10);
	assertEquals("saldo: 0.0", kortti.toString());

	// hauska leikki: testata silloin kun kortille jää 5 snt

    }

    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
	kortti.otaRahaa(100);
	assertEquals("saldo: 0.10", kortti.toString());
    }

    @Test
    public void metodiPalauttaaTrueJosLaillinenOtto() {
	assertEquals(true, kortti.otaRahaa(5));
    }

    @Test
    public void metodiPalauttaaFalsoJosLaitonOtto() {
	assertEquals(false, kortti.otaRahaa(100));
    }

    @Test
    public void saldoPalauttaaSaldon() {
	assertEquals(10, kortti.saldo());
    }

}
