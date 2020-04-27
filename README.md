# MapApp

Sovellus pyrkii olemaan karttageneraattori roolipelikampanjoita varten. Se tuottaa pelikelpoisia karttoja erilaisten kohinamallien ja eroosion simuloimisen avulla.

# Käyttöohje

[Käyttöohje](dokumentaatio/käyttöohje.md)

# Releaset

[Viikko 5](https://github.com/nomelif/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/nomelif/ot-harjoitustyo/releases/tag/viikko6)

# Arkkitehtuuridiagrammi

[Arkkitehtuuridokumentti](dokumentaatio/arkkitehtuuri.md)

# Työaikakirjanpito

[Työaikakirjanpito](dokumentaatio/Työaikakirjanpito.md)

# Vaatimusmäärittely

[Vaatimusmäärittely](dokumentaatio/vaatimusmäärittely.md)

# Ohjelman tuotokset

[Tällä tiedostolla](dokumentaatio/demot/Viekas%20kettu.map) ohjelma tuottaa seuraavan kuvan ja .obj -tiedon:

* ![esimerkkikuva](dokumentaatio/demot/Viekas%20kettu.png)
* [obj-tiedosto](dokumentaatio/demot/Viekas%20kettu.obj), sen esikatselu [Sketchfabissa](https://sketchfab.com/3d-models/mapapp-demo-18ee7da3680a41c4a5a3bfdbac9c4516).

Yleisesti .obj-tiedostoja voi tarkastella [esimerkiksi tällä nettikilkkeellä](http://masc.cs.gmu.edu/wiki/ObjViewer).

# Komentoriviltä suoritettavat toimenpiteet

(Kaikissa alla olevissa oletetaan, että ollaan kansiossa `MapApp`)

Omalla koneellani testeihin kuluu kymmenisen sekuntia ja etäpalvelimella viitisentoista.

```
mvn test
```

Jacoco suorittuu komennolla:

```
mvn test jacoco:report
```

Javadoc päivittyy komennolla:

```
mvn javadoc:javadoc
```

Suoritettavan `.jar-` tiedoston saa aikaan komennolla:

```
mvn package
```

Tämän saa sitten suortettua komennolla:

```
java -jar target/MapApp*.jar
```
