package org.example.finalProjectSpring.controller;

import org.example.finalProjectSpring.model.enams.Condition;
import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.services.interfaces.UserService;
import org.junit.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails("ElaReader")
    public void teacherCoursesTest() throws Exception {
        Course course1 = new Course();

        course1.setId("1");
        course1.setName("Test Course1");
        course1.setTheme("Test");
        course1.setDuration(4L);
        course1.setNumberOfStudents(0);
        course1.setTeacher(userService.findUserByUsername("ElaReader"));
        course1.setCondition(Condition.NOT_STARTED);

        Course course2 = new Course();

        course2.setId("2");
        course2.setName("Test Course2");
        course2.setTheme("Test");
        course2.setDuration(5L);
        course2.setNumberOfStudents(0);
        course2.setTeacher(userService.findUserByUsername("ElaReader"));
        course2.setCondition(Condition.NOT_STARTED);

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        User teacher = new User();
        teacher.setAssignedCourses(courses);

        when(userService.findUserByUsername("ElaReader")).thenReturn(teacher);

        mockMvc.perform(get("/assigned_courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher_courses"))
                .andExpect(forwardedUrl("/WEB-INF/views/teacher_courses.jsp"))
                .andExpect(model().attribute("courses", hasSize(2)))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("courseName", is("Test Course1")),
                                hasProperty("courseTheme", is("Test")),
                                hasProperty("duration", is(4L))
                        )
                )))
                .andExpect(model().attribute("courses", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("courseName", is("Test Course2")),
                                hasProperty("courseTheme", is("Test")),
                                hasProperty("duration", is(5L))
                        )
                )));

        verify(userService, times(3)).findUserByUsername("ElaReader");
        verifyNoMoreInteractions(userService);
    }
}
