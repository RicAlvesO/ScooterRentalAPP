import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Demultiplexer {

    private final Connection con;
    private final ReentrantLock lock = new ReentrantLock();
    private Map <Integer, Alarm> despertadores = new HashMap<>();

    public Demultiplexer(Connection con) {
        this.con = con;
    }

    public int send(Frame f){
        this.lock.lock();
        if (this.despertadores.containsKey(f.getFrameType())==false) {
            this.despertadores.put(f.getFrameType(), new Alarm());
        }
        this.lock.unlock();
        try {
            con.send(f);
            return 0;
        } catch (IOException e) {
            return 1;
        }
    }

    public Frame receive(int tag) throws InterruptedException {
        Alarm d = this.despertadores.get(tag);
        Frame f = d.poll();
        return f;
    }

    public void close() {
        this.con.close();
    }
}
