package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate paate;

    @Before
    public void setUp() {
        paate = new Kassapaate();
    }

    @Test
    public void alkutilaOikein() {
        assertEquals(paate.kassassaRahaa(), 100000); // 1000€
        assertEquals(paate.maukkaitaLounaitaMyyty(), 0);
        assertEquals(paate.edullisiaLounaitaMyyty(), 0);
    }

    // Maukkaan oston testit (käteinen)

    @Test
    public void maukkaanLounaanOstamisestaVaihtorahatJosKateista() {
        assertEquals(100, paate.syoMaukkaasti(500));
    }

    @Test
    public void maukkaanLounaanOstamisestaRahatTakaisinJosEiKateista() {
        assertEquals(399, paate.syoMaukkaasti(399));
    }

    @Test
    public void maukkaanLounaanOstamisestaKertyyRahaaJosKateista() {
        paate.syoMaukkaasti(500);
        assertEquals(100400, paate.kassassaRahaa());
    }
 
    @Test
    public void maukkaanLounaanOstamisestaEiKerryRahaaJosEiKateista() {
        paate.syoMaukkaasti(399);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void maukasKerryttaaJosKateista() {
        paate.syoMaukkaasti(400);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukasEiKerrytaJosEiKateista() {
        paate.syoMaukkaasti(399);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    // Maukkaan oston testit (kortti)

    @Test
    public void maukkaanLounaanOstaminenToimiiJosRahaa() {
        assertEquals(true, paate.syoMaukkaasti(new Maksukortti(400)));
    }

    @Test
    public void maukkaanLounaanOstaminenEiToimiiJosEiRahaa() {
        assertEquals(false, paate.syoMaukkaasti(new Maksukortti(399)));
    }

    @Test
    public void maukkaanLounaanOstaminenVeloittaaJosRahaa() {
        Maksukortti kortti = new Maksukortti(400);
        paate.syoMaukkaasti(kortti);
        assertEquals("saldo: 0.0", kortti.toString());
    }

    @Test
    public void maukkaanLounaanOstaminenEiVeloitaJosEiRahaa() {
        Maksukortti kortti = new Maksukortti(399);
        paate.syoMaukkaasti(kortti);
        assertEquals("saldo: 3.99", kortti.toString());
    }

    @Test
    public void maukkaanLounaanOstaminenKerryttaaJosRahaa() {
        paate.syoMaukkaasti(new Maksukortti(400));
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maukkaanLounaanOstaminenEiKerrytaJosEiRahaa() {
        paate.syoMaukkaasti(new Maksukortti(399));
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kassaanEiKerryRahaaJosMaukasKortilla() {
        paate.syoMaukkaasti(new Maksukortti(400));
        assertEquals(100000, paate.kassassaRahaa());
    }

    // Edullisen oston testit (käteinen)

    @Test
    public void edullisenLounaanOstamisestaVaihtorahatJosKateista() {
        assertEquals(260, paate.syoEdullisesti(500));
    }

    @Test
    public void edullisenLounaanOstamisestaRahatTakaisinJosEiKateista() {
        assertEquals(239, paate.syoEdullisesti(239));
    }

    @Test
    public void edullisenLounaanOstamisestaKertyyRahaaJosKateista() {
        paate.syoEdullisesti(500);
        assertEquals(100240, paate.kassassaRahaa());
    }
 
    @Test
    public void edullisenLounaanOstamisestaEiKerryRahaaJosEiKateista() {
        paate.syoEdullisesti(239);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void edullinenKerryttaaJosKateista() {
        paate.syoEdullisesti(240);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullinenEiKerrytaJosEiKateista() {
        paate.syoEdullisesti(239);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    // Edullisen oston testit (kortti)

    @Test
    public void edullisenLounaanOstaminenToimiiJosRahaa() {
        assertEquals(true, paate.syoEdullisesti(new Maksukortti(240)));
    }

    @Test
    public void edullisenLounaanOstaminenEiToimiiJosEiRahaa() {
        assertEquals(false, paate.syoMaukkaasti(new Maksukortti(239)));
    }

    @Test
    public void edullisenLounaanOstaminenVeloittaaJosRahaa() {
        Maksukortti kortti = new Maksukortti(240);
        paate.syoEdullisesti(kortti);
        assertEquals("saldo: 0.0", kortti.toString());
    }

    @Test
    public void edullisenLounaanOstaminenEiVeloitaJosEiRahaa() {
        Maksukortti kortti = new Maksukortti(239);
        paate.syoEdullisesti(kortti);
        assertEquals("saldo: 2.39", kortti.toString());
    }

    @Test
    public void edullisenLounaanOstaminenKerryttaaJosRahaa() {
        paate.syoEdullisesti(new Maksukortti(240));
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void edullisenLounaanOstaminenEiKerrytaJosEiRahaa() {
        paate.syoEdullisesti(new Maksukortti(239));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void kassaanEiKerryRahaaJosEdullinenKortilla() {
        paate.syoEdullisesti(new Maksukortti(240));
        assertEquals(100000, paate.kassassaRahaa());
    }

    // Kortin lataukseen liittyvät testit

    @Test
    public void kortinLatausKasvattaaKortinSaldoa() {
        Maksukortti kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, 1000); // Ladataan kortille 10€
        assertEquals("saldo: 10.0", kortti.toString());
    }

    @Test
    public void kortinLatausKasvattaaPaatteenKateismaaraa() {
        Maksukortti kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, 1000); // Ladataan kortille 10€
        assertEquals(101000, paate.kassassaRahaa());
    }

    @Test
    public void kortinLatausEiMuutaSaldoaJosNegatiivinenLataus() {
        Maksukortti kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, -1000); // Ladataan kortille -10€
        assertEquals("saldo: 0.0", kortti.toString());
    }

    @Test
    public void kortinLatausEiMuutaPaatteenKateismaaraaJosNegatiivinenLataus() {
        Maksukortti kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, -1000); // Ladataan kortille -10€
        assertEquals(100000, paate.kassassaRahaa());
    }

}
