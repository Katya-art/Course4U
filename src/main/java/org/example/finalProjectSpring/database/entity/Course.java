package org.example.finalProjectSpring.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalProjectSpring.model.Condition;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Simple JavaBean domain object that represents a Course.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courses")
public class Course {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "theme")
    private String theme;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "number_of_students")
    private int numberOfStudents;

    @Transient
    private String teacherName;

    @ManyToOne
    @JoinTable(name = "course_teacher", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private User teacher;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "course_students_marks", joinColumns = @JoinColumn(name = "course_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id"))
//    private Set<User> students;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @MapKeyJoinColumn(name = "student_id")
//    @JoinTable(name = "course_students_marks", joinColumns = @JoinColumn(name = "course_id"),
//            inverseJoinColumns = @JoinColumn(name = "mark_id"))
//    private Map<User, Mark> studentsMarks;

    public static Course.CourseBuilder builder() {
        return new Course.CourseBuilder().id(UUID.randomUUID().toString()).condition(Condition.NOT_STARTED);
    }

}
