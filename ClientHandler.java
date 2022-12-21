public class ClientHandler implements Runnable{
    //receive the socket from server and start the thread
    private Connection con;
    private boolean running;
    private UserDB userDB;
    private String current;

    public ClientHandler(Connection con, UserDB userDB) {
        try {
            this.con = con;
            this.userDB = userDB;
            this.running = true;
            this.current = null;
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
                    if (this.current != null) {
                        this.userDB.logoff(this.current);
                        System.out.println("User " + this.current + " disconnected");
                    }
                    System.out.println("Connection closed by client");
                    return;
                }else if(msg.getFrameType() >= 0 && msg.getFrameType() <= 7){
                    this.handleQuery(msg);
                }
            }catch (Exception e) {
                con.close();
                if (this.current != null) {
                    this.userDB.logoff(this.current);
                    System.out.println("User " + this.current + " disconnected");
                }
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
            switch (f.getFrameType()){
                case 0:
                    User u1=(User)f.getData();
                    int newID=userDB.addUser(u1);
                    if (newID!=-1){
                        System.out.println("New user: " + u1.getName()+ " " + u1.getId());
                        this.current=u1.getName();
                    }
                    this.con.send(new Frame(0,true,newID));
                    break;
                case 1:
                    User u=(User)f.getData();
                    boolean login=userDB.login(u.getName(),u.getPassword());
                    if (login){
                        System.out.println("User logged in: " + u.getName());
                        this.current=u.getName();
                    }
                    this.con.send(new Frame(1,true,login));
                    break;
            }

        } catch (Exception e) {
            System.out.println("Error in handleQuery: " + e.getMessage());
        }
        System.out.println("Sent: " + f.getFrameType());
    }
}

