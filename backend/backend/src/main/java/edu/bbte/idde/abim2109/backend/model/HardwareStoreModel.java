package edu.bbte.idde.abim2109.backend.model;

public class HardwareStoreModel implements EnityID {
    private Integer id;
    private String name;
    private String category;
    private String description;
    private Integer price;
    private Integer quantity;

    public HardwareStoreModel() {
    }

    public HardwareStoreModel(
            String name,
            String category,
            String description,
            Integer price,
            Integer quantity) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Hardware: { "
                + "id=" + id
                + ", name='" + name
                + '\''
                + ", category='" + category
                + '\''
                + ", description='" + description
                + '\''
                + ", price=" + price
                + ", quantity=" + quantity
                + " }";
    }
}
