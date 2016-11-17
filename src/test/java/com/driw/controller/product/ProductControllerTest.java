package com.driw.controller.product;

import com.driw.component.product.Product;
import com.driw.component.product.ProductService;
import com.driw.utils.SetIdHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    private final static Double PACKAGE_PRICE_1 = 175.0;
    private final static int UNITS_PR_PACKAGE_1 = 20;
    private static final String PRODUCT_NAME_1 = "Product1";
    private static final Long PRODUCT_ID_1 = 1L;

    private final static Double PACKAGE_PRICE_2 = 175.0;
    private final static int UNITS_PR_PACKAGE_2 = 20;
    private static final String PRODUCT_NAME_2 = "Product2";
    private static final Long PRODUCT_ID_2 = 2L;

    private final static int PACKAGE_DISCOUNT_THRESHOLD_VALUE = 3;

    private final static int PACKAGE_DISCOUNT_PERCENTAGE = 10;
    private final static int UNIT_INCREASE_PERCENTAGE = 30;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    public void getPriceList() throws Exception {
        when(productService.findAll()).thenReturn(this.getStubProductList());
        this.mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product1")));

        verify(productService, times(1)).findAll();
    }

    @Test
    public void getForm() throws Exception {
        when(productService.findAll()).thenReturn(this.getStubProductList());
        this.mockMvc.perform(get("/calculator"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product2")));

        verify(productService, times(1)).findAll();
    }

    @Test
    public void postForm() throws Exception {

    }

    private List<Product> getStubProductList(){
        Product product1 = new Product(PRODUCT_NAME_1, PACKAGE_PRICE_1, UNITS_PR_PACKAGE_1,
                PACKAGE_DISCOUNT_PERCENTAGE, PACKAGE_DISCOUNT_THRESHOLD_VALUE, UNIT_INCREASE_PERCENTAGE);
        SetIdHelper.setId(product1, PRODUCT_ID_1);

        Product product2 = new Product(PRODUCT_NAME_2, PACKAGE_PRICE_2, UNITS_PR_PACKAGE_2,
                PACKAGE_DISCOUNT_PERCENTAGE, PACKAGE_DISCOUNT_THRESHOLD_VALUE, UNIT_INCREASE_PERCENTAGE);
        SetIdHelper.setId(product2, PRODUCT_ID_2);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        return productList;
    }
}