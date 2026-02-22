package server;

public class MainServer {
    public static void main(String[] args) {
        System.out.println("AVVIO SERVER");

        Server server = new Server(3000);

        if (!server.avvia()) {
            System.err.println("Impossibile avviare il server. Terminazione.");
            return;
        }

        // Accetta un singolo client, gestisce la comunicazione e chiude
        server.attendi();

        String richiesta = server.leggi();
        if (richiesta != null) {
            server.scrivi("Ho ricevuto: \"" + richiesta + "\"");
        }

        server.chiudi();
        server.termina();

        System.out.println("FINE SERVER");
    }
}