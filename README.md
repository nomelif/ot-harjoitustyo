# MapApp

Sovellus pyrkii olemaan karttageneraattori roolipelikampanjoita varten. Se tuottaa pelikelpoisia karttoja erilaisten kohinamallien ja eroosion simuloimisen avulla.

# Työaikakirjanpito

[Työaikakirjanpito](dokumentaatio/Työaikakirjanpito.md)

# Vaatimusmäärittely

[Vaatimusmäärittely](dokumentaatio/vaatimusmäärittely.md)

# Ohjelman suoritus

Minulla on ollut tietyt vaikeudet saada testattua ohjelman suorittuvuus yliopiston tekniikalla. (Omalla koneellani on NixOS.) Yliopiston etäpalvelimista [vdi.helsinki.fi](https://vdi.helsinki.fi) oli ainoa jolla sain ohjelman suorittumaan (kokeilin melkinpaatta ja -karia komentoriviltä). Silläkin ainoastaan TMC-beans suostui suorittamaan koodini vihreän nappulan menetelmällä. Omalla koneellani toimiva Maven-loitsu oli:

```mvn compile exec:java -Dexec.mainClass=ui.Main```

Ohjelman suorituksessa sen tarjoamilla oletusasetuksilla kestää minun koneellani osapuilleen kaksi minuuttia. Ohjelma antaa väliakatiedotetta laskennan edistymisestä eli on helppo nähdä onko se kaatunut. Ulos tuleva korkeuskartta ei välttämättä ole kummoinen, mutta sen projektio 3D-tasoksi on kiintoisa. Tässä on esimerkki oletusarvoilla ja siemenarvolla `1337` syntyvä tulos:

![esimerkkikuva]("dokumentaatio/1337.jpg")

# Testien suoritus ja Jacoco

Testien pitäisi suorittua yksinkertaisesti `mvn test` -loitsulla. Omalla koneellani tähän kuluu kymmenisen sekuntia ja etäpalvelimella viitisentoista. Jacoco suorittuu komennolla `mvn test jacoco:report`.


