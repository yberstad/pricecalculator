package com.driw.web.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pingvinører")));
    }

    @Test
    public void getForm() throws Exception {
        this.mockMvc.perform(get("/calculator"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Price Calculator")));
    }

    @Test
    public void postForm() throws Exception {

    }

}