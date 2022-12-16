import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Connection {

    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final ReentrantLock wlock = new ReentrantLock();
    private final ReentrantLock rlock = new ReentrantLock();

    public Connection(Socket socket) throws IOException{
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
    }

    public void send(Frame f) throws IOException{
        wlock.lock();
        out.writeObject(f);
        wlock.unlock();
    }

    public Frame receive() throws IOException, ClassNotFoundException{
        rlock.lock();
        Frame fr=(Frame)in.readObject();
        rlock.unlock();
        return fr;
    }

    public boolean close(){
        try {
            this.socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
