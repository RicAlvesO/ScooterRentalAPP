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

    public void send(Frame f) throws IOException {
        this.lock.lock();
        if (this.despertadores.containsKey(f.getTag())==false) {
            this.despertadores.put(f.getTag(), new Alarm());
        }
        this.lock.unlock();
        con.send(f);
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
