# Applicazione Client-Server TCP

Applicazione Java che implementa una comunicazione tra un client e un server tramite socket TCP.
Il client invia messaggi inseriti da tastiera, il server li riceve e risponde confermando la ricezione.
La comunicazione si chiude quando il client scrive "exit".

---

## Descrizione delle classi

Server.java
Gestisce tutta la logica lato server: apertura del socket in ascolto,
attesa della connessione del client, lettura dei messaggi, invio delle risposte e chiusura.

MainServer.java
Punto di ingresso del server. Istanzia la classe Server e gestisce il ciclo
di comunicazione finche il client non invia il comando "exit".

Client.java
Gestisce tutta la logica lato client: connessione al server,
invio dei messaggi, lettura delle risposte e chiusura.

MainClient.java
Punto di ingresso del client. Istanzia la classe Client, legge i messaggi
inseriti dall'utente da tastiera e li invia al server fino a quando
l'utente non scrive "exit".

---

## Come avviare

1. Avviare MainServer
2. Avviare MainClient
3. Inserire i messaggi da tastiera nella console del client
4. Scrivere "exit" per chiudere la comunicazione

---

## Scenari gestiti

Scenario 1 - Avvio server e successivo avvio client (nominale)
Il server attende bloccato sul metodo accept() finche un client non si connette.

Scenario 2 - Avvio client e successivo avvio server
Il client esegue fino a 5 tentativi di connessione con 2 secondi di pausa tra l'uno e l'altro.
Eccezione gestita: ConnectException.

Scenario 3 - Avvio server quando un altra istanza e gia in esecuzione
Il server rileva che la porta e gia occupata e termina con un messaggio di errore.
Eccezione gestita: BindException.

Scenario 4 - Avvio client con hostname errato
Il client rileva che il nome host non esiste e termina immediatamente senza riprovare.
Eccezione gestita: UnknownHostException.
