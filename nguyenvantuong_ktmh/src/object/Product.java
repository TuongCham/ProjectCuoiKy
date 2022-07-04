package object;

import javafx.scene.control.TextField;

public class Product {
    private String descriptions;
    private Double price;
    private TextField quality;

    public Product(String descriptions, Double price, TextField quality) {
        this.descriptions = descriptions;
        this.price = price;
        this.quality = quality;
    }

    public Product(String descriptions, Double price) {
        this.descriptions = descriptions;
        this.price = price;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TextField getQuality() {
        return quality;
    }

    public void setQuality(TextField quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "Product{" +
                "descriptions='" + descriptions + '\'' +
                ", price=" + price +
                ", quality=" + quality +
                '}';
    }
}
