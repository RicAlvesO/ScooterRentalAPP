import java.util.ArrayList;
import java.util.List;

public class UserAPI {

    private Demultiplexer demultiplexer;

    public UserAPI(Demultiplexer demu){
        this.demultiplexer=demu;
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
        demultiplexer.send(request);

        // Receive a frame from the demultiplexer
        Frame response = demultiplexer.receive(0);
        String out = new String(response.getData());

        // Parse the response value
        try {
            return Integer.parseInt(out);
        } catch (NumberFormatException e) {
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
        demultiplexer.send(request);

        // Receive a frame from the demultiplexer
        Frame response = demultiplexer.receive(1);
        String out = new String(response.getData());

        // Parse the response value
        try {
            return (1==Integer.parseInt(out));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Pos> get_available(Pos current){
        
        // Create data camp of frame
        byte[] data = current.toString().getBytes();

        // Create a frame with frameType 2 and send it trough the demultiplexer
        Frame request = new Frame(2, data);
        demultiplexer.send(request);

        // Receive a frame from the demultiplexer
        Frame response = demultiplexer.receive(2);
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
        demultiplexer.send(request);

        // Receive a frame from the demultiplexer
        Frame response = demultiplexer.receive(3);
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
}