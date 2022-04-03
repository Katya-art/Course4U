package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.model.Role;
import org.example.finalProjectSpring.model.Status;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("KaterynaKravchenko")
    public void studentsListTest() throws Exception {
        User user1 = new User();

        user1.setId("1");
        user1.setFullName("Test User1");
        user1.setUsername("TestUser1");
        user1.setEmail("test.user1@gmail.com");
        user1.setPassword(bCryptPasswordEncoder.encode("testUser1"));
        user1.setRole(Role.ROLE_STUDENT);
        user1.setStatus(Status.UNLOCK);

        User user2 = new User();

        user2.setId("2");
        user2.setFullName("Test User2");
        user2.setUsername("TestUser2");
        user2.setEmail("test.user2@gmail.com");
        user2.setPassword(bCryptPasswordEncoder.encode("testUser2"));
        user2.setRole(Role.ROLE_STUDENT);
        user2.setStatus(Status.UNLOCK);

        when(userService.findAllByRole(Role.ROLE_STUDENT)).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/students_list"))
                .andExpect(status().isOk())
                .andExpect(view().name("students_list"))
                .andExpect(forwardedUrl("/WEB-INF/views/students_list.jsp"))
                .andExpect(model().attribute("studentsList", hasSize(2)))
                .andExpect(model().attribute("studentsList", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("fullName", is("Test User1")),
                                hasProperty("username", is("TestUser1")),
                                hasProperty("email", is("test.user1@gmail.com"))
                        )
                )))
                .andExpect(model().attribute("studentsList", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("fullName", is("Test User2")),
                                hasProperty("username", is("TestUser2")),
                                hasProperty("email", is("test.user2@gmail.com"))
                        )
                )));

        verify(userService, times(1)).findAllByRole(Role.ROLE_STUDENT);
        verifyNoMoreInteractions(userService);
    }

}
