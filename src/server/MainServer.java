package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    private static final int PORT = 3000;

    public static void main(String[] args) {
        System.out.println("SERVER: inizio esecuzione");

        ServerSocket server = null;

        // Avvio del server
        try {
            server = new ServerSocket(PORT);
            System.out.println("SERVER: in ascolto sulla porta " + PORT);

        } catch (BindException e) {
            // SCENARIO 3: un'altra istanza del server è già in esecuzione sulla stessa porta
            System.err.println("SERVER: ERRORE - la porta " + PORT + " è già in uso.");
            System.err.println("SERVER: probabilmente un'altra istanza del server è già avviata.");
            System.err.println("SERVER: terminazione.");
            return; // impossibile continuare

        } catch (IOException e) {
            System.err.println("SERVER: ERRORE durante l'avvio: " + e.getMessage());
            return;
        }

        // Ciclo di accettazione connessioni
        // Il server rimane attivo per accettare una connessione alla volta.
        // In questo esempio gestisco una singola sessione, poi il server si chiude
        try {
            System.out.println("SERVER: in attesa di richieste dal client");

            // SCENARIO 1: il server è avviato, il client si connetterà in seguito.
            // server.accept() è bloccante: attende finché un client
            // non si connette.
            Socket clientSocket = server.accept();
            System.out.println("SERVER: client connesso: "
                    + clientSocket.getInetAddress().getHostAddress()
                    + ":" + clientSocket.getPort());

            gestisciClient(clientSocket);

        } catch (IOException e) {
            System.err.println("SERVER: ERRORE durante l'attesa o gestione del client: " + e.getMessage());
        } finally {
            // Chiusura del servizio
            try {
                if (server != null && !server.isClosed()) {
                    server.close();
                    System.out.println("SERVER: chiusura effettuata correttamente.");
                }
            } catch (IOException e) {
                System.err.println("SERVER: ERRORE durante la chiusura: " + e.getMessage());
            }
        }

        System.out.println("SERVER: fine esecuzione.");
    }


    // Gestisce la comunicazione con un singolo client:
    // lettura della richiesta, elaborazione, invio della risposta.

    private static void gestisciClient(Socket clientSocket) {
        System.out.println("SERVER: inizio gestione client.");

        try (
                BufferedReader in  = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter    out = new PrintWriter(clientSocket.getOutputStream(), true);
                Socket         cs  = clientSocket  // chiuso automaticamente dal try-with-resources
        ) {
            // Lettura richiesta
            String richiestaClient = in.readLine();

            if (richiestaClient == null) {
                // Il client si è disconnesso prima di inviare dati
                System.err.println("SERVER: il client si è disconnesso senza inviare dati.");
                return;
            }

            System.out.println("SERVER: richiesta ricevuta:  \"" + richiestaClient + "\"");

            // Invio risposta
            String risposta = "Ho ricevuto il tuo messaggio: \"" + richiestaClient + "\"";
            out.println(risposta);
            System.out.println("SERVER: risposta inviata:    \"" + risposta + "\"");

        } catch (IOException e) {
            System.err.println("SERVER: ERRORE durante la comunicazione con il client: " + e.getMessage());
        }

        System.out.println("SERVER: comunicazione con il client chiusa.");
    }
}