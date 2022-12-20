import java.io.Serializable;

public class Price implements Serializable{
    
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
}
