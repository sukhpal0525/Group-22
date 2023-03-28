package org.aston.ecommerce.product;

import org.aston.ecommerce.file.ImageInfo;

public class ProductImage {
    private Product product;
    private ImageInfo imageInfo;

    public ProductImage(Product product, ImageInfo imageInfo) {
        this.product = product;
        this.imageInfo = imageInfo;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public ImageInfo getImageInfo() {
        return imageInfo;
    }
    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }
}
