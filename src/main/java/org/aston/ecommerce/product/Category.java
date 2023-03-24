package org.aston.ecommerce.product;

public enum Category {

    PROCESSOR(0),
    MOTHERBOARD(1),
    GPU(2),
    MEMORY(3),
    MOUSE(4);

    private final Integer id;

    Category(Integer id) {
        this.id = id;
    }

    public static Category parseCategoryStr(String category) {
        try {
            return Category.valueOf(category);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public Integer id() {
        return id;
    }

}
