# ThisHotel Lite - Hotel Management System
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.3-green?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-blue?logo=docker)
![JWT](https://img.shields.io/badge/JWT-Auth-black?logo=jsonwebtokens)
> [Versione italiana](#italiano)

ThisHotel is an application designed to manage the daily operations of a hotel or hospitality facility.
It currently supports user management with different roles, room creation and editing, staff shift assignment, and password reset.
The project was born from the desire to challenge myself, gain proficiency in a scalable RESTful architecture, integrating security systems while paying close attention to code quality.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Lombok, MapStruct
- Docker
- Postman

## How to Run
### Prerequisites
* Java 17.
* Maven 3.6+.
* PostgreSQL
* Git

### Setup
1. Clone: `git clone https://github.com/felixstan97/thisHotel.git`

2. Set the build profile
   #### With remote DB:
    * Make sure the profile inside `pom.xml` at path `build/plugins/plugin/configuration/profiles` is set to `aws`

   #### With local DB:
    * Edit the profile inside `pom.xml` under `build/plugins/plugin/configuration/profiles`
    * Set it to `local` (default is `aws`)

3. (ENV) Once the profile is chosen, there are two strategies to set the environment variables:

   #### Terminal-level export (in the terminal where you intend to run the server):
    * Open a terminal and paste the following:
        - export DB_URL="jdbc:postgresql://<your_domain>"
        - export DB_USERNAME="<your_username>"
        - export DB_PASSWORD="<your_password>"

   #### Hard-coded values:
    * Directly inside the selected application.properties file (either aws or local), replace the `${value}` placeholders with your own credentials.

### Build & Run
* mvn clean install
* mvn spring-boot:run

## How to Test with Postman
A Postman Collection with all requests pre-configured is included in the repository.

1. Open Postman
2. Click **Import** and select the [Postman Collection](./postman/Hotel_API.postman_collection.json) from the `/postman` folder
3. Make sure the `baseUrl` environment variable is set to http://localhost:8080 (if testing locally),
   otherwise set `baseUrl` to the URL/IP where the server is running
4. Run the authentication calls first (login/register) to obtain the JWT token
5. The token is automatically used as a Bearer Token in subsequent requests

### Notes
**The project is configured so that upon first startup — with an empty database — the initial required operation is registering an admin user,
followed by logging in as that admin to obtain the JWT token.
Once authenticated as admin, all other operations can be performed, such as creating rooms or registering staff members.**


---
# Italiano
> [English version](#thishotel-lite---hotel-management-system)
# ThisHotel lite - Hotel Management System
ThisHotel è un'applicazione progettata per la gestione delle operazioni quotidiane di un hotel / struttura alberghiera.
Attualmente permette la gestione di utenti con ruoli diversi, la creazione e la modifica delle stanze, 
l'assegnazione dei turni al personale ed il reset della password.
Il progetto nasce dalla voglia di mettersi in gioco, acquisire padronanza di un'architettura RESTful scalabile, 
integrando sistemi di sicurezza, prestando attenzione alla qualità del codice.


## Tecnologie utilizzate
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Lombok, MapStruct
- Docker
- Postman

## Come avviarlo 
### Prerequisiti
* Java 17.
* Maven 3.6+.
* PostgreSQL
* Git

### Setup
1. Clone: `git clone https://github.com/felixstan97/thisHotel.git`

2. Imposta il profilo
    #### Con DB online:
    * Controlla che il profilo dentro al `pom.xml` al path `build/plugins/plugin/configuration/profiles` sia `aws`

   #### Con DB in locale:
    * Modifica il profilo dentro al `pom.xml` dentro `build/plugins/plugin/configuration/profiles`
      * Imposta `local` (default `aws`)
3. (ENV) Una volta scelto il profilo, si hanno due modalita per impostare le variabili d'ambiente:
   #### Valori a livello di terminale dove poi si vuole far girare il server:
   * Per farlo basta aprire un terminale incollare:
      - export DB_URL="jdbc:postgresql://<tuo_dominio>"
      - export DB_USERNAME="<tuo_username>"
      - export DB_PASSWORD="<tua_password>"
   #### Valori hard-coded:
   * Direttamente dentro application.properties scelto (che sia aws o local), 
   modificare i campi `${valore}` con i propri dati.

### Build & Run:
* mvn clean install
* mvn spring-boot:run

## Come testarlo con Postman
Nel repository è inclusa una Postman Collection con tutte le chiamate già configurate.

1. Apri Postman
2. Clicca su **Import** e seleziona la [Postman Collection](./postman/Hotel_API.postman_collection.json) dalla cartella `/postman`
3. Controlla che la variabile d'ambiente `baseUrl` sia su http://localhost:8080 (nel caso testi in locale)
   altrimenti imposta `baseUrl` con l URL/IP di dove gira il server
4. Esegui prima le chiamate di autenticazione (login/register) per ottenere il JWT token
5. Il token viene usato automaticamente come Bearer Token nelle chiamate successive

### NOTE
**Il progetto e configurato in modo che appena avviato il server, quindi con DB vuoto,
la prima operazione da fare sia la registrazione del admin, dopo di che la login dell'admin per ottenere il token,
una volta loggato come admin, effettuare le altre operazioni come la creazione delle stanze o registrazione staff.**
