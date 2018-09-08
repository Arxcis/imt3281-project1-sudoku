# Prosjekt 1 I IMT3281 Applikasjonsutvikling

## Kom i gang

Lag en fork av dette repositoriet, inviter de andre på gruppa inn i det forkede repositoriet. Sørg for at alle kan kompilere, teste, og pushe opp igjen til repositoriet. Bruk Gjerne Wiki'en og eller f.eks. Trello (https://trello.com/) for å styre prosjektet.

## Oppgaven

I dette prosjektet skal det utvikles en en bruker [Sudoku](https://no.wikipedia.org/wiki/Sudoku) applikasjon med et grafisk grensesnitt.

Begynn med å lage en klasse for å styre logikken i Sudoku spillet, denne klassen må da ta vare på informasjon om tilstanden til spillet og inneholde logikk som gjør det mulig å plassere tall samt sjekke om tall som plasseres er gyldige. Klassen skal også inneholde metoder for å lese inn et spill i form av en JSON enkodet streng samt manipulere spillet på ulike måter.

Tilstanden til et Sudoku spill kan (men trenger ikke nødvendigvis) representeres ved hjelp av en enkelt array med 81 tall (9*9 tall).

![Skjermbilde 2018-08-21 kl. 12.42.56.png](https://bitbucket.org/repo/9pXdBkE/images/890589570-Skjermbilde%202018-08-21%20kl.%2012.42.56.png)

Det skal lages tester (og dermed også funksjonalitet i Sudoku klassen) for følgende funksjonalitet :

* Hente og sette et element i arrayen (brukes internt, defineres som protected)
* Sette et element (tall) på en gitt rad/kolonne
* Parse en JSON streng og sette opp et brett etter innholdet i denne
Bruk f.eks. følgende JSON data (en to dimensjonal array hvor den ytterste arrayen inneholder radene og hver array inne i denne inneholder data for hvert element i raden (-1 betyr tomt element))
```
#!json
[[5, 3, -1, -1, 7, -1, -1, -1, -1],
[6, -1, -1, 1, 9, 5, -1, -1, -1], 
[-1, 9, 8, -1, -1, -1, -1, 6, -1], 
[8, -1, -1, -1, 6, -1, -1, -1, 3], 
[4, -1, -1, 8, -1, 3, -1, -1, 1], 
[7, -1, -1, -1, 2, -1, -1, -1, 6], 
[-1, 6, -1, -1, -1, -1, 2, 8, -1], 
[-1, -1, -1, 4, 1, 9, -1, -1, 5], 
[-1, -1, -1, -1, 8, -1, -1, 7, 9]]
```
* Hente et [Iterator](https://docs.oracle.com/javase/10/docs/api/java/util/Iterator.html) objekt for en rad, en kolonne eller et sub-grid (de delene som er nummerert med lysegrått i figuren på toppen av siden.) Disse vil brukes internt for å sjekke om det er lov til å plassere et gitt tall på en gitt posisjon da hvert tall (1-9) kun kan brukes en gang pr. rad/kolonne/sub-grid.
* Når en setter et element til en verdi mellom null og ni så skal det sjekkes om denne verdien allerede finnes i gjeldende rad/kolonne/sub-grid. Dersom verdien finnes så skal metoden kaste en BadNumberException som inneholder nok informasjon til at en kan identifisere hvilket element som allerede har denne verdien (rad/kolonne) og om denne finnes i samme rad/kolonne/sub-grid. Lag en test for hvert tilfelle.
* **Når et brett blir speilet om en av aksene (horisontalt, vertikalt, på skrå så vil det fortsatt være det samme brettet og altså gyldig, men for en bruker så vil det fremstå som et helt nytt brett.**
* Lag test for og funksjonalitet som speiler et brett, dvs at første og siste kolonne bytter plass, nest første og nest siste kolonne bytter plass osv. Gjør det samme for å flippe brettet, dvs at første og siste rad bytter plass, nest første og nest siste rad bytter plass osv.
* Lag så tester og funksjonalitet for å speile brettet rundt henholdsvis den røde og den blå linjen i bildet under. Dvs at når brettet speiles rundt den røde linjen så skal øverste venstre hjørne bytte plass med nederste høyre hjørne (5 bytter plass med 9, så bytter 6 plass med 7, 3 med 5 osv.)

![mirror.png](https://bitbucket.org/repo/9pXdBkE/images/2942291463-mirror.png)

* Det at en bruker tall for å løse Sudoku er et valg man har gjort, en kunne like gjerne brukt bokstaver eller symboler. Dermed kan en også bytte f.eks. alle firetall med tretall og omvendt. Lag funksjonalitet for å tilfeldig bytte ut alle tall på brettet. Lag så en test for å sjekke at dette ble riktig, testen må da sjekke at overalt hvor de tidligere sto f.eks. et åttetall så står det samme tallet (kan være et vilkårlig tall, men det samme tallet på alle stedene.
* Når en har laget et brett (manuelt satt tall i aktuelle elementer eller lest det inn fra en JSON struktur og så rotert/flippet og randomisert det) så må en kunne låse de elementene som en da har lagt til slik at disse ikke kan endres via metoden du tidligere laget for å sette et element i en gitt rad/kolonne. Lag en metode som "låser" de aktuelle elementene og endre metoden som setter en verdi i en gitt rad/kolonne slik at den kaster en exception dersom en prøver å endre et låst element (andre elementer skal kunne overskrives.) Lag også en test som tester dette.
* I tillegg til at en ikke skal kunne endre disse elementene trengs en metode som kan brukes for å finne ut om et gitt element (rad/kolonne) er en låst celle. Lag denne metoden og testen for denne.
* Dersom du ikke tidligere har laget en metode for å hente verdien på en gitt rad/kolonne så lager du denne og en enkelt test for dette.

Nå er logikken for å la en bruker spille Sudoku klar, det skal nå lages et grafisk grensesnitt for å la brukere spille Sudoku. Det er ønskelig å vise dette omtrent som vist i figuren under. Det bør være en synlig gruppering av gruppene på ni og ni tall, bruk tekstfelter for å ta imot input fra brukeren og vise eksisterende tall og tall brukeren har skrevet inn.

![sudoku_solved.png](https://bitbucket.org/repo/9pXdBkE/images/3469227115-sudoku_solved.png)

Når et spill er startet så gjøres bakgrunnen på felt som er ferdig utfylt (del av oppgaven) grå, gjør det også slik at brukeren ikke kan overskrive tall i disse rutene. 

Dersom brukere skriver inn noe som ikke er et tall, eller et ugyldig tall (negative tall, null, tall større enn ni) så slettes innholdet i feltet. Dersom brukeren skriver inn et tall som allerede finnes på raden, i kolonnen eller i sub-gridet så settes bakgrunnsfargen til rød for det elementet som brukeren skrev inn dette tallet i.

Gi en hyggelig tilbakemelding til brukeren når vedkommende har klart å løse et helt Sudokubrett.

Applikasjonen skal være internasjonalisert og dokumentert via JavaDoc.