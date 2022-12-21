import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    private UserDB userDB;
    private ServerSocket serverSocket;
    private boolean running;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(12345);
            this.running = true;
            this.userDB = new UserDB();
        } catch (Exception e) {
            System.err.println("Could not listen on port: 12345.");
            System.exit(1);
        }
    }

    public void acceptConnections() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                Connection con = new Connection(clientSocket);
                System.out.println("Connection from client accepted");

                new Thread(new ClientHandler(con,userDB)).start();
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
