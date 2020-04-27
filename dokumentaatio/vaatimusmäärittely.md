# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen tarkoitus on tuottaa karttoja (tai ainakin karttapohjia) roolipelikampanjoihin. 

## Perusversion toiminnallisuus

* Ohjelmalla on graafinen käyttöliittymä [tehty]
* Käyttäjä voi generoida kartan [tehty]
    * Oletusasetuksilla syntyy korkeuskartta meren ympäröimästä maamassasta, johon muodostuisi vuoristoja ja vesistöjä [tehty]
    * Eroosion ja vuorten poimutuksen keskinäisen suhteen pitäisi olla vaihdettavissa [tehty]
    * Simulaatiota voi askeltaa eteenpäin [tehty]
        * Päädyin siihen, että säädettävällä eroosiomäärällä voi saada aikaan saman tuloksen käyttäjäystävällisemmin ja kooditasolla selkeämmin.
    * Satunnaislukugeneraattorin siemenarvoa voi vaihtaa [tehty]
* Käyttäjä voi tallentaa kartan [tehty]
    * Kartan pitäisi olla tallennettavissa sekä kuvatiedostona (png), että työtiedostona (joka sisältäisi kartan historian) [tehty]
* Käyttäjä voi ladata työtiedoston [tehty]
    * Työtiedostosta voi ladata kartan aiempia versioita [tehty]

## Jatkokehitysideoita

Näitä olisi tarkoitus toteuttaa jo kurssin aikana sen mukaan, mihin riittää aikaa ja motivaatiota:

* Yhteensopivuus OBJ-standardin kanssa. PNG-korkeuskartat ovat yllättävän operaatio värisyvyyssyistä. Karttaeditorista saa tuotettua suoraan 3D-malleja. [tehty]

* Yhteensopivuus [The Battle for Wesnoth](https://wesnoth.org/)-pelin karttaeditorin kanssa. Generoidusta kartasta voisi laskea Wesnoth-karttatiedoston, jonka peli osaa piirtää pikseligrafiikkana. Itse karttaformaatti on sen verran yksinkertaisen oloinen, että tämä lienee helppo toteuttaa. Wesnothin komentoriviohjelma pystyy muuntamaan karttatiedostoja PNG-kuviksi, joten tämä voisi olla napin painalluksella tapahtuvaa. Vaikein lienee muuntaa simulaation rasteri kuusikulmiolaatoiksi mielekkäällä tavalla.
    * Mikäli tämä tapahtuu, niin lienee helppoa muuntaa karttoja muihin kuusikulmiolaatoitukseen perustuviin käyttöihin, kuten esimerkiksi [Hyground](https://www.hygroundtiles.com/) -3D-laatoitukseksi.

* Lisää simuloitavia ilmiöitä (näistä on helpohko napsia yksittäisiä toteutettavaksi):
    * Maastonmuotojen ilmastovaikutukset
        * Vuoristojen vaikutukset sateisiin
        * Koriolis-ilmiö
        * Metsien, soiden, arojen ja aavikoiden jakautuminen ilmasto-olojen perusteella.
            * Tämän voisi integroida Wesnothin biomien kanssa.
    * Laattatektoniikkaa
        * Tulivuoria
            * Kuuman pisteen tulivuoren aiheuttamat saaristot
