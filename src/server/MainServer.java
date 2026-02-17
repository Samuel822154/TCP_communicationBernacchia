package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) {
        System.out.println("SERVER: inizio esecuzione");

        try (ServerSocket server = new ServerSocket(3000)) {

            System.out.println("SERVER: in attesa di richieste dal client...");
            Socket clientSocket = server.accept();
            System.out.println("SERVER: il client si è connesso");

            // Apertura stream di lettura (ricezione richiesta dal client)
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );

            // Apertura stream di scrittura (invio risposta al client)
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Lettura richiesta del client
            String richiestaClient = in.readLine();
            System.out.println("SERVER: richiesta ricevuta:  " + richiestaClient);

            // Invio risposta al client
            String risposta = "Ho ricevuto il tuo messaggio: \"" + richiestaClient + "\"";
            out.println(risposta);
            System.out.println("SERVER: risposta inviata al client");

            // Chiusura comunicazione
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("SERVER: comunicazione chiusa");

        } catch (IOException e) {
            System.err.println("SERVER: errore: " + e.getMessage());
        }

        System.out.println("SERVER: server chiuso");
    }
}