package org.aston.ecommerce.product;

public class Purchase {

    private String product_id;
    private String num_ordered;

    public Purchase(String product_id, String num_ordered) {
        this.product_id = product_id;
        this.num_ordered = num_ordered;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getNum_ordered() {
        return num_ordered;
    }

    public void setNum_ordered(String num_ordered) {
        this.num_ordered = num_ordered;
    }
}
