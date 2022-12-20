public class ClientHandler implements Runnable{
    //receive the socket from server and start the thread
    private Connection con;
    private boolean running;

    public ClientHandler(Connection con) {
        try {
            this.con = con;
            this.running = true;
        } catch (Exception e) {
            System.out.println("Error in ClientHandler constructor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while(running){
            try {
                Frame msg = con.receive();
                if (msg.getFrameType() == -1) {
                    running = false;
                    System.out.println("Connection closed by client");
                    return;
                }else if(msg.getFrameType() >= 0 && msg.getFrameType() <= 7){
                    this.handleQuery(msg);
                }
            }catch (Exception e) {
                System.out.println("Connection closed by client error");
                return;
            }
        }
        this.con.close();
        System.out.println("Server closed");

    }

    public void handleQuery(Frame f){
        System.out.println("Received: " + f.getFrameType());
        try {
            this.con.send(f);
        } catch (Exception e) {
            System.out.println("Error in handleQuery: " + e.getMessage());
        }
        System.out.println("Sent: " + f.getFrameType());
    }
}

