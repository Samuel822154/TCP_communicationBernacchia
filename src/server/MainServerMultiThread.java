package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RAMO MULTITHREAD: connessioni contemporanee
 * Il server accetta piu' client contemporaneamente.
 * Per ogni client accettato viene creato un nuovo thread
 * che gestisce la comunicazione in modo indipendente e parallelo.
 */
public class MainServerMultiThread {

    private static final int PORT = 3000;

    public static void main(String[] args) {
        System.out.println("AVVIO SERVER MULTITHREAD");

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("[Server] in ascolto sulla porta " + PORT + "  ");
        } catch (BindException e) {
            System.err.println("[Server] ERRORE - porta " + PORT + " gia' in uso. Terminazione.");
            return;
        } catch (IOException e) {
            System.err.println("[Server] ERRORE durante l'avvio: " + e.getMessage());
            return;
        }

        int idCliente = 0;

        // Il server accetta client all'infinito, ognuno gestito da un thread dedicato
        while (true) {
            try {
                System.out.println("[Server] in attesa di un nuovo client  ");
                Socket clientSocket = serverSocket.accept();
                idCliente++;

                System.out.println("[Server] client #" + idCliente + " connesso. Avvio thread  ");

                // Crea e avvia un thread dedicato per questo client
                Thread t = new Thread(new GestoreClient(clientSocket, idCliente));
                t.start();

            } catch (IOException e) {
                System.err.println("[Server] ERRORE nell'accettare il client: " + e.getMessage());
                // Non termina: il server rimane attivo per i successivi client
            }
        }
    }
}
