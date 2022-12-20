import java.io.Serializable;

public class Reward implements Serializable{
    
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
}
