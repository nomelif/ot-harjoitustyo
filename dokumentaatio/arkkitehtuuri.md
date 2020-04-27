# Pakkausrakenne

![pakkauskaavio](pakkauskaavio.svg)

Käyttöliittymä elää `ui`-pakkauksessa. `file`-pakkaus huolehtii tiedostosta ja siihen liittyvistä käyttöliittymäasioista kuten versiohistoriasta. `map`-pakkaus huolehtii karttoihin liittyvästä laskennasta.

# Käyttöliittymä

Käyttöliittymä on sikäli yksinkertainen, ettei siihen liity näkymävaihdoksia tai kirjautumisia. Kaikki luodaan ohjelmallisesti `ui`-pakkauksen `Ui.java`-tiedostossa. Tällä hetkellä tiedostojen fyysinen manipulaatio kulkee `Ui.java`-tiedoston läpi sen takia, että itse tiedostojen käsittely tapahtuu Gson-kirjastossa.

# Sovelluslogiikka

Oikeaa sovellusloogista laskentaa tapahtuu luokissa `Map` ja `MapTask`. `Map` toteuttaa kartan matalan tason manipulaation. `MapTask` on korkeamman tason tekniikkaa ja antaa yhdessä `OptionCollection`-luokan kanssa puhtaan funktionaalisen rajapinnan karttojen luomiseen. 

# Tiedostojen tallentaminen

MapApp käsittelee monenlaisia tiedostoja. Se lukee ja kirjoittaa JSON-muotoisia `.map`-työtiedostoja. Tämä tapahtuu käytännössä hyödyntämällä Gson-kirjastoa `MapAppFile`-olioiden serialisoimiseen. `MapAppFile` on suunniteltu niin, ettei sillä ole muuta tilaa kuin nykyiseen muokkaukseen osoittava kokonaisluku ja `ArrayList<OptionCollection>` tyyppinen historia. Näin se on triviaalisesti tallennettavissa hyvinkin elegantiksi JSON-tekstiksi. (De)serialisaatio tapahtuu niin puhtaasti ulkoisessa kirjastossa, etten kokenut mielekkääksi tehdä siitä omaa luokkaansa.

Lisäksi MapApp osaa kirjoittaa karttoja PNG-kuvina ja Wavefront OBJ -3D-malleina. Molemmissa tapauksissa `Map`-luokka osaa tuottaa sopivan muotoisia tietueita: PNG-kuvan tapauksessa `BufferedImagen` ja OBJ-mallin tapauksessa merkkijonon. Näiden kirjoittaminen tiedostoon on jälleen sen verran triviaalinen tehtävä, että sen eriyttäminen omaksi luokakseen tuntui mutkistavan koodia enemmän kuin selkeyttävän. OBJ-malli on siitä mukava formaatti, että se on tekstipohjainen ja helppo tuottaa korkeuskartasta.

# Luokkakaavio

![arkkitehtuuri](arkkitehtuuri.svg)

# Prosesseista

Eroosion laskeminen on sen verran pitkä toiminto, että se piti eriyttää omalle säikeeleen. Siihen liittyvä tapahtumasarja on kuvattu alla olevaan sekvenssikaavioon.

![sekvenssikaavio](sekvenssikaavio.svg)
<!--

[Application]<-[Ui||start(window: Stage); static run()]1--[Map||index(x: int, y: int): double; getWidth(): int; getHeight(): int; makePerlin(scale: double, influence: double, offset: double); waterCutoff(cutoff: double); doErosion(drops: int, iterations: int); toBufferedImage(): BufferedImage; toWavefrontOBJ(): String]

[Task]<-[MapTask||call(): Map]1--[Map]
[Ui]1--[MapTask]
[OptionCollection|final seed: String; final mountainScale: double; final mountainCutoff: double; final largeFeatureScale: double; final seaCutoff: double; final erosionIterations: int|withSeed(seed: String): OptionCollection; withMountainScale(mountainScale: double): OptionCollection; withMountainCutoff(mountainCutoff: double): OptionCollection; withLargeFeatureScale(largeFeatureScale: double): OptionCollection; withSeaCutoff(seaCutoff: double): OptionCollection; withErosionIterations(erosionIterations: int): OptionCollection]

[MapAppFile|update(state: OptionCollection); state(): OptionCollection; canUndo(): boolean; canRedo(): boolean; undo(); redo()]

[MapAppFile]1-*[OptionCollection]

[MapAppFile]1-1[Ui]

[MapTask]1-[OptionCollection]

-->
