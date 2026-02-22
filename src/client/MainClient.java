package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainClient {

    private static final String HOST = "localhost"; // SCENARIO 4: provare "locaost"
    private static final int PORT = 3000;
    private static final int MAX_TENTATIVI = 5;          // tentativi massimi di connessione
    private static final int ATTESA_MS = 2000;       // pausa tra un tentativo e l'altro

    public static void main(String[] args) {
        System.out.println("CLIENT: avvio del client!");

        Socket socket = null;

        // SCENARIO 4: hostname non valido o irraggiungibile
        // UnknownHostException viene lanciata subito alla creazione del Socket
        // se il nome host non può essere risolto dal DNS.

        // SCENARIO 2: il client parte prima del server
        // Il server potrebbe non essere ancora pronto: eseguiamo N tentativi
        // con una pausa tra l'uno e l'altro prima di rinunciare.

        for (int tentativo = 1; tentativo <= MAX_TENTATIVI; tentativo++) {
            try {
                System.out.println("CLIENT: tentativo di connessione "
                        + tentativo + "/" + MAX_TENTATIVI
                        + " a " + HOST + ":" + PORT + "...");

                socket = new Socket(HOST, PORT);
                System.out.println("CLIENT: connessione al server stabilita.");
                break; // connessione riuscita, uscita dal ciclo

            } catch (UnknownHostException e) {
                // SCENARIO 4 il n0me host non esiste
                System.err.println("CLIENT: ERRORE - host \"" + HOST + "\" non trovato.");
                System.err.println("CLIENT: verificare il nome o indirizzo del server.");
                System.err.println("CLIENT: terminazione.");
                return;

            } catch (ConnectException e) {
                // SCENARIO 2 il server non è ancora pronto
                System.err.println("CLIENT: connessione rifiutata (server non disponibile).");

                if (tentativo < MAX_TENTATIVI) {
                    System.out.println("CLIENT: nuovo tentativo tra "
                            + (ATTESA_MS / 1000) + " secondi");
                    try {
                        Thread.sleep(ATTESA_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        System.err.println("CLIENT: attesa interrotta.");
                        return;
                    }
                } else {
                    System.err.println("CLIENT: numero massimo di tentativi raggiunto.");
                    System.err.println("CLIENT: impossibile connettersi al server. Terminazione.");
                    return;
                }

            } catch (IOException e) {
                System.err.println("CLIENT: ERRORE di connessione: " + e.getMessage());
                return;
            }
        }

        // Verifica di sicurezza
        if (socket == null) {
            System.err.println("CLIENT: socket non inizializzato. Terminazione.");
            return;
        }

        // Comunicazione con il server
        try (
                Socket s = socket;
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(s.getInputStream()))
        ) {
            // Invio richiesta
            String richiesta = "Sono il client.";
            out.println(richiesta);
            System.out.println("CLIENT: richiesta inviata:  \"" + richiesta + "\"");

            // Lettura risposta
            String risposta = in.readLine();

            if (risposta == null) {
                System.err.println("CLIENT: il server ha chiuso la connessione senza rispondere.");
            } else {
                System.out.println("CLIENT: risposta ricevuta:  \"" + risposta + "\"");
            }

        } catch (IOException e) {
            System.err.println("CLIENT: ERRORE durante la comunicazione: " + e.getMessage());
        }

        System.out.println("CLIENT: comunicazione chiusa. Fine esecuzione.");
    }
}
