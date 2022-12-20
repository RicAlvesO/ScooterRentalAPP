import java.io.DataOutputStream;
import java.io.IOException;

public class Reward{
    
    private Pos start;
    private Pos end;
    private double discount;

    public Reward(Pos start, Pos end, double discount) {
        this.start = start;
        this.end = end;
        this.discount = discount;
    }

    public Pos getStart() {
        return start;
    }

    public Pos getEnd() {
        return end;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean confirmReward(Pos start, Pos end){
        return this.start.equals(start) && this.end.equals(end);
    }

    public static void serialize(Reward r, DataOutputStream out) throws IOException {
        Pos p1 = r.getStart();
        Pos p2 = r.getEnd();
        out.writeInt(p1.getX());
        out.writeInt(p1.getY());
        out.writeInt(p2.getX());
        out.writeInt(p2.getY());
        out.writeDouble(r.getDiscount());
    }
}
