package org.example.finalProjectSpring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.emptyString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void correctLoginTest() throws Exception {
        this.mockMvc.perform(formLogin().user("KaterynaKravchenko").password("12345678"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void incorrectLoginTest() throws Exception {
        this.mockMvc.perform(post("/login").param("user", "Alfred"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void contextLoads() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(forwardedUrl("/WEB-INF/views/welcome.jsp"));
    }

    @Test
    public void userPageTest() throws Exception {
        this.mockMvc.perform(get("/user/KaterynaKravchenko"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void userPageTestFailed() throws Exception {
        this.mockMvc.perform(get("/user/test"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}
