package com.driw.controller.product;

import com.driw.controller.product.viewmodels.InputFormViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

// SpringRunner is an alias for the SpringJUnit4ClassRunner.(requires JUnit 4.12 or higher)
// @SpringBootTest is a simplification of @SpringApplicationConfiguration, @ContextConfiguration, @IntegrationTest etc..

// Two configurations:
// 1. WebEnvironment.MOCK and @WebAppConfiguration class annotation + WebApplicationContext
// 2. WebEnvironment.RANDOM_PORT, no @WebAppConfiguration and EmbeddedWebApplicationContext

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
public class ProductControllerIntegrationTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getHome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Price Calculator Start")));
    }

    @Test
    public void getPriceList() throws Exception {
        this.mockMvc.perform(get("/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pingvinører")));
    }

    @Test
    public void getForm() throws Exception {
        this.mockMvc.perform(get("/calculator"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Price Calculator")));
    }

    @Test
    public void postForm_InvalidNumberOfPackages_ShouldGiveUserValidationError() throws Exception {
        InputFormViewModel inputForm = new InputFormViewModel();
        inputForm.setNumberOfPackages(-1);
        String json = mapper.writeValueAsString(inputForm);
        this.mockMvc.perform(post("/calculator")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("numberOfPackages", "-1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("has-error")))
                .andExpect(content().string(containsString("Must be a number and between 0 and 100")));
    }

    @Test
    public void postForm_InvalidNumberOfUnits_ShouldGiveUserValidationError() throws Exception {
        InputFormViewModel inputForm = new InputFormViewModel();
        inputForm.setNumberOfPackages(-1);
        String json = mapper.writeValueAsString(inputForm);
        this.mockMvc.perform(post("/calculator")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("numberOfUnits", "-1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("has-error")))
                .andExpect(content().string(containsString("Must be a number and between 0 and 100")));
    }

    @Test
    public void postForm_NoProductSelected_ShouldGiveUserValidationError() throws Exception {
        InputFormViewModel inputForm = new InputFormViewModel();
        inputForm.setNumberOfPackages(-1);
        String json = mapper.writeValueAsString(inputForm);
        this.mockMvc.perform(post("/calculator")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productId", "-1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("has-error")))
                .andExpect(content().string(containsString("Must select a product")));
    }

    @Test
    public void postForm_ValidSelected_ShouldDisplaySelectedProduct() throws Exception {
        InputFormViewModel inputForm = new InputFormViewModel();
        inputForm.setNumberOfPackages(-1);
        String json = mapper.writeValueAsString(inputForm);
        this.mockMvc.perform(post("/calculator")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productId", "1")
                .param("numberOfPackages", "1")
                .param("numberOfUnits", "1")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("has-error"))))
                .andExpect(content().string(containsString("Pingvinører")));
    }

}