Aiheen kuvaus ja rakenne
========================

+ *Aihe:* Angry Birds -klooni

Suppea versio
-------------

Pelin päähahmo on krenatööri joka heittää kranaatteja kohti pelialueella
sijaitsevia vihollisia ja rakennelmia. Pelissä saa pisteitä pääasiassa
vihollisten tuhoamisesta. Kranaatteja pelissä on rajoitettu määrä ja peli
päättyy voittoon vain mikäli kaikki viholliset saatiin tuhottua käytössä
olleella ammusmäärällä.

Pelissä pisteytys lasketaan tuhotun objektin mukaan ja pisteistä jaetaan
kenttäkohtaisesti rank/sotilasarvo riippuen oletetusta hyvästä pistemäärästä
kyseenomaisella kentällä.

Peli toteutetaan libGDX:llä, Box2D:llä ja Physics Body Editor kirjastoilla
Android- ja desktop-versioina.

+ *Käytttäjät:* Ainoa käyttäjä on pelaaja.


Pelaajan toiminnot
------------------

+ Pelikentän valitseminen
+ Pistetilastojen tarkasteleminen
+ Toiminnot pelin aikana
++ kranaatin heittäminen
++ Pelin pysäyttäminen, jatkaminen ja keskeyttäminen
