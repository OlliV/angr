Ohjelman rakenne
================

Peli koostuu pääasiassa kolmesta moduulista joista ensimmäisessä
on pelin logiikkaan liittyvät luokat ja muu alustariippumaton koodi,
kahteen muuhun moduuliin on jaettu alustariippuvainen koodi.

Moduulit
--------

### Angr

Angr löytyy hakemistosta workspace/Angr. Tässä moduulissa/eclipse-projektissa
on pelin alustariippumaton koodi.

Tärkeimpiä pakkauksia ja luokkia Angr:n alla pelin laajentamisen kannalta on
fi.hbp.angr -pakkauksessa sijaitseva BodyFactory.java jolla luodaan kaikki
peliobjektit pelin sisällä. BodyFactory omistaa tekstuurit, äänet ja muut
assetit joita käytetään aina uutta peliobjektia luodessa.

Pakkaus fi.hbp.angr.models sisältää pelissä käytetyt mallit liittyen
fysiikkamallinnukseen. Tässä pakkauksessa on mm. Explosion-luokka jolla
voidaan käynnistää räjähdyksen mallintaminen pelin fysiikkamoottorissa.

Pakkaus fi.hbp.angr.models.actors on tärkein pakkaus laajennettaessa
peliobjektien valikoimaa. Tässä luokassa on kaikki pelin aikana nähtävät
objektit kuten laatikot (Box.java), pelaajan hahmo (Hans.java) ja kranaatti
(grenade.java). Tässä dokumentissa peliobjetilla tarkoitetaan suurinpiirtein
samaa kuin koodissa esiintyvällä sanalla actor. Sen lisäksi, että Actor on
luokan nimi LibGDX:ssä.

Pakkaus fi.hbp.angr.models.levels käsittää luonnollisesti pelin kentät.
Pelissä jokainen pelikenttä on oma luokkatoteutuksensa abstraktista luokasta
Level.java. Kenttien maasto ladataan Level.java:n sisältämillä metodeilla.
Kenttien maasto on jaettu OpenGL ES:n rajoitusten vuoksi 1000 px leveisiin
osiin joiden määrä kerrotaan Level:n konstruktorille. Parhaiten asian ymmärtää
katsomalla TestLevel.java:n toteutusta. Käytännössä uusi Level:n perivä luokka
on vastuussa luoda kaikki pelikentällä nähtävät peliobjektit pl. maasto.

Pakkaus fi.hbp.angr.stage sisältää tärkeimpänä GameStage-luokan joka on
varsinainen omistaja kaikille peliobjekteille pelikentällä. GameStage
vastaa myös peliobjektien tuhoamisesta. Peliobjektit näkyvät GameStage-luokalle
pääasiassa Actor-luokan rajapinnan kautta. Actor periytyy LibGDX:n
suunnitteluperiaatteista missä Actorit omistaa Stage. Tämän lisäksi
LibGDX:n semantiikassa esiintyy Screen ja kameroita (Camera-alkuiset luokat).
GameStage on pääasiallinen kameroiden omistaja.

Pakkaus fi.hbp.angr.screens sisältää eri tilanteissa näytettäviä ruutuja.
Näitä ruutuja ovat valikot ja itse peli. Ruudut piirtävät sekä itse
sprite-grafiikkaa, että Stage-olioiden kohdalla ko. stagen kautta sen
omistamien actorien grafiikan (pääasiassa spritet).


### Angr-desktop

Angr-desktop on periaatteessa alustariippumaton versio pelistä (joskin
rajoittuu x86:n), mutta LibGDX:ssä sen kohdealusta on ns. normaalit
tietokoneet.

Tässä pelissä desktop-versiossa ei ole juuri muuta eroa android-versioon kuin
natiivikirjastot. Joskin tällä hetkellä desktop-versiossa ei ole tukea
pisteiden tallentamiseen tai lähettämiseen mihinkään palveluun.


### Angr-android

Angr-android on Android-käyttöjärjestelmälle suunnattu versio pelistä.
Erona desktop-versioon on Arm:lle käännetyt natiivikirjastot ja
se, että pisteiden laskentaan käytetään SwarmConnect -palvelua.
