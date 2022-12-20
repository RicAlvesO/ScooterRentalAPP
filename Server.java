import java.net.ServerSocket;

public class Server {
    
    private ServerSocket serverSocket;
    private boolean running;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(12345);
            this.running = true;
        } catch (Exception e) {
            System.err.println("Could not listen on port: 12345.");
            System.exit(1);
        }
    }

    public void acceptConnections() {
        while (running) {
            try {
                new Thread(new ClientHandler(serverSocket.accept())).start();
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
        }
        try {
            this.serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
