package server;

/**
 * RAMO MULTICLIENT: connessioni non contemporanee
 * Il server accetta piu' client uno alla volta:
 * finisce di servire il primo, poi accetta il secondo, e cosi' via.
 * Usa un ciclo while che termina quando il client invia "exit".
 */
public class MainServerMultiClient {
    public static void main(String[] args) {
        System.out.println("AVVIO SERVER MULTICLIENT");

        Server server = new Server(3000);

        if (!server.avvia()) {
            System.err.println("Impossibile avviare il server. Terminazione.");
            return;
        }

        // Il server rimane attivo e serve un client alla volta
        while (true) {
            System.out.println("[Server] pronto per un nuovo client ");

            // Attende la connessione del prossimo client
            if (server.attendi() == null) {
                System.err.println("[Server] errore nell'accettare il client. Riprovo ");
                continue;
            }

            // Legge il messaggio del client
            String richiesta = server.leggi();

            if (richiesta == null) {
                // Il client si e disconnesso senza inviare nulla
                server.chiudi();
                continue;
            }

            // Controlla se il client vuole spegnere il server
            if (richiesta.equalsIgnoreCase("exit")) {
                System.out.println("[Server] ricevuto comando di spegnimento.");
                server.scrivi("Server in spegnimento. Arrivederci.");
                server.chiudi();
                break;
            }

            // Risponde al client
            server.scrivi("Ho ricevuto: \"" + richiesta + "\"");

            // Chiude la connessione con questo client prima di accettarne un altro
            server.chiudi();
        }

        server.termina();
        System.out.println("FINE SERVER MULTICLIENT");
    }
}