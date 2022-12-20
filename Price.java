import java.io.DataOutputStream;
import java.io.IOException;

public class Price{
    
    private double price;
    private double discount;

    public Price(double price, double discount) {
        this.price = price;
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public double getFinalPrice() {
        return price - (price * discount);
    }

    public static void serialize(Price p, DataOutputStream out) throws IOException {
        out.writeDouble(p.getPrice());
        out.writeDouble(p.getDiscount());
    }
}
