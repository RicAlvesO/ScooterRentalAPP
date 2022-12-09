public class UserAPI {

    private Demultiplexer demultiplexer;

    public UserAPI(Demultiplexer demu){
        this.demultiplexer=demu;
    }

    public int register(String username, String password) {
        
        // Create data camp of frame
        StringBuilder in = new StringBuilder();
        in.append(username);
        in.append(",");
        in.append(password);
        byte[] data = in.toString().getBytes();

        // Create a frame with frameType 0 and send it trough the demultiplexer
        Frame request = new Frame(0, data);
        demultiplexer.send(request);

        // Receive a frame from the demultiplexer
        Frame response = demultiplexer.receive(0);
        String out = new String(response.getData());
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
        in.append(",");
        in.append(password);
        byte[] data = in.toString().getBytes();
        
        // Create a frame with frameType 1 and send it trough the demultiplexer
        Frame request = new Frame(1, data);
        demultiplexer.send(request);

        // Receive a frame from the demultiplexer
        Frame response = demultiplexer.receive(1);
        String out = new String(response.getData());
        try {
            return (1==Integer.parseInt(out));
        } catch (NumberFormatException e) {
            return false;
        }
    }
}