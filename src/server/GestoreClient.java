package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * RAMO MULTITHREAD
 * Ogni istanza di questa classe viene eseguita in un thread separato
 * e gestisce la comunicazione con un singolo client in modo indipendente.
 * Questo permette al server di servire piu' client contemporaneamente.
 */
public class GestoreClient implements Runnable {

    private Socket clientSocket;
    private int id;

    public GestoreClient(Socket clientSocket, int id) {
        this.clientSocket = clientSocket;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("[Thread-" + id + "] avviato per client: "
                + clientSocket.getInetAddress().getHostAddress()
                + ":" + clientSocket.getPort());

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                Socket cs = clientSocket
        ) {
            String richiesta = in.readLine();

            if (richiesta == null) {
                System.err.println("[Thread-" + id + "] il client si e' disconnesso senza inviare dati.");
                return;
            }

            System.out.println("[Thread-" + id + "] messaggio ricevuto: \"" + richiesta + "\"");

            // Simula un'elaborazione che richiede tempo
            Thread.sleep(1000);

            String risposta = "[Thread-" + id + "] Ho ricevuto: \"" + richiesta + "\"";
            out.println(risposta);
            System.out.println("[Thread-" + id + "] risposta inviata.");

        } catch (IOException e) {
            System.err.println("[Thread-" + id + "] ERRORE: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[Thread-" + id + "] thread interrotto.");
        }

        System.out.println("[Thread-" + id + "] comunicazione chiusa.");
    }
}
