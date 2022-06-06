package gap.model;


import gap.constants.Category;
import gap.constants.Color;
import gap.constants.Gender;
import gap.constants.Size;

import java.util.Map;

public class Product {
    private String id;
    private String name;
    private Category category;
    private Color color;
    private double price;
    private Gender idealFor;
    private Map<Size, Integer> size;

    public Product(String id, String name, Category category, Color color, double price, Gender idealFor, Map<Size, Integer> size) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.color = color;
        this.price = price;
        this.idealFor = idealFor;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Color getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }

    public Gender getIdealFor() {
        return idealFor;
    }

    public Map<Size, Integer> getSize() {
        return size;
    }

    public String getProductSizeAvailability() {
        return size.entrySet().stream().map(entry -> entry.getKey() + " - " + entry.getValue()).reduce("", (sizes, size) -> sizes + "\n" + size.toString());
    }

    public boolean outOfStock() {
        return size.entrySet().stream().allMatch(entry -> entry.getValue() <= 0);
    }

    public String showSizes() {
        return outOfStock() ? "* Out of Stock *" : size.entrySet().stream().map(size -> size.getKey() + "-" + size.getValue()).reduce((sizes, size) -> sizes + ", " + size).get();
    }

    public String productShowcaseInMenu() {
        return getName() + "\t" + getPrice() + "\n   " + getColor() + "\t" + showSizes();
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", color=" + color +
                ", price=" + price +
                ", idealFor=" + idealFor +
                ", size=" + getProductSizeAvailability() +
                '}';
    }
}