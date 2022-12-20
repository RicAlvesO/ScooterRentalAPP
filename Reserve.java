import java.io.Serializable;

public class Reserve implements Serializable{
    
    private int code;
    private Pos start;
    private Pos end;
    private String datetime;

    public Reserve(int code, Pos start, String datetime) {
        this.code = code;
        this.start = start;
        this.datetime = datetime;
        this.end = null;
    }

    public void setEnd(Pos end){
        this.end = end;
    }

    public int getCode() {
        return code;
    }

    public Pos getStart() {
        return start;
    }

    public Pos getEnd() {
        return end;
    }

    public String getDatetime() {
        return datetime;
    }

    public boolean isFinished(){
        return end != null;
    }

    public double getDistance(){
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }
}
