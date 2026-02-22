package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int porta;

    public Server(int porta) {
        this.porta = porta;
        this.serverSocket = null;
        this.clientSocket = null;
    }

    /**
     * Avvia il server mettendosi in ascolto sulla porta specificata.
     * return true se avviato correttamente, false in caso di errore
     */
    public boolean avvia() {
        try {
            serverSocket = new ServerSocket(porta);
            System.out.println("[Server] in ascolto sulla porta " + porta + "  ");
            return true;
        } catch (BindException e) {
            System.err.println("[Server] ERRORE - la porta " + porta + " e' gia' in uso.");
            System.err.println("[Server] un'altra istanza del server e' probabilmente gia' attiva.");
            return false;
        } catch (IOException e) {
            System.err.println("[Server] ERRORE durante l'avvio: " + e.getMessage());
            return false;
        }
    }

    /**
     * Attende e accetta la connessione di un client.
     * return il Socket del client accettato, oppure null in caso di errore
     */
    public Socket attendi() {
        try {
            System.out.println("[Server] in attesa di un client  ");
            clientSocket = serverSocket.accept();
            System.out.println("[Server] client connesso: "
                    + clientSocket.getInetAddress().getHostAddress()
                    + ":" + clientSocket.getPort());
            return clientSocket;
        } catch (IOException e) {
            System.err.println("[Server] ERRORE durante l'attesa: " + e.getMessage());
            return null;
        }
    }

    /**
     * Invia un messaggio al client connesso.
     */
    public void scrivi(String messaggio) {
        if (clientSocket == null || clientSocket.isClosed()) {
            System.err.println("[Server] ERRORE - nessun client connesso.");
            return;
        }
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(messaggio);
            System.out.println("[Server] risposta inviata: \"" + messaggio + "\"");
        } catch (IOException e) {
            System.err.println("[Server] ERRORE durante l'invio: " + e.getMessage());
        }
    }

    /**
     * Legge un messaggio dal client connesso.
     *
     * @return la stringa ricevuta, oppure null in caso di errore/disconnessione
     */
    public String leggi() {
        if (clientSocket == null || clientSocket.isClosed()) {
            System.err.println("[Server] ERRORE - nessun client connesso.");
            return null;
        }
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String messaggio = in.readLine();
            if (messaggio == null) {
                System.err.println("[Server] il client si e' disconnesso.");
            } else {
                System.out.println("[Server] messaggio ricevuto: \"" + messaggio + "\"");
            }
            return messaggio;
        } catch (IOException e) {
            System.err.println("[Server] ERRORE durante la lettura: " + e.getMessage());
            return null;
        }
    }

    /**
     * Chiude la connessione con il client corrente.
     */
    public void chiudi() {
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                clientSocket.close();
                System.out.println("[Server] connessione con il client chiusa.");
            } catch (IOException e) {
                System.err.println("[Server] ERRORE durante la chiusura del client: " + e.getMessage());
            }
        }
    }

    /**
     * Chiude il server definitivamente.
     */
    public void termina() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("[Server] server terminato.");
            } catch (IOException e) {
                System.err.println("[Server] ERRORE durante la chiusura del server: " + e.getMessage());
            }
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public int getPorta() {
        return porta;
    }
}