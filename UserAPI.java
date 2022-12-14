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

    public int register(String username, String password) {
        
        // Create data camp of frame
        StringBuilder in = new StringBuilder();
        in.append(username);
        in.append(";");
        in.append(password);
        byte[] data = in.toString().getBytes();

        // Create a frame with frameType 0 and send it trough the demultiplexer
        Frame request = new Frame(0, data);
        if (1==demultiplexer.send(request)){
            return -1;
        }
        
        try {
            // Receive a frame from the demultiplexer
            Frame response = demultiplexer.receive(0);
            String out = new String(response.getData());

            // Parse the response value
            return Integer.parseInt(out);
        } catch (Exception e) {
            return -1;
        }
    }

    public Boolean login(String username, String password) {
        
        // Create data camp of frame
        StringBuilder in = new StringBuilder();
        in.append(username);
        in.append(";");
        in.append(password);
        byte[] data = in.toString().getBytes();
        
        // Create a frame with frameType 1 and send it trough the demultiplexer
        Frame request = new Frame(1, data);
        if (1==demultiplexer.send(request)){
            return false;
        }

        try {
            // Receive a frame from the demultiplexer
            Frame response = demultiplexer.receive(1);
            String out = new String(response.getData());

            // Parse the response value
            return (1==Integer.parseInt(out));
        } catch (Exception e) {
            return false;
        }
    }

    public List<Pos> get_available(Pos current){
        
        // Create data camp of frame
        byte[] data = current.toString().getBytes();

        // Create a frame with frameType 2 and send it trough the demultiplexer
        Frame request = new Frame(2, data);
        if (1==demultiplexer.send(request)){
            return null;
        }

        // Receive a frame from the demultiplexer
        Frame response;
        try {
            response = demultiplexer.receive(2);
        } catch (Exception e) {
            return null;
        }
        String out = new String(response.getData());

        // Build list of available positions
        String[] available = out.split(";");
        List<Pos> list = new ArrayList<Pos>();
        for (String s : available) {

            // Parse position from string
            int x = Integer.parseInt(s.split(",")[0]);
            int y = Integer.parseInt(s.split(",")[1]);

            // Add position to list
            list.add(new Pos(x, y));
        }

        // Return list of available positions
        return list;
    }

    public List<Reward> check_rewards(Pos current){
            
        // Create data camp of frame
        byte[] data = current.toString().getBytes();

        // Create a frame with frameType 3 and send it trough the demultiplexer
        Frame request = new Frame(3, data);
        if (1 == demultiplexer.send(request)) {
            return null;
        }

        // Receive a frame from the demultiplexer
        Frame response;
        try {
            response = demultiplexer.receive(3);
        } catch (Exception e) {
            return null;
        }
        String out = new String(response.getData());

        // Build list of rewards
        String[] rewards = out.split(";");
        List<Reward> list = new ArrayList<Reward>();
        for (String s : rewards) {
            
            //Parse reward from string
            String[] reward = s.split(",");
            int x1 = Integer.parseInt(reward[0]);
            int y1 = Integer.parseInt(reward[1]);
            int x2 = Integer.parseInt(reward[2]);
            int y2 = Integer.parseInt(reward[3]);
            double discount = Double.parseDouble(reward[4]);

            // Add reward to list
            list.add(new Reward(new Pos(x1, y1), new Pos(x2, y2), discount));
        }

        // Return list of rewards
        return list;
    }

    public Reserve reserve_scooter(Pos start){
        
        // Create data camp of frame
        byte[] data = start.toString().getBytes();

        // Create a frame with frameType 4 and send it trough the demultiplexer
        Frame request = new Frame(4, data);
        if (1==demultiplexer.send(request)){
            return null;
        }

        // Receive a frame from the demultiplexer
        Frame response;
        try {
            response = demultiplexer.receive(4);
        } catch (Exception e) {
            return null;
        }
        String out = new String(response.getData());

        // Parse reserve from string
        String[] reserve = out.split(";");
        int code = Integer.parseInt(reserve[0]);
        int x = Integer.parseInt(reserve[1]);
        int y = Integer.parseInt(reserve[2]);
        String datetime = reserve[3];

        // Return reserve
        return new Reserve(code, new Pos(x, y), datetime);
    }

    public Price park_scooter(Pos end, int code){
        
        // Create data camp of frame
        StringBuilder in = new StringBuilder();
        in.append(end.toString());
        in.append(";");
        in.append(code);
        byte[] data = in.toString().getBytes();

        // Create a frame with frameType 5 and send it trough the demultiplexer
        Frame request = new Frame(5, data);
        if (1 == demultiplexer.send(request)) {
            return null;
        }

        // Receive a frame from the demultiplexer
        Frame response;
        try {
            response = demultiplexer.receive(5);
        } catch (Exception e) {
            return null;
        }
        String out = new String(response.getData());

        // Parse price from string
        String[] price = out.split(";");
        double amount = Double.parseDouble(price[0]);
        double discount = Double.parseDouble(price[1]);

        // Return price
        return new Price(amount, discount);
    }

    public boolean set_notifications(Pos desired, Boolean on){
            
        // Create data camp of frame
        StringBuilder in = new StringBuilder();
        in.append(desired.toString());
        in.append(";");
        in.append(on);
        byte[] data = in.toString().getBytes();

        // Create a frame with frameType 6 and send it trough the demultiplexer
        Frame request = new Frame(6, data);
        if (1==demultiplexer.send(request)){
            return false;
        }

        // Receive a frame from the demultiplexer
        Frame response;
        try {
            response = demultiplexer.receive(2);
        } catch (Exception e) {
            return false;
        }
        String out = new String(response.getData());

        // Parse response value
        try {
            return (1==Integer.parseInt(out));
        } catch (NumberFormatException e) {
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
        Frame response;
        try {
            response = demultiplexer.receive(7);
        } catch (Exception e) {
            return null;
        }
        String out = new String(response.getData());

        // Parse reward from string
        String[] reward = out.split(",");
        int x1 = Integer.parseInt(reward[0]);
        int y1 = Integer.parseInt(reward[1]);
        int x2 = Integer.parseInt(reward[2]);
        int y2 = Integer.parseInt(reward[3]);
        double discount = Double.parseDouble(reward[4]);

        // Return reward
        return new Reward(new Pos(x1, y1), new Pos(x2, y2), discount);
    }
}