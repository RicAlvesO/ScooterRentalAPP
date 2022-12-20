import java.io.DataOutputStream;
import java.io.IOException;

public class Reserve{
    
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

    public static void serialize(Reserve r, DataOutputStream out) throws IOException {
        out.writeInt(r.getCode());
        Pos p1 = r.getStart();
        out.writeInt(p1.getX());
        out.writeInt(p1.getY());
        Pos p2 = r.getEnd();
        if (p2 == null){
            out.writeInt(-1);
            out.writeInt(-1);
        } else {
            out.writeInt(p2.getX());
            out.writeInt(p2.getY());
        }
        out.writeUTF(r.getDatetime());
    } 
}
