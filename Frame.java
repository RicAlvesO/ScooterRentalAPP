import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class Frame{
    private int frameType;
    private boolean response;
    private Object data;

    public Frame(int frameType, boolean response, Object data) {
        this.frameType = frameType;
        this.response = response;
        this.data = data;
    }

    public int getFrameType() {
        return frameType;
    }

    public Object getData() {
        return data;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(frameType);
        out.writeBoolean(response);
        switch (frameType) {
            case 0:
                if (response) {
                    out.writeInt((int) data);
                } else {
                    User.serialize((User)data, out);
                }
                break;
            case 1:
                if (response) {
                    out.writeBoolean((boolean) data);
                } else {
                    User.serialize((User) data, out);
                }
                break;
            case 2:
                if (response) {
                    List<?> list = (List<?>) data;
                    out.writeInt(list.size());
                    for (Object o : list) {
                        Pos.serialize((Pos)o, out);
                    }
                } else {
                    Pos.serialize((Pos) data, out);
                }
                break;
            case 3:
                if (response) {
                    List<?> list = (List<?>) data;
                    out.writeInt(list.size());
                    for (Object o : list) {
                        Reward.serialize((Reward)o, out);
                    }
                } else {
                    Pos.serialize((Pos) data, out);
                }
                break;
            case 4:
                if (response) {
                    Reserve.serialize((Reserve) data, out);
                } else {
                    Pos.serialize((Pos) data, out);
                }
                break;
            case 5:
                if (response) {
                    Price.serialize((Price) data, out);
                } else {
                    Reserve.serialize((Reserve) data, out);
                }
                break;
            case 6:
                if (response) {
                    out.writeBoolean((boolean) data);
                } else {
                    Pos.serialize((Pos) data, out);
                }
                break;
            case 7:
                if (response) {
                    Reward.serialize((Reward) data, out);
                }
                break;
        }
    }

    public static Frame deserialize(DataInputStream in) {
        return null;
    }
}