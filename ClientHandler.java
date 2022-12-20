import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    //receive the socket from server and start the thread
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean running;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            running = true;
        } catch (Exception e) {
            System.out.println("Error in ClientHandler constructor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while(running){
            try {
                Frame msg = (Frame) in.readObject();
                if (msg.getFrameType() == -1) {
                    running = false;
                    socket.close();
                    System.out.println("Connection closed");
                    return;
                }
                System.out.println(msg.getFrameType());
            }catch (Exception e) {
                running = false;
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("Connection closed");
                return;
            }
        }
    }
}
