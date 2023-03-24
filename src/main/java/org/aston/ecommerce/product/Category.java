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

    @Override
    public String toString() {
        switch(this){
            case PROCESSOR: return "CPU";
            case MOTHERBOARD: return "Motherboard";
            case GPU: return "GPU";
            case MEMORY: return "Memory";
            case MOUSE: return "Mouse";
            default: throw new IllegalArgumentException();
        }
    }
}
