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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/")
class ProductController {

    private ProductService productService;

    @Autowired
    ProductController(ProductService productService){
        this.productService = productService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    ModelAndView getPriceList(){
        ModelAndView modelAndView = new ModelAndView("list");
        List<Product> productList = productService.findAll();
        List<ProductViewModel> productModelList = new ArrayList<>();
        for (Product product : productList ) {
            for (int i = 1; i<= 50; i++){
                productModelList.add(new ProductViewModel(product.getName(), i, product.getTotalPrice(i)));
            }
        }
        return modelAndView.addObject("products", productModelList);
    }

    @RequestMapping(value = "/calculator", method = RequestMethod.GET)
    ModelAndView getForm(InputForm inputForm){
        ModelAndView modelAndView = new ModelAndView("calculator");
        List<Product> productList = productService.findAll();
        Set<Option> optionList = new HashSet<>();
        for(Product product : productList) {
            optionList.add(new Option(product.getId(), product.getName()));
        }
        return modelAndView.addObject("options", optionList);
    }

    @RequestMapping(value = "/calculator", method = RequestMethod.POST)
    String postForm(InputForm inputForm){
        return "calculator";
    }

    public static final class InputForm {
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

    public static final class Option {
        private long value;
        private String text;

        public Option(long value, String text){
            this.value = value;
            this.text = text;
        }

        public long getValue() {
            return value;
        }

        public String getText() {
            return text;
        }

        public void setValue(long value) {
            this.value = value;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
