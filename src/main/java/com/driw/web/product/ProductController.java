package com.driw.web.product;

import com.driw.product.Product;
import com.driw.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
class ProductController {

    private ProductService productService;

    @Autowired
    ProductController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView getPriceListForAllProducts(Model model){
        ModelAndView modelAndView = new ModelAndView("index");
        List<Product> productList = productService.findAll();
        List<ProductViewModel> productModelList = new ArrayList<>();
        for (Product product : productList ) {
            for (int i = 1; i<= 50; i++){
                productModelList.add(new ProductViewModel(product.getName(), i, product.getTotalPrice(i)));
            }
        }
        return modelAndView.addObject("products", productModelList);
    }
}
