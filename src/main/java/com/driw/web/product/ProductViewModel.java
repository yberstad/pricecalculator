package com.driw.web.product;

class ProductViewModel {
    private String productName;
    private int numberOfUnits;
    private Double total;

    public ProductViewModel(String productName, int numberOfUnits, Double total) {
        this.productName = productName;
        this.numberOfUnits = numberOfUnits;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public Double getTotal() {
        return total;
    }
}
