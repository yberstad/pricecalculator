package com.driw.web.product.viewmodels;

import com.driw.product.Product;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CalculatorViewModel {
    private Set<OptionViewModel> optionList;
    private Boolean displaySelectedProduct;
    private String selectedProduct;
    private int selectedTotalUnits;
    private Double total;

    public CalculatorViewModel(List<Product> productList) {
        optionList = productList
                .stream()
                .map(product -> new OptionViewModel(product.getId(), product.getName()))
                .collect(Collectors.toSet());
    }

    public Set<OptionViewModel> getOptionList() {
        return optionList;
    }

    public void setOptionList(Set<OptionViewModel> optionList) {
        this.optionList = optionList;
    }

    public Boolean getDisplaySelectedProduct() {
        return displaySelectedProduct;
    }

    public void setDisplaySelectedProduct(Boolean displaySelectedProduct) {
        this.displaySelectedProduct = displaySelectedProduct;
    }

    public String getSelectedProductName() {
        return selectedProduct;
    }

    public void setSelectedProductName(String selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public int getSelectedTotalUnits() {
        return selectedTotalUnits;
    }

    public void setSelectedTotalUnits(int selectedTotalUnits) {
        this.selectedTotalUnits = selectedTotalUnits;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
