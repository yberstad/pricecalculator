package com.driw.web.product.viewmodels;

/**
 * Created by oyvindhabberstad on 17/11/2016.
 */
public class InputFormViewModel {
    private long productId;
    private int numberOfPackages;
    private int numberOfUnits;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(int numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(int numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }
}
