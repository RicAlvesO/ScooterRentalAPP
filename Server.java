import java.net.ServerSocket;

public class Server {
    // Echo Java server receiving connections from clients on port 12345 in localhost
    // For each connection, the server creates a new thread to handle the client
    // The server is stopped by typing "shutdown" in the console
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        boolean listening = true;
        try {
            serverSocket = new ServerSocket(12345);
        } catch (Exception e) {
            System.err.println("Could not listen on port: 12345.");
            System.exit(-1);
        }
        System.out.println("Server is running");
        while (listening)
            try {
                new Thread(new ClientHandler(serverSocket.accept())).start();
                System.out.println("New connection");
            } catch (Exception e) {
                System.err.println("Accept failed.");
                System.exit(-1);
            }
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
