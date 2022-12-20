import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class User{
    
    private String name;
    private String password;
    private int id;
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.id = 0;
    }
    
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    public String getPassword() {
        return password;
    }

    public static void serialize(User u, DataOutputStream out) throws IOException {
        out.writeUTF(u.getName());
        out.writeUTF(u.getPassword());
        out.writeInt(u.getId());
    }

    public static User deserialize(DataInputStream in) throws IOException {
        String name = in.readUTF();
        String password = in.readUTF();
        int id = in.readInt();
        User u = new User(name, password);
        u.setId(id);
        return u;
    }
}
