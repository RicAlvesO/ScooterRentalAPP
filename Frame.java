public class Frame {
    private int frameType;
    private int tag; //adicionei pq nao tinha
    private int length; // adicionaue pq é preciso para criar o array de bytes
    private byte[] data;

    public Frame(int frameType, byte[] data, int tag) {
        this.frameType = frameType;
        this.tag = tag;
        this.length = data.length;
        this.data = data;
    }

    public int getFrameType() {
        return frameType;
    }

    public int getTag() {
        return tag;
    }

    public int getLength() {
        return length;
    }

    public byte[] getData() {
        return data;
    }
}