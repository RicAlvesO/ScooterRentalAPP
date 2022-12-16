import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/* API CALL GUIDE:
 * 0 -> register(String username, String password) -> int
 * 1 -> login(String username, String password) -> Boolean
 * 2 -> get_available(Pos current) -> List<Pos>
 * 3 -> check_rewards(Pos current) -> List<Reward>
 * 4 -> reserve_scooter(Pos start) -> Reserve
 * 5 -> park_scooter(Pos end, int code) -> Price
 * 6 -> set_notification(Boolean on, Pos desired) -> Boolean
 * 7 -> receive_notifications() -> Reward
 */

public class UserAPI {

    private Connection con;
    private Demultiplexer demultiplexer;

    public UserAPI(Socket s) throws IOException{
        this.con = new Connection(s);
        this.demultiplexer=new Demultiplexer(this.con);
    }

    public int register(User u) {

        // Create a frame with frameType 0 and send it trough the demultiplexer
        Frame request = new Frame(0, u);
        if (1==demultiplexer.send(request)){
            return -1;
        }
        
        try {
            // Receive a frame from the demultiplexer
            Frame response = demultiplexer.receive(0);

            // Parse the response value
            return (Integer)response.getData();
        } catch (Exception e) {
            return -1;
        }
    }

    public Boolean login(User u) {
        
        // Create a frame with frameType 1 and send it trough the demultiplexer
        Frame request = new Frame(1, u);
        if (1==demultiplexer.send(request)){
            return false;
        }

        try {
            // Receive a frame from the demultiplexer
            Frame response = demultiplexer.receive(1);

            // Parse the response value
            return (1== (Integer)response.getData());
        } catch (Exception e) {
            return false;
        }
    }

    public List<Pos> get_available(Pos current){
        
        // Create a frame with frameType 2 and send it trough the demultiplexer
        Frame request = new Frame(2, current);
        if (1==demultiplexer.send(request)){
            return null;
        }

        // Receive a frame from the demultiplexer
        try {
            Frame response = demultiplexer.receive(2);
            // Return list of available positions
            List<?> avail =(List<?>)response.getData();
            List<Pos> list = new ArrayList<Pos>();
            for (Object o : avail) {
                list.add((Pos)o);
            }
            return list;
        } catch (Exception e) {
            return null;
        }

    }

    public List<Reward> check_rewards(Pos current){
        // Create a frame with frameType 3 and send it trough the demultiplexer
        Frame request = new Frame(3, current);
        if (1 == demultiplexer.send(request)) {
            return null;
        }

        // Receive a frame from the demultiplexer
        try {
            Frame response = demultiplexer.receive(2);
            // Return list of available positions
            List<?> avail = (List<?>) response.getData();
            List<Reward> list = new ArrayList<Reward>();
            for (Object o : avail) {
                list.add((Reward) o);
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public Reserve reserve_scooter(Pos start){
        
        // Create a frame with frameType 4 and send it trough the demultiplexer
        Frame request = new Frame(4, start);
        if (1==demultiplexer.send(request)){
            return null;
        }

        // Receive a frame from the demultiplexer
        try {
            Frame response = demultiplexer.receive(4);
            return (Reserve)response.getData();
        } catch (Exception e) {
            return null;
        }
    }

    public Price park_scooter(Reserve res){
        
        // Create a frame with frameType 5 and send it trough the demultiplexer
        Frame request = new Frame(5, res);
        if (1 == demultiplexer.send(request)) {
            return null;
        }

        // Receive a frame from the demultiplexer
        try {
            Frame response = demultiplexer.receive(5);
            return (Price)response.getData();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean set_notifications(Pos desired){
            
        // Create a frame with frameType 6 and send it trough the demultiplexer
        Frame request = new Frame(6, desired);
        if (1==demultiplexer.send(request)){
            return false;
        }

        // Receive a frame from the demultiplexer
        try {
            Frame response = demultiplexer.receive(2);
            return (1==(Integer)response.getData());
        } catch (Exception e) {
            return false;
        }
    }

    public Reward receive_notifications(){
            
        // Create a frame with frameType 7 and send it trough the demultiplexer
        Frame request = new Frame(7, null);
        if (1 == demultiplexer.send(request)) {
            return null;
        }

        // Receive a frame from the demultiplexer
        try {
            Frame response = demultiplexer.receive(7);
            return (Reward)response.getData();
        } catch (Exception e) {
            return null;
        }
    }
}