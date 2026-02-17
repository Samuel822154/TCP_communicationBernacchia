package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainClient {
    public static void main(String[] args) {
        System.out.println("CLIENT: avvio del client!");

        try (Socket socket = new Socket("localhost", 3000)) {

            System.out.println("CLIENT: connessione al server stabilita");

            // Apertura stream di scrittura (invio richiesta al server)
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Apertura stream di lettura (ricezione risposta dal server)
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Invio richiesta al server
            String richiesta = "Sono il client.";
            out.println(richiesta);
            System.out.println("CLIENT: richiesta inviata: " + richiesta);

            // Lettura risposta dal server
            String risposta = in.readLine();
            System.out.println("CLIENT: risposta ricevuta:  " + risposta);

            // Chiusura comunicazione
            in.close();
            out.close();
            System.out.println("CLIENT: comunicazione chiusa");

        } catch (IOException e) {
            System.err.println("CLIENT: errore di connessione -> " + e.getMessage());
        }
    }
}