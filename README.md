Applicazione Client-Server TCP
Applicazione Java che implementa una comunicazione unicast tra un client e un server tramite socket TCP.
Il client invia un messaggio al server, il server lo riceve e risponde confermando la ricezione.
Il progetto e organizzato in 3 branch Git, ognuno con funzionalita diverse.

Comunicazione base tra un singolo client e il server.
Le classi Client e Server incapsulano tutta la logica di connessione e comunicazione
seguendo il paradigma della programmazione orientata agli oggetti.
Come avviare:

Avviare MainServer
Avviare MainClient

Output atteso:
[Server] in ascolto sulla porta 3000
[Server] client connesso.
[Server] messaggio ricevuto: "Ciao, sono Mario [colore: rosso]"
[Mario] connessione stabilita.
[Mario] messaggio inviato: "Ciao, sono Mario [colore: rosso]"
[Mario] risposta ricevuta: "Ho ricevuto: Ciao, sono Mario [colore: rosso]"

Branch multiclient
Comunicazione sequenziale tra piu client e il server.
Il server accetta un client alla volta: finisce di servire il primo, poi accetta il secondo.
Il server rimane attivo finche non riceve il messaggio "exit".
Come avviare:

Avviare MainServerMultiClient
Avviare MainClientMultiClient

Output atteso:
[Server] pronto per un nuovo client
[Server] client connesso.
[Server] messaggio ricevuto: "Ciao, sono Alice [colore: rosso]"
[Server] pronto per un nuovo client
[Server] client connesso.
[Server] messaggio ricevuto: "Ciao, sono Bob [colore: blu]"
[Server] ricevuto comando di spegnimento.

Branch multithread
Comunicazione contemporanea tra piu client e il server.
Per ogni client che si connette, il server crea un thread dedicato (GestoreClient)
che gestisce la comunicazione in modo indipendente e parallelo.
In questo modo piu client vengono serviti contemporaneamente senza attese.
Come avviare:

Avviare MainServerMultiThread
Avviare MainClientMultiThread

Output atteso:
[Server] client #1 connesso. Avvio thread
[Server] client #2 connesso. Avvio thread
[Thread-1] messaggio ricevuto: "Ciao, sono Alice [colore: rosso]"
[Thread-2] messaggio ricevuto: "Ciao, sono Bob [colore: blu]"
[Thread-3] messaggio ricevuto: "Ciao, sono Carlo [colore: verde]"

Scenari gestiti
Scenario 1 - Avvio server e successivo avvio client
Il server attende bloccato su accept() finche un client non si connette.
Scenario 2 - Avvio client e successivo avvio server
Il client esegue fino a 5 tentativi di connessione con 2 secondi di pausa tra l'uno e l'altro.
Eccezione gestita: ConnectException.
Scenario 3 - Avvio server quando un altra istanza e gia in esecuzione
Il server rileva che la porta e gia occupata e termina con un messaggio di errore.
Eccezione gestita: BindException.
Scenario 4 - Avvio client con hostname errato
Il client rileva immediatamente che il nome host non esiste e termina senza riprovare.
Eccezione gestita: UnknownHostException.

Parametri configurabili
In Server.java e MainServerMultiThread.java:

PORT: porta su cui il server e in ascolto (default: 3000)

In Client.java:

HOST: indirizzo del server (default: localhost)
PORT: porta del server (default: 3000)
MAX_TENTATIVI: numero massimo di tentativi di connessione (default: 5)
ATTESA_MS: pausa in millisecondi tra un tentativo e l'altro (default: 2000)