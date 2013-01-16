Aiheen kuvaus ja rakenne
========================

+ *Aihe:* Angry Birds -klooni

Suppea versio
-------------

Pelissä on päähahmo jolla on käytössään pelaajan ohjaama ritsa tai jousipyssy
jolla ammutaan nuolia kohti pelialueella sijaitsevia vihollisia ja rakennelmia.
Pelissä saa pisteitä pääasiassa vihollisten tuhoamisesta. Ammuksia pelissä on
rajoitettu määrä ja peli päättyy voittoon vain mikäli kaikki viholliset saatiin
tuhottua käytössä olleella ammusmäärällä.

Jatkoa
------

Pelissä pelaaja liikuttaa eleillä (gesture) pelaajaa joka kantaa mukanaan
jousipyssyä tai jotain vastaavaa asetta. Aseella ammutaan saman tyylisesti
kuin Angry Birds:n ritsalla. Keskeistä pelin kartoissa on mahdollisuus
suojautua. Pelin ympäristö voi olla jossain määrin tuhoutuvaa objektien osalta.
Jokainen pelikenttä sisältää mahdollisesti ruutu kerrallaan scrollaavan, mutta
kuitenkin suhteellisen pienen kartan jossa on tietty määrä vihollisia jotka
aktivoituvat kun pelaaja tulee uudelle kartta-alueelle tai kartta ruudun
alueelle.

Peli toteutetaan libGDX:llä, Box2D:llä ja Physics Body Editor kirjastoilla
Android- ja desktop-versioina.

+ *Käytttäjät:* Ainoa käyttäjä on pelaaja.


Pelaajan toiminnot
------------------

+ Pelikentän valitseminen
+ Pistetilastojen tarkasteleminen
+ Toiminnot pelin aikana
++ ampuminen
++ (hahmon liikuttaminen eleillä)
++ Pelin pysäyttäminen, jatkaminen ja keskeyttäminen
