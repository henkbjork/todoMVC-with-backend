# todoMVC-with-backend

Projekt: TodoMVC

I projektet har jag använt mig av HTML, CSS, Spring Boot, Thymeleaf, JPA och MySQL.

Funktioner
En att-göra-lista där man kan:
Lägga till / ta bort föremål
Markera föremål som färdiga / ofärdiga
Se hur hur många ofärdiga föremål som återstår
Ta bart alla färdiga föremål
Visa alla färdiga, ofärdiga eller alla föremål.
Föremål kan redigeras.
Ovan funktioner fumgerar tack vare kommunikation med en databas.

Beskrivning av funktion
Applikationen är en todo-list där användaren kan skriva in saker som ska göras i form av en textsträng, dessa "todos" lagras i en databas och visas för användaren. Användaren kan sedan välja att markera dessa "todos" som färdiga/ofärdiga, redigera dem samt ta bort dem. Man kan även välja att sortera sina "todos" som färdiga/ofärdiga eller att visa samtliga "todos" som ska göras. De olika valen användaren gör påverkar databasen där "todos" finns lagerade samt vad som visas på skärmen.

Beskrivning av applikation
I denna version av TodoMVC har jag använt mig av Spring Boot med följande dependencies: Web, Thymeleaf, JPA och MySQL.

Applikationen är uppbyggd enligt MVC design mönstret där jag har en Controller som tar hand om Http requesten och tillsammans med en Model som bär datan till vår View. Jag har valt att jobba med Thymeleaf för rendering av vyerna.

För att underlätta kommunikatioen med databasen har jag använt mig av Spring JPA och Spring Boot använder som default Hibernate. Eftersom jag använder MySQL så har jag också en mysql-connector som dependencie i min pom.xml fil. Konfigureringen av MySQL är gjord i application.propoerties filen.
