package org.aston.ecommerce.order;

public enum Status {
    UNPROCESSED(0),
    PROCESSED(1),
    SHIPPED(2);

    private final Integer id;

    Status(Integer id){this.id = id;}

    public static Status parseStatusStr(String status) {
        try {
            return Status.valueOf(status);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public Integer id() {
        return this.id;
    }

    @Override
    public String toString() {
        switch(this){
            case UNPROCESSED: return "Unprocessed";
            case PROCESSED: return "Processed";
            case SHIPPED: return "Shipped";
            default: throw new IllegalArgumentException();
        }
    }
}
