Käyttöohje
==========

Pelin kääntäminen onnistuu parhaiten Eclipsellä tai mikäli
haluua myös Android-version niin mielellään Eclipse ADT
budlella jonka saa developer.android.com:sta.

Valmistelut Android-versiota varten
-----------------------------------

Android-versio käyttää SwarmConnectia pisteiden tallentamiseen ja jakamiseen.
SwarmConnect ei ole kuitenkaan sisällytetty Git:n lisenssiehtojen takia
(tai varmuuden vuoksi ja toisaalta, koska mukana on paljon binääriä).

SwarmConnectin voi ladata http://swarmconnect.com/ -sivustolta. Laataminen ja
käyttäminen ei maksa mitään joskin vaatii rekisteröitymisen. Paketti puretaan
suoraan workspace-hakemistoon.

Git repositorysta puuttuu Android-projektista SwarmAppKey.java joka kuuluu
samaan hakemistoon MainActivity.java:n kanssa. Tiedoston tulisi sisältää
seuraavaa:

package fi.hbp.angr;

/**
 * Private key
 */
public class SwarmAppKey {
    /**
     * Swarm AppKey
     */
    static final String key = "MY_PRIVATE_KEY";
}


Jossa MY_PRIVATE_KEY korvataan henkilökohtaisella SwarmConnect:n avaimella.
Avaimen olemassa ololla pyritään estämään pistetilastoihin spammaaminen, mutta
sen turvassa pitämisellä ei ole sen suurempaa merkitystä, koska ko. avaimella
ei pääse mihinkään tietoihin käsiksi Swarm:n palveluissa.


Kääntäminen
-----------

Nyt kääntäminen eri alustoille pitäisi onnistua valitsemalla haluamansa
alustan projekti.
