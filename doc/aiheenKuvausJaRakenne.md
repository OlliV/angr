Aiheen kuvaus ja rakenne
========================

+ *Aihe:* Angry Birds -klooni

Kuvaus
------

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


Kehitettävää
------------

Pelistä puuttuu vielä varsinaiset viholliset ja kaikki "järkevä" tuhottava.
Jotta peli olisi mielekäs pelissä olisi hyvä olla vaikka tykkejä ja jotain
muuta 1700-luvun lopun sotatantereelle kuuluvaa välineistöä.

Peli tarvitsisi useamman kentän mikäli pelaajan mielenkiintoa halutaan
pitää yllä. Lisäksi peli tarvitsisi samasta syystä jotain muitakin
ei niin historiallisesti tarkkoja räjähteitä. Näitä voisi jopa myydä
Swarmin kautta tai ansaita pisteillä tms.

Koodin puolesta kehitettävää on vielä mm. desktop-version pistetilastot.
Lisäksi peliobjetien assetteja ei voida tällä hetkellä järkevästi poistaa
muistista, koska ei tiedetä mitä seuraava kenttä tarvitsee. Tästä syystä
tekstuurit ym. ovat tällä hetkellä jatkuvasti muistissa.

