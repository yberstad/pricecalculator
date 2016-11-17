package com.driw.web.product;

import com.driw.product.Product;
import com.driw.product.ProductService;
import com.driw.web.product.viewmodels.CalculatorViewModel;
import com.driw.web.product.viewmodels.InputFormViewModel;
import com.driw.web.product.viewmodels.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
class ProductController {

    private ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    String getHome() {
        return "index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    ModelAndView getPriceList() {
        ModelAndView modelAndView = new ModelAndView("list");
        List<com.driw.product.Product> productList = productService.findAll();
        List<ProductViewModel> productModelList = new ArrayList<>();
        for (com.driw.product.Product product : productList) {
            for (int i = 1; i <= 50; i++) {
                productModelList.add(new ProductViewModel(product.getName(), i, product.getTotalPrice(i)));
            }
        }
        return modelAndView.addObject("products", productModelList);
    }

    @RequestMapping(value = "/calculator", method = RequestMethod.GET)
    ModelAndView getForm(InputFormViewModel inputForm) {
        List<Product> productList = productService.findAll();

        CalculatorViewModel viewModel = new CalculatorViewModel(productList);
        ModelAndView modelAndView = new ModelAndView("calculator");

        return modelAndView.addObject("viewModel", viewModel);
    }

    @RequestMapping(value = "/calculator", method = RequestMethod.POST)
    ModelAndView postForm(@Valid InputFormViewModel inputForm, BindingResult bindingResult) {

        List<Product> productList = productService.findAll();

        CalculatorViewModel viewModel = new CalculatorViewModel(productList);
        ModelAndView modelAndView = new ModelAndView("calculator");

        if (bindingResult.hasErrors()) {
            return modelAndView.addObject("viewModel", viewModel);
        }

        Optional<Product> selectedProduct = productList
                .stream()
                .filter(product -> (product.getId() == inputForm.getProductId()))
                .findFirst();

        if (selectedProduct.isPresent()) {
            Product product = selectedProduct.get();
            Double total = product.getTotalPrice(inputForm.getNumberOfUnits(), inputForm.getNumberOfPackages());
            int totalUnits = inputForm.getNumberOfUnits() + (inputForm.getNumberOfPackages() * product.getUnitsPrPackage());

            viewModel.setDisplaySelectedProduct(true);
            viewModel.setSelectedProductName(product.getName());
            viewModel.setSelectedTotalUnits(totalUnits);
            viewModel.setTotal(total);
        }

        return modelAndView.addObject("viewModel", viewModel);
    }
}
