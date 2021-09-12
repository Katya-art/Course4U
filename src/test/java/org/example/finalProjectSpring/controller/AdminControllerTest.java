package org.example.finalProjectSpring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("KaterynaKravchenko")
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void studentsListTest() throws Exception {
        this.mockMvc.perform(get("/students_list"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id=\"students_list\"]").nodeCount(7));
    }

}
