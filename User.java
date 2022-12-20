import java.io.Serializable;

public class User implements Serializable{
    
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
}
