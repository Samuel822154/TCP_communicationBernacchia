package client;

/**
 * RAMO MULTICLIENT: connessioni non contemporanee
 * Simula piu' client che si connettono uno dopo l'altro allo stesso server.
 * Ogni client crea una propria connessione, invia un messaggio, riceve la risposta
 * e si disconnette prima che il successivo si connetta.
 */
public class MainClientMultiClient {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("AVVIO MULTICLIENT");

        String[] nomi = {"Alice", "Bob", "Carlo"};
        String[] colori = {"rosso", "blu", "verde"};

        for (int i = 0; i < nomi.length; i++) {
            System.out.println("\n Client: " + nomi[i] + " ");

            Client client = new Client(nomi[i], colori[i]);

            int esito = client.connetti("localhost", 3000);
            if (esito != 0) {
                System.err.println("[" + nomi[i] + "] connessione fallita. Salto al prossimo client.");
                continue;
            }

            client.scrivi("Ciao, sono " + nomi[i] + " [colore: " + colori[i] + "]");
            client.leggi();
            client.chiudi();

            // Piccola pausa tra un client e l'altro per rendere la simulazione leggibile
            Thread.sleep(500);
        }

        // Ultimo client invia il comando di spegnimento del server
        System.out.println("\n Client finale: invio comando exit ");
        Client clientExit = new Client("Admin");
        if (clientExit.connetti("localhost", 3000) == 0) {
            clientExit.scrivi("exit");
            clientExit.leggi();
            clientExit.chiudi();
        }

        System.out.println("\n=== FINE MULTICLIENT ===");
    }
}
