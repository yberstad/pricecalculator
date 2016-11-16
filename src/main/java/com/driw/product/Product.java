package com.driw.product;

import com.driw.base.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class Product extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int NUMBER_OF_DECIMAL_PLACES = 2;

    @Id
    @Column(name = "product_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Double packagePrice;

    private int unitsPrPackage;

    private int packageDiscountPercentage;

    private int packageDiscountThresholdValue;

    private int unitIncreasePercentage;

    protected Product() {
    }

    public Product(String name,
                   Double packagePrice,
                   int unitsPrPackage,
                   int packageDiscountPercentage,
                   int packageDiscountThresholdValue,
                   int unitIncreasePercentage) {
        this.name = name;
        this.packagePrice = packagePrice;
        this.unitsPrPackage = unitsPrPackage;
        this.packageDiscountPercentage = packageDiscountPercentage;
        this.packageDiscountThresholdValue = packageDiscountThresholdValue;
        this.unitIncreasePercentage = unitIncreasePercentage;
    }

    public Double getTotalPrice(int numberOfUnits, int numberOfPackages){
        numberOfUnits += numberOfPackages * this.unitsPrPackage;
        return this.calculatePrice(numberOfUnits);
    }

    public Double getTotalPrice(int numberOfUnits){
        return this.calculatePrice(numberOfUnits);
    }

    private Double calculatePrice(int numberOfUnits) {

        Assert.isTrue(numberOfUnits >= 0, "Cannot use negative numbers");
        int numberOfPackages = numberOfUnits / this.unitsPrPackage;
        int reminderOfUnits = numberOfUnits % this.unitsPrPackage;

        // Calculate the package cost
        Double calculatedPackagePrice = this.packagePrice;
        if(numberOfPackages >= this.packageDiscountThresholdValue) {
            calculatedPackagePrice = this.packagePrice * getDiscountFactor(this.packageDiscountPercentage);
        }
        Double total = numberOfPackages * calculatedPackagePrice;

        // Calculate the cost for reminding units.
        if(reminderOfUnits > 0)
        {
            Double unitPrice =
                    (this.packagePrice / this.unitsPrPackage) * this.getIncreaseFactor(this.unitIncreasePercentage);
            total += reminderOfUnits * unitPrice;
        }

        return roundUp(total, NUMBER_OF_DECIMAL_PLACES);
    }

    private Double getDiscountFactor(int percentage) {
        return roundUp((100 - percentage) / 100.0, 2);
    }

    private Double getIncreaseFactor(int percentage) {
        return roundUp(1 + (percentage / 100.0), 2);
    }

    private Double roundUp(Double value, int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public String getName() {
        return name;
    }

    public int getUnitsPrPackage() { return unitsPrPackage; }

    public Long getId() {
        return id;
    }

    @Override
    protected void setId(Long id) {
        this.id = id;
    }

}
