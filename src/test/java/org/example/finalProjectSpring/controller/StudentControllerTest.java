package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.model.enams.Condition;
import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/enroll_course"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @org.junit.Test
    @WithUserDetails("AyvaBowers")
    public void studentCoursesTest() throws Exception {
        Course course1 = new Course();

        course1.setId("1");
        course1.setName("Test Course1");
        course1.setTheme("Test");
        course1.setDuration(4L);
        course1.setNumberOfStudents(1);
        course1.setTeacher(userService.findUserByUsername("ElaReader"));
        course1.setCondition(Condition.NOT_STARTED);

        Course course2 = new Course();

        course2.setId("2");
        course2.setName("Test Course2");
        course2.setTheme("Test");
        course2.setDuration(5L);
        course2.setNumberOfStudents(1);
        course2.setTeacher(userService.findUserByUsername("ElaReader"));
        course2.setCondition(Condition.IN_PROGRESS);

        Course course3 = new Course();

        course3.setId("3");
        course3.setName("Test Course3");
        course3.setTheme("Test");
        course3.setDuration(2L);
        course3.setNumberOfStudents(1);
        course3.setTeacher(userService.findUserByUsername("ElaReader"));
        course3.setCondition(Condition.COMPLETED);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);

        User student = new User();
        //student.setEnrolledCourses(courses);

        when(userService.findUserByUsername("AyvaBowers")).thenReturn(student);

        mockMvc.perform(get("/my_courses/not_started"))
                .andExpect(status().isOk())
                .andExpect(view().name("student_courses"))
                .andExpect(forwardedUrl("/WEB-INF/views/student_courses.jsp"))
                .andExpect(model().attribute("courses", hasSize(1)))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("courseName", is("Test Course1")),
                                hasProperty("courseTheme", is("Test")),
                                hasProperty("duration", is(4L))
                        )
                )));

        mockMvc.perform(get("/my_courses/in_progress"))
                .andExpect(status().isOk())
                .andExpect(view().name("student_courses"))
                .andExpect(forwardedUrl("/WEB-INF/views/student_courses.jsp"))
                .andExpect(model().attribute("courses", hasSize(1)))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("courseName", is("Test Course2")),
                                hasProperty("courseTheme", is("Test")),
                                hasProperty("duration", is(5L))
                        )
                )));

        mockMvc.perform(get("/my_courses/completed"))
                .andExpect(status().isOk())
                .andExpect(view().name("student_courses"))
                .andExpect(forwardedUrl("/WEB-INF/views/student_courses.jsp"))
                .andExpect(model().attribute("courses", hasSize(1)))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("id", is(3L)),
                                hasProperty("courseName", is("Test Course3")),
                                hasProperty("courseTheme", is("Test")),
                                hasProperty("duration", is(2L))
                        )
                )));

        verify(userService, times(3)).findUserByUsername("ElaReader");
        verify(userService, times(3)).findUserByUsername("AyvaBowers");
        verifyNoMoreInteractions(userService);
    }
}
