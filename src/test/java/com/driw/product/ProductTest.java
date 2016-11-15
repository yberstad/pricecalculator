package com.driw.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {


    private final static Double PACKAGE_PRICE_PRODUCT_1 = 175.0;
    private final static int UNITS_PR_PACKAGE_PRODUCT_1 = 20;

    private final static Double PACKAGE_PRICE_PRODUCT_2 = 825.0;
    private final static int UNITS_PR_PACKAGE_PRODUCT_2 = 5;

    private final static int PACKAGE_DISCOUNT_PERCENTAGE = 10;
    private final static int PACKAGE_DISCOUNT_THRESHOLD_VALUE = 3;
    private final static int UNIT_INCREASE_PERCENTAGE = 30;

    private Product getStubProduct1(){
        String name = "Product1";
        return new Product(name, PACKAGE_PRICE_PRODUCT_1, UNITS_PR_PACKAGE_PRODUCT_1, PACKAGE_DISCOUNT_PERCENTAGE,
                PACKAGE_DISCOUNT_THRESHOLD_VALUE, UNIT_INCREASE_PERCENTAGE);
    }
    private Product getStubProduct2(){
        String name = "Product2";

        return new Product(name, PACKAGE_PRICE_PRODUCT_2, UNITS_PR_PACKAGE_PRODUCT_2, PACKAGE_DISCOUNT_PERCENTAGE,
                PACKAGE_DISCOUNT_THRESHOLD_VALUE, UNIT_INCREASE_PERCENTAGE);
    }

    @Test
    public void getTotalPrice_CountMatchesNumberOfUnitsPrPackage_PriceShouldMatchPackagePrice() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(UNITS_PR_PACKAGE_PRODUCT_1))
                .isEqualTo(PACKAGE_PRICE_PRODUCT_1);
    }

    @Test
    public void getTotalPrice_CountMatchWholePackagesButLessThanDiscountThreshold_PriceShouldMatchPackagePriceMultipiedWithCount() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(UNITS_PR_PACKAGE_PRODUCT_1 * 2))
                .isEqualTo(PACKAGE_PRICE_PRODUCT_1 * 2);
    }

    @Test
    public void getTotalPrice_CountMatchWholePackagesAndIsEqualToDiscountThreshold_PriceShouldBeDiscounted() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(UNITS_PR_PACKAGE_PRODUCT_1 * PACKAGE_DISCOUNT_THRESHOLD_VALUE))
                .isLessThan(PACKAGE_PRICE_PRODUCT_1 * PACKAGE_DISCOUNT_THRESHOLD_VALUE);
    }
}