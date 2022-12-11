import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Alarm {
    private final ReentrantLock lock;
    private final Condition desp;
    private final Queue<Frame> queue;

    public Alarm(){
        this.lock = new ReentrantLock();
        this.desp = this.lock.newCondition();
        this.queue = new LinkedList<>();
    }

    public Frame poll() throws InterruptedException{
        this.lock.lock();
        this.desp.await();
        Frame f = this.queue.poll();
        this.lock.unlock();
        return f;
    }
}
