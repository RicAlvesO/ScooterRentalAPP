public class Frame {
    private int frameType;
    private byte[] data;

    public Frame(int frameType, byte[] data) {
        this.frameType = frameType;
        this.data = data;
    }

    public int getFrameType() {
        return frameType;
    }

    public int getLength() {
        return this.data.length;
    }

    public byte[] getData() {
        return data;
    }
}