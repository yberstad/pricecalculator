package com.driw.controller.product.viewmodels;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InputFormViewModel {

    @NotNull
    @Min(1)
    private long productId;

    @NotNull
    @Min(0)
    @Max(100)
    private int numberOfPackages;

    @NotNull
    @Min(0)
    @Max(100)
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
