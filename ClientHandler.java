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
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.running = true;
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
                    System.out.println("Connection closed");
                    return;
                }else if(msg.getFrameType() >= 0 && msg.getFrameType() <= 7){
                    this.handleQuery(msg);
                }
            }catch (Exception e) {
                running = false;
                System.out.println("Connection closed");
                return;
            }
        }
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed");

    }

    public void handleQuery(Frame f){
        System.out.println("Received: " + f.getFrameType());
    }

    public boolean send(Frame msg){
        try {
            out.writeObject(msg);
            return true;
        } catch (IOException e) {
            running = false;
            return false;
        }
    }
}

