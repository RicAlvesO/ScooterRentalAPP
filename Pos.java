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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pos) {
            Pos p = (Pos) o;
            return this.x == p.x && this.y == p.y;
        }
        return false;
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

    public String toString() {
        return "Pos " + "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        return (int) x*1000 + y;
    }
}