import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Connection {

    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final ReentrantLock wlock = new ReentrantLock();
    private final ReentrantLock rlock = new ReentrantLock();

    public Connection(Socket socket) throws IOException{
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    public void send(Frame f) throws IOException{
        wlock.lock();
        out.writeInt(f.getFrameType());
        out.writeInt(f.getLength());
        out.write(f.getData());
        out.flush();
        wlock.unlock();
    }

    public void send(int frameType, byte[] data) throws IOException{
        send(new Frame(frameType, data));
    }

    public Frame receive() throws IOException{
        rlock.lock();
        int frameType = in.readInt();
        int length = in.readInt();
        byte[] data = new byte[length];
        in.readFully(data);
        rlock.unlock();
        return new Frame(frameType, data);
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
