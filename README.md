EVENT-DRIVEN E-COMMERCE API (KAFKA & DOCKER)
Nowoczesny, w pelni skonteneryzowany backend dla platformy e-commerce. Aplikacja udostepnia bezpieczne REST API do zarzadzania produktami i uzytkownikami. 

Zostala zaprojektowana w oparciu o architekture sterowana zdarzeniami (Event-Driven Architecture) z wykorzystaniem Apache Kafka, co gwarantuje wysoka wydajnosc i asynchroniczne przetwarzanie zadan w tle.

Projekt wykorzystuje srodowisko Docker do latwego uruchamiania calej infrastruktury (Aplikacja, Baza Danych, Broker Wiadomosci) za pomoca jednej komendy.

TECHNOLOGIE I ARCHITEKTURA
- Jezyk i Framework: Java 17, Spring Boot 3
- Baza danych: PostgreSQL, Spring Data JPA, Hibernate
- Message Broker (Asynchronicznosc): Apache Kafka
- Bezpieczenstwo: Spring Security, bezstanowa autoryzacja za pomoca tokenow JWT (JSON Web Token)
- Infrastruktura: Docker, Docker Compose
- Architektura & Wzorce:
  * Wzorzec DTO (Data Transfer Object) z dedykowanymi Mapperami w celu izolacji logiki bazodanowej od widoku klienta.
  * Scentralizowana obsluga wyjatkow (@ControllerAdvice / GlobalExceptionHandler).
  * Architektura Event-Driven (Producent / Konsumer) dla zadan dzialajacych w tle.


KLUCZOWE FUNKCJONALNOSCI
1. System Powiadomien (Apache Kafka)
Zaimplementowano asynchroniczny system powiadomien. Podczas rejestracji uzytkownika, glowny watek zapisuje dane w bazie PostgreSQL i natychmiast zwraca token do klienta (zapewniajac szybki czas odpowiedzi API). Rownolegle, poprzez KafkaProducerService, wysylane jest zdarzenie na dedykowany topic. Niezalezny mikro-modul (NotificationConsumer) odbiera te wiadomosc i w tle przetwarza wysylke e-maila powitalnego.

2. Modul Bezpieczenstwa (JWT)
- Rejestracja i logowanie uzytkownikow z haslami szyfrowanymi algorytmem BCrypt.
- Generowanie i weryfikacja krotkoterminowych tokenow JWT, chroniacych dostep do wrazliwych endpointow API.

3. Modul Zarzadzania Produktami
- Pelny cykl zycia (CRUD) dla encji Product.
- Scisla walidacja danych wejsciowych i hermetyzacja danych zwracanych do klienta.


JAK URUCHOMIC PROJEKT LOKALNIE?
Dzieki wykorzystaniu technologii Docker, nie musisz instalowac lokalnie bazy PostgreSQL ani serwera Kafka. Cala infrastruktura podniesie sie automatycznie w dedykowanej sieci wirtualnej.

1. Wejdz do folderu z projektem:
   cd event-driven-ecommerce-api
2. Zbuduj i uruchom kontenery uzywajac Docker Compose:
   docker-compose up -d --build

Aplikacja (REST API) bedzie dostepna pod adresem: http://localhost:8080

(Aby zatrzymac i wyczyscic srodowisko, wpisz: docker-compose down -v)


KONTAKT
--------------------------------------------------------------------
Sebastian Waszczun - Junior Software Engineer (Java/Backend)
- LinkedIn: www.linkedin.com/in/sebastian-waszczun-85b953310
- E-mail: sebastian.waszczun@gmail.com
