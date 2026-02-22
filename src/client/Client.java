package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private String nome;
    private String colore;
    private Socket socket;

    private static final int MAX_TENTATIVI = 5;
    private static final int ATTESA_MS = 2000;

    // Costruttore con solo nome
    public Client(String nome) {
        this.nome = nome;
        this.colore = "bianco"; // colore di default
        this.socket = null;
    }

    // Costruttore con nome e colore
    public Client(String nome, String colore) {
        this.nome = nome;
        this.colore = colore;
        this.socket = null;
    }

    /**
     * Tenta la connessione al server.
     * Gestisce hostname errato, server non ancora avviato e altri errori.
     * return 0 se connessione riuscita, -1 in caso di errore
     */
    public int connetti(String nomeServer, int portaServer) {
        for (int tentativo = 1; tentativo <= MAX_TENTATIVI; tentativo++) {
            try {
                System.out.println("[" + nome + "] tentativo di connessione "
                        + tentativo + "/" + MAX_TENTATIVI
                        + " a " + nomeServer + ":" + portaServer + "...");

                socket = new Socket(nomeServer, portaServer);
                System.out.println("[" + nome + "] connessione stabilita.");
                return 0;

            } catch (UnknownHostException e) {
                System.err.println("[" + nome + "] ERRORE  host \"" + nomeServer + "\" non trovato.");
                System.err.println("[" + nome + "] verificare il nome del server. Terminazione.");
                return -1;

            } catch (ConnectException e) {
                System.err.println("[" + nome + "] server non disponibile (tentativo " + tentativo + ").");
                if (tentativo < MAX_TENTATIVI) {
                    System.out.println("[" + nome + "] nuovo tentativo tra " + (ATTESA_MS / 1000) + " secondi...");
                    try {
                        Thread.sleep(ATTESA_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return -1;
                    }
                } else {
                    System.err.println("[" + nome + "] numero massimo di tentativi raggiunto. Terminazione.");
                    return -1;
                }

            } catch (IOException e) {
                System.err.println("[" + nome + "] ERRORE di connessione: " + e.getMessage());
                return -1;
            }
        }
        return -1;
    }

    /**
     * Invia un messaggio al server.
     */
    public void scrivi(String messaggio) {
        if (socket == null || socket.isClosed()) {
            System.err.println("[" + nome + "] ERRORE  non connesso al server.");
            return;
        }
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(messaggio);
            System.out.println("[" + nome + "] messaggio inviato: \"" + messaggio + "\"");
        } catch (IOException e) {
            System.err.println("[" + nome + "] ERRORE durante l'invio: " + e.getMessage());
        }
    }

    /**
     * Legge la risposta dal server.
     */
    public void leggi() {
        if (socket == null || socket.isClosed()) {
            System.err.println("[" + nome + "] ERRORE  non connesso al server.");
            return;
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String risposta = in.readLine();
            if (risposta == null) {
                System.err.println("[" + nome + "] il server ha chiuso la connessione senza rispondere.");
            } else {
                System.out.println("[" + nome + "] risposta ricevuta: \"" + risposta + "\"");
            }
        } catch (IOException e) {
            System.err.println("[" + nome + "] ERRORE durante la lettura: " + e.getMessage());
        }
    }

    /**
     * Chiude la connessione con il server.
     */
    public void chiudi() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
                System.out.println("[" + nome + "] connessione chiusa.");
            } catch (IOException e) {
                System.err.println("[" + nome + "] ERRORE durante la chiusura: " + e.getMessage());
            }
        }
    }

    public String getNome() {
        return nome;
    }

    public String getColore() {
        return colore;
    }
}
