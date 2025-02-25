comandi postgres:
-----------
per attivare/spegnere il server postgres (17 e la versione) ps. come admin
net start postgresql-x84-17
net stop postgresql-x84-17
-----------
=Attivo il db
- psql -U postgres
-------------------------------------------------------------
CREATE DATABASE hotelbooking;
CREATE USER hoteladmin WITH ENCRYPTED PASSWORD 'toor';
GRANT ALL PRIVILEGES ON DATABASE hotelbooking TO hoteladmin;
--------------------------------------------------------------
