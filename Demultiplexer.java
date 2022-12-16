import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Demultiplexer {

    private final Connection con;
    private final ReentrantLock lock = new ReentrantLock();
    private Map <Integer, Alarm> alarms = new HashMap<>();

    public Demultiplexer(Connection con) {
        this.con = con;
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    Frame frame = con.receive();
                    Alarm a = this.alarms.get(frame.getFrameType());
                    a.push(frame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int send(Frame f){
        this.lock.lock();
        if (this.alarms.containsKey(f.getFrameType())==false) {
            this.alarms.put(f.getFrameType(), new Alarm());
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
        Alarm al = this.alarms.get(tag);
        Frame f = al.poll();
        return f;
    }

    public void close() {
        this.con.close();
    }
}
