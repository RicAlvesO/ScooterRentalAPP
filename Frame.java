import java.io.Serializable;

public class Frame implements Serializable{
    private int frameType;
    private Object data;

    public Frame(int frameType, byte[] data) {
        this.frameType = frameType;
        this.data = data;
    }

    public Frame(int frameType, Object data) {
        this.frameType = frameType;
        this.data = data.toString().getBytes();
    }

    public int getFrameType() {
        return frameType;
    }

    public Object getData() {
        return data;
    }
}