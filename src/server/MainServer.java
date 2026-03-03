package server;

public class MainServer {
    public static void main(String[] args) {
        System.out.println("=== AVVIO SERVER ===");

        Server server = new Server(3000);

        if (!server.avvia()) {
            System.err.println("Impossibile avviare il server. Terminazione.");
            return;
        }

        server.attendi();

        // Rimane in ascolto finche il client non invia "exit"
        while (true) {
            String richiesta = server.leggi();

            if (richiesta == null) {
                System.err.println("[Server] client disconnesso inaspettatamente.");
                break;
            }

            // Controlla se il client vuole chiudere la comunicazione
            if (richiesta.equalsIgnoreCase("exit")) {
                System.out.println("[Server] il client ha richiesto la chiusura.");
                server.scrivi("Chiusura comunicazione. Arrivederci.");
                break;
            }

            server.scrivi("Ho ricevuto: \"" + richiesta + "\"");
        }

        server.chiudi();
        server.termina();

        System.out.println("=== FINE SERVER ===");
    }
}