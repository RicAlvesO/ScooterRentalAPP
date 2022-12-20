import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Pos {
    
    private int x;
    private int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static void serialize(Pos p,DataOutputStream out) throws IOException {
        out.writeInt(p.getX());
        out.writeInt(p.getY());
    }

    public static Pos deserialize(DataInputStream in) throws IOException {
        int x = in.readInt();
        int y = in.readInt();
        return new Pos(x, y);
    }
}