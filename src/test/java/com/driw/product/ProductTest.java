package com.driw.product;

import com.driw.utils.SetIdHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {


    private final static Double PACKAGE_PRICE = 175.0;
    private final static int UNITS_PR_PACKAGE = 20;

    private final static int PACKAGE_DISCOUNT_THRESHOLD_VALUE = 3;

    private final static int PACKAGE_DISCOUNT_PERCENTAGE = 10;
    private final static double PACKAGE_DISCOUNT_PERCENTAGE_FACTOR = .9;

    private final static int UNIT_INCREASE_PERCENTAGE = 30;
    private final static double UNIT_INCREASE_PERCENTAGE_FACTOR = 1.3;
    private static final String PRODUCT_NAME = "Product1";
    private static final Long PRODUCT_ID = 1L;

    private Double unitPrice = 0.0;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        unitPrice = (PACKAGE_PRICE / UNITS_PR_PACKAGE) * UNIT_INCREASE_PERCENTAGE_FACTOR;
    }

    @Test
    public void getTotalPrice_CountMatchesNumberOfUnitsPrPackage_PriceShouldMatchPackagePrice() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(UNITS_PR_PACKAGE))
                .isEqualTo(PACKAGE_PRICE);
    }

    @Test
    public void getTotalPrice_CountMatchWholePackagesButLessThanDiscountThreshold_PriceShouldMatchPackagePriceMultipiedWithCount() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(UNITS_PR_PACKAGE * 2))
                .isEqualTo(PACKAGE_PRICE * 2);
    }

    @Test
    public void getTotalPrice_CountMatchWholePackagesAndIsEqualToDiscountThreshold_PriceShouldBeDiscounted() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(UNITS_PR_PACKAGE * PACKAGE_DISCOUNT_THRESHOLD_VALUE))
                .isLessThan(PACKAGE_PRICE * PACKAGE_DISCOUNT_THRESHOLD_VALUE);
    }

    @Test
    public void getTotalPrice_CountMatchWholePackagesAndIsEqualToDiscountThreshold_PriceShouldBeDiscountedWithCorrectFactor() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(UNITS_PR_PACKAGE * PACKAGE_DISCOUNT_THRESHOLD_VALUE))
                .isEqualTo(PACKAGE_PRICE * PACKAGE_DISCOUNT_THRESHOLD_VALUE * PACKAGE_DISCOUNT_PERCENTAGE_FACTOR);
    }

    @Test
    public void getTotalPrice_CountMatchWholePackagesAndIsHigherToDiscountThreshold_PriceShouldBeDiscountedWithCorrectFactor() throws Exception {
        int packageCount = (PACKAGE_DISCOUNT_THRESHOLD_VALUE + 1);
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(UNITS_PR_PACKAGE * packageCount))
                .isEqualTo(PACKAGE_PRICE * packageCount * PACKAGE_DISCOUNT_PERCENTAGE_FACTOR);
    }

    @Test
    public void getTotalPrice_CountLowerThanOnePackage_PriceShouldBeIncreasedWithUnitIncreaseFactor() throws Exception {
        int units = (UNITS_PR_PACKAGE - 1);
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(units))
                .isEqualTo(roundUp(units * unitPrice, 2));
    }

    @Test
    public void getTotalPrice_CountMatchPackagesAndUnitsAndBellowDiscountThreshold_PriceShouldBeIncreasedWithIncreaseFactorForUnits() throws Exception {
        int units = (UNITS_PR_PACKAGE - 1);
        int packages = (PACKAGE_DISCOUNT_THRESHOLD_VALUE - 1) ;
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(units + packages * UNITS_PR_PACKAGE))
                .isEqualTo(roundUp((units * unitPrice) + (packages * PACKAGE_PRICE), 2));
    }

    @Test
    public void getTotalPrice_CountMatchPackagesAndUnitsAndAboveDiscountThreshold_PriceShouldBeIncreasedWithIncreaseFactorForUnits() throws Exception {
        int units = (UNITS_PR_PACKAGE - 1);
        int packages = (PACKAGE_DISCOUNT_THRESHOLD_VALUE + 1) ;
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(units + packages * UNITS_PR_PACKAGE))
                .isEqualTo(roundUp((units * unitPrice) + (packages * PACKAGE_PRICE * PACKAGE_DISCOUNT_PERCENTAGE_FACTOR), 2));
    }

    @Test
    public void getTotalPrice_UsedWithUnitsAndPackages_PriceShouldBeEqualToUsingUnits() throws Exception {
        int units = (UNITS_PR_PACKAGE - 1);
        int packages = (PACKAGE_DISCOUNT_THRESHOLD_VALUE + 1) ;
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(units, packages))
                .isEqualTo(product.getTotalPrice(units + packages * UNITS_PR_PACKAGE));
    }

    @Test
    public void getTotalPrice_ZeroNumberOfUnits_PriceShouldZero() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getTotalPrice(0))
                .isEqualTo(0);
        assertThat(product.getTotalPrice(0,0))
                .isEqualTo(0);
    }

    @Test
    public void getTotalPrice_NegativeNumberOfUnits_ShouldThorwException() throws Exception {
        Product product = getStubProduct1();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("Cannot use negative numbers"));
        product.getTotalPrice(-1);
    }

    @Test
    public void getName_NameSetThroughConstructor_ShouldReturnSameName() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getName())
                .isEqualTo(PRODUCT_NAME);
    }

    @Test
    public void getUnitsPrPackage_UnitsPrPackageSetThroughConstructor_ShouldReturnSameValue() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getUnitsPrPackage())
                .isEqualTo(UNITS_PR_PACKAGE);
    }

    @Test
    public void getId_ShouldReturnProductId() throws Exception {
        Product product = getStubProduct1();
        assertThat(product.getId())
                .isEqualTo(PRODUCT_ID);
    }

    private Double roundUp(Double value, int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    private Product getStubProduct1(){
        Product product = new Product(PRODUCT_NAME, PACKAGE_PRICE, UNITS_PR_PACKAGE, PACKAGE_DISCOUNT_PERCENTAGE,
                PACKAGE_DISCOUNT_THRESHOLD_VALUE, UNIT_INCREASE_PERCENTAGE);
        SetIdHelper.setId(product, 1L);
        return product;
    }

}